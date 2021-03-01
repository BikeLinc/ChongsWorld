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
import utils.FileTool;
import world.TileType;

public class Player extends Entity {
	
	public Inventory inventory;
	public InventoryItem item;
	public int selectedItem = 0;
	
	public int health = 20;
	public int animation = 0;
	
	private static final int SPEED = 2;
	private static final int JUMP_VELOCITY = 4;
	
	int texture = 0;
	TextureRegion[][] sprite;

	public Player(float x, float y, GameMap gameMap) {
		super(x, y, EntityType.PLAYER, gameMap);
		sprite = TextureRegion.split(new Texture(FileTool.getPath() + "/assets/chongo2.png"), 16, 16);
		inventory = new Inventory();
	}
	
	@Override
	public void update(float delta, float gravity) {
			
		
		super.update(delta, gravity);
		movePlayer(delta);
		updateItem();
	}
	
	private void movePlayer(float delta) {
		boolean left = false;
		boolean right = false;
		boolean up = false;
		boolean down = false;
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded) {
			this.velocityY += JUMP_VELOCITY * getWeight();
		} else if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded && this.velocityY > 0) {
			this.velocityY += JUMP_VELOCITY * getWeight() * delta;
		}
		
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
		
		
		
		updateTexture(left, right, up, down);
	}
	
	/**
	 * Updates Players Selected Item If New Inventory Slot Selected
	 */
	private void updateItem() {
		if(!inventory.isEmpty()) {
			if(item == null && inventory.getSize() > 0 || !inventory.isEmpty(item)) {
				item = inventory.getItemAt(0);
			}
			if(item.getType().isItem()) {
				for(InventoryItem items : inventory.getItems()) {
					if(!items.getType().isItem()) {
						item = items;
						break;
					}
					item = null;
				}
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_1) && inventory.getSize() >= 1) {
				item = inventory.getItemAt(0);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_2) && inventory.getSize() >= 2) {
				item = inventory.getItemAt(1);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_3) && inventory.getSize() >= 3) {
				item = inventory.getItemAt(2);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_4) && inventory.getSize() >= 4) {
				item = inventory.getItemAt(3);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_5) && inventory.getSize() >= 5) {
				item = inventory.getItemAt(4);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_6) && inventory.getSize() >= 6) {
				item = inventory.getItemAt(5);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_7) && inventory.getSize() >= 7) {
				item = inventory.getItemAt(6);
			}
		} else {
			item = null;
		}
		selectedItem = inventory.getItems().indexOf(item);
	}
	
	/**
	 * Check Entity Build Action
	 * @param camera
	 * @param map
	 */
	public void build(Camera camera, Map map) {
		if(Gdx.input.isTouched()) {
			boolean shiftLeft = Gdx.input.isKeyPressed(Keys.SHIFT_LEFT);
			boolean mouseLeft = Gdx.input.isButtonPressed(Buttons.LEFT);
			boolean mouseRight = Gdx.input.isButtonPressed(Buttons.RIGHT);
			Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			// Set Tile
			if(mouseRight) {
				if(item != null && !item.getType().isItem() && inventory.getItem(item) != null && inventory.getItem(item).getNumber() > 0) {
					if(map.getTileTypeByLocation(1, pos.x, pos.y) != item.getType() && map.getTileTypeByLocation(5, pos.x, pos.y) != item.getType()) {
						if(shiftLeft && map.getTileTypeByLocation(1, pos.x, pos.y) == TileType.AIR || shiftLeft && map.getTileTypeByLocation(1, pos.x, pos.y) == null) {
							map.setTile(item.getType(), 1, pos.x, pos.y);
							inventory.removeItemFrom(item);
						} else if (map.getTileTypeByLocation(5, pos.x, pos.y) == TileType.AIR && map.getTileTypeByLocation(5, pos.x, pos.y) == TileType.AIR  || map.getTileTypeByLocation(1, pos.x, pos.y) == null && map.getTileTypeByLocation(5, pos.x, pos.y) == null){
							map.setTile(item.getType(), 5, pos.x, pos.y);
							inventory.removeItemFrom(item);
						}
					}
				}
			}
			
				// Break Tile
			if(mouseLeft) {
			TileType tile = null;
				for(int layer = map.getLayers() -1; layer > 0; layer--) {
					TileType layerTile = map.getTileTypeByLocation(layer, pos.x, pos.y);
					if(layerTile != null || layerTile != TileType.AIR) {
						tile = layerTile;
						map.setTile(TileType.AIR, layer, pos.x, pos.y);
						if(tile != null && tile != TileType.AIR) {
							inventory.add(tile);
						}
					}
				}
				
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite[0][texture], pos.x, pos.y, getWidth(), getHeight());
	}
	
	private void updateTexture(boolean left, boolean right, boolean up, boolean down) {
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
	
	private void setTexture(int no) {
		texture = no;
	}

	public void animateTexture() {
		if(animation == 0) {
			animation = 1;
		} else {
			animation = 0;
		}
	}

}
