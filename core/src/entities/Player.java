package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
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

public class Player extends Entity implements InputProcessor{
	
	Camera camera;
	Map map;
	
	public Inventory inventory;
	public InventoryItem item;
	public int selectedItem = 0;
	
	public int health = 20;
	public int animation = 0;
	
	private static final int SPEED = 2;
	private static final int JUMP_VELOCITY = 4;
	
	private boolean breakBlock = false;
	private boolean placeBlock = false;
	
	int texture = 0;
	TextureRegion[][] sprite;

	public Player(float x, float y, GameMap gameMap) {
		super(x, y, EntityType.PLAYER, gameMap);
		sprite = TextureRegion.split(new Texture(FileTool.getPath() + "/assets/chongo2.png"), 16, 16);
		inventory = new Inventory();
		Gdx.input.setInputProcessor(this);
		
		// Give Player Some Items
		inventory.add(TileType.WOOD_PLANK);
		inventory.add(TileType.WOOD_LOG);
		inventory.add(TileType.COBBLE);
		
		inventory.add(TileType.FURNACE_LIT);
		inventory.add(TileType.FURNACE_LIT);
		
		inventory.add(TileType.CHEST);
		inventory.add(TileType.CHEST);
		
		for(int i = 0; i < 64; i++) {
			inventory.add(TileType.WOOD_PLANK);
			inventory.add(TileType.WOOD_LOG);
			inventory.add(TileType.COBBLE);
		}
	}
	
	@Override
	public void update(Camera camera, Map map, float delta, float gravity) {
		super.update(camera, map, delta, gravity);
		this.camera = camera;
		this.map = map;
		build();
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
			if(item == null && inventory.getSize() > 0 || !inventory.isItemInInventory(item)) {
				item = inventory.getItemByIndex(0);
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
				item = inventory.getItemByIndex(0);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_2) && inventory.getSize() >= 2) {
				item = inventory.getItemByIndex(1);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_3) && inventory.getSize() >= 3) {
				item = inventory.getItemByIndex(2);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_4) && inventory.getSize() >= 4) {
				item = inventory.getItemByIndex(3);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_5) && inventory.getSize() >= 5) {
				item = inventory.getItemByIndex(4);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_6) && inventory.getSize() >= 6) {
				item = inventory.getItemByIndex(5);
			}
			if(Gdx.input.isKeyPressed(Keys.NUM_7) && inventory.getSize() >= 7) {
				item = inventory.getItemByIndex(6);
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
	public void build() {
		if(Gdx.input.isTouched()) {
			boolean shiftLeft = Gdx.input.isKeyPressed(Keys.SHIFT_LEFT);
			Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			// Set Tile
			if(placeBlock) {
				if(item != null && !item.getType().isItem() && inventory.getItemByItem(item) != null && inventory.getItemByItem(item).getNumber() > 0) {
					if(map.getTileTypeByLocation(1, pos.x, pos.y) != item.getType() && map.getTileTypeByLocation(5, pos.x, pos.y) != item.getType()) {
						if(shiftLeft && map.getTileTypeByLocation(1, pos.x, pos.y) == TileType.AIR || shiftLeft && map.getTileTypeByLocation(1, pos.x, pos.y) == null) {
							map.setTile(item.getType(), 1, pos.x, pos.y);
							inventory.decrease(item);
						} else if (map.getTileTypeByLocation(5, pos.x, pos.y) == TileType.AIR && map.getTileTypeByLocation(5, pos.x, pos.y) == TileType.AIR  || map.getTileTypeByLocation(1, pos.x, pos.y) == null && map.getTileTypeByLocation(5, pos.x, pos.y) == null){
							map.setTile(item.getType(), 5, pos.x, pos.y);
							inventory.decrease(item);
						}
					}
				}
			}
			
				// Break Tile
			if(breakBlock) {
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

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(button == Buttons.LEFT) {
			breakBlock = true;
		} else if (button == Buttons.RIGHT) {
			placeBlock = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(button == Buttons.LEFT) {
			breakBlock = false;
		} else if (button == Buttons.RIGHT) {
			placeBlock = false;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

}
