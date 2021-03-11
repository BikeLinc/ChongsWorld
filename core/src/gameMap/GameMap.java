package gameMap;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.Entity;
import entities.Player;
import world.TileType;

public abstract class GameMap {
	
	Player player;
	ArrayList<Entity> entities;
	
	/**
	 * Creates New Entities List & Adds Player
	 */
	public GameMap() {
		entities = new ArrayList<>();
		player = new Player(500,1200,this);
		entities.add(player);
	}

	/**
	 * Renders All Entities
	 * @param camera
	 * @param batch
	 */
	public void render(OrthographicCamera camera, SpriteBatch batch) {
		for (Entity entity : entities) {
			entity.render(batch);
		}
	}
	
	/**
	 * Applies Gravity To Entities
	 * @param delta
	 */
	public void update(float delta) {
	}
	
	public abstract void dispose();
	
	/**
	 * Returns Player If Not Null
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns TileType From Given Layer & Screen Location
	 * @param layer
	 * @param x
	 * @param y
	 * @return TileType
	 */
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), (int) (y / TileType.TILE_SIZE));
	}
	
	/**
	 * Returns TileType From Given Layer & Map Coordinates
	 * @param layer
	 * @param col
	 * @param row
	 * @return TileType
	 */
	public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);
	
	/**
	 * Returns True If Rectangle Coordinates Collide With Tile In Map
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return boolean
	 */
	public boolean doesRectCollideWithMap(float x, float y, int width, int height) {
		if (x < 0 || y < 0 || x + width > getPixelWidth() || y + height > getPixelHeight())
			return true;
		
		for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil((y + height) / TileType.TILE_SIZE); row++) {
			for (int col = (int) (x / TileType.TILE_SIZE); col < Math.ceil((x + width) / TileType.TILE_SIZE); col++) {
					TileType type = getTileTypeByCoordinate(5, col, row);
					if (type != null && type.isCollidable())
						return true;
			}
		}
		return false;
	}
	
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getLayers();
	
	public int getPixelWidth() {
		return this.getWidth() * TileType.TILE_SIZE;
	}
	
	public int getPixelHeight() {
		return this.getHeight() * TileType.TILE_SIZE;
	}
	
	/**
	 * Returns Converted Screen Coordinate To Map Coordinate
	 * @param i
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract Point screenCoordToMapCoord(float x, float y);
	
}
