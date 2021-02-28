package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import gameMap.GameMap;
import gameMap.Map;
import inventory.Inventory;
import inventory.InventoryItem;
import world.TileType;

public class Player extends Entity {
	
	public int health = 20;
	public int mangos = 0;
	public int active = 0;
	public Inventory inventory;
	public InventoryItem item;
	
	public int animation = 0;
	
	private static final int SPEED = 2;
	private static final int JUMP_VELOCITY = 3;
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	int texture = 0;
	TextureRegion[][] sprite;

	public Player(float x, float y, GameMap gameMap) {
		super(x, y, EntityType.PLAYER, gameMap);
		sprite = TextureRegion.split(new Texture("chongo2.png"), 16, 16);
		inventory = new Inventory();
	}
	
	@Override
	public void update(float delta, float gravity) {
			
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded) {
			this.velocityY += JUMP_VELOCITY * getWeight();
		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded && this.velocityY > 0) {
			this.velocityY += JUMP_VELOCITY * getWeight() * delta;
		}
		super.update(delta, gravity);
		
		left = false;
		right = false;
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			moveX(-SPEED);
			left = true;
			right = false;
		}
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			moveX(SPEED);
			right = true;
			left = false;
		}
		
		if (velocityY > 0 && !left && !right) {
			up = true;
			down = false;
		}
		
		if(velocityY < 0 && !left && !right) {
			down = true;
			up = false;
		}
		
		if(velocityY == 0) {
			up = false;
			down = false;
		}
		
		if(left) {
			setTexture(2 + animation);
		}
		if(right) {
			setTexture(0 + animation);
		}
		if(!left && !right) {
			setTexture(4 + animation);
		}
		if(up) {
			setTexture(6);
		}
		if(down) {
			setTexture(7);
		}
	}
	
	/**
	 * Check Entity Build Action
	 * @param camera
	 * @param map
	 */
	public void build(Camera camera, Map map) {
		if(Gdx.input.isTouched()) {
			Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			// Set Tile
			if(Gdx.input.isButtonPressed(Buttons.RIGHT)) {
				if(item != null) {
					map.setTile(item.getType(), 1, pos.x, pos.y);
				}
			}
			
			// Break Tile
			if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
				TileType topTile = map.getTileTypeByLocation(1, pos.x, pos.y);
				TileType bottomTile = map.getTileTypeByLocation(0, pos.x, pos.y);
				if(topTile != TileType.SKY && topTile != null || bottomTile != TileType.SKY && bottomTile != null) {
					inventory.add(topTile);
				}
				map.setTile(TileType.SKY, 0, pos.x, pos.y);
				map.setTile(TileType.SKY, 1, pos.x, pos.y);
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite[0][texture], pos.x, pos.y, getWidth(), getHeight());
	}
	
	
	// 0 - Left
	// 1 - Right
	// 2 - Up
	// 3 - Down
	private void setTexture(int no) {
		texture = no;
	}

	public void animate() {
		if(animation == 0) {
			animation = 1;
		} else {
			animation = 0;
		}
	}

}
