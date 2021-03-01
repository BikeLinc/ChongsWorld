package gameMap;

import java.awt.Point;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import utils.FileTool;
import world.TileType;

public class Map extends GameMap {
	
	private MapData data;
	private TextureRegion[][] tiles;
	
	/**
	 * Loads Map Information & Tile Texture
	 */
	public Map() {
		this.data = MapLoader.loadMap("basic", "grass");
		
		tiles = TextureRegion.split(new Texture(FileTool.getPath() + "/assets/tiles4.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
	}

	/**
	 * Renders Map
	 */
	public void render(OrthographicCamera camera, SpriteBatch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		int x,y;
		for (int layer = 0; layer < getLayers(); layer++) {
			for (int row = 0; row < getHeight(); row++) {
				for (int col = 0; col < getWidth(); col++) {
					TileType type = this.getTileTypeByCoordinate(layer, col, row);
					if (type != null) {
					y =	(type.getId() -1)/6;
					x = (type.getId() -1) - (y * 6);
						if(layer == 1) {
							batch.setColor(Color.LIGHT_GRAY);
						} else {
							batch.setColor(Color.WHITE);
						}
						batch.draw(tiles[y][x], col * TileType.TILE_SIZE, row * TileType.TILE_SIZE, 16,16);
					}
				}
			}
		}
		super.render(camera, batch);
		batch.end();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}

	/**
	 * Save & Cleanup
	 */
	@Override
	public void dispose() {
		MapLoader.saveMap(data);
	}
	
	@Override
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return this.getTileTypeByCoordinate(layer,(int) (x/TileType.TILE_SIZE),(int) (y/TileType.TILE_SIZE));
	}
	
	public Point screenCoordToMapCoord(float x, float y) {
		return new Point((int) (x/TileType.TILE_SIZE),(int) (y/TileType.TILE_SIZE));
	}

	@Override
	public TileType getTileTypeByCoordinate(int layer, int col, int row) {
		if(col<0 || col >= getWidth() || row < 0 || row >= getHeight()) {
			return null;
		}
		return TileType.getTileById(data.map[layer][getHeight() - row -1][col]);
	}

	
	/**
	 * Sets TileType At Given Layer And Screen Coordinates
	 * @param type
	 * @param layer
	 * @param x
	 * @param y
	 */
	public void setTile(TileType type, int layer, float x, float y) {
		Point coord = screenCoordToMapCoord(x, y);
		if(coord.x < 0 || coord.x >= getWidth() || coord.y < 0 || coord.y >= getHeight()) {
			System.out.println("You cant build here, looks like your out of this world!");
		}  else {
			if(TileType.getTileById(data.map[0][getHeight() - coord.y -1][coord.x]) != TileType.SKY) {
				data.map[0][getHeight() - coord.y -1][coord.x] = type.getId();
			}
			if(type != null) {
				data.map[layer][getHeight() - coord.y -1][coord.x] = type.getId();
			}
		}
		//System.out.println("Tile @ (" + coord.x + ", " + coord.y + ") on Layer: " + layer + " | "+ getTileTypeByCoordinate(layer, coord.x, coord.y).getName() + " set to " + type.getName());
	}
	
	@Override
	public int getWidth() {
		return data.map[0][0].length;
	}

	@Override
	public int getHeight() {
		return data.map[0].length;
	}

	@Override
	public int getLayers() {
		return data.map.length;
	}

}
