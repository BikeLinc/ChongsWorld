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
import world.TileType;

public class Player extends Entity {
	
	public int health = 20;
	public int mangos = 0;
	public int active = 0;
	public TileType tile;
	public int[] inventory;
	
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
		inventory = new int[7];
	}
	
	public TileType[] readInventory(int[] inventory, int size) {
		TileType[] tiles = new TileType[size];
		for(int i = 0; i < inventory.length; i++) {
			if(inventory[i] != 0) {
				if(i == 0) {
					tiles[i] = TileType.GRASS;
				} 
				if(i == 1) {
					tiles[i] = TileType.DIRT;
				}
				if(i == 2) {
					tiles[i] = TileType.STONE;
				}
				if(i == 3) {
					tiles[i] = TileType.WOOD;
				}
				if(i == 4) {
					tiles[i] = TileType.MANGOLEFT;
				}
				if(i == 5) {
					tiles[i] = TileType.LEAF;
				}
				if(i == 6) {
					tiles[i] = TileType.LAVA;
				}
			}
		}
		return tiles;
	}
	
	@Override
	public void update(float delta, float gravity) {
		int max = 0;
		for(int i = 0; i < inventory.length; i++) {
			if(inventory[i] != 0) {
				max++;
			}
		}
			if(max >= 1 && Gdx.input.isKeyPressed(Keys.NUM_1)) {
				active = 0;
			}
			if(max >= 2 && Gdx.input.isKeyPressed(Keys.NUM_2)) {
				active = 1;
			}
			if(max >= 3 && Gdx.input.isKeyPressed(Keys.NUM_3)) {
				active = 2;
			}
			if(max >= 4 && Gdx.input.isKeyPressed(Keys.NUM_4)) {
				active = 3;
			}
			if(max >= 5 && Gdx.input.isKeyPressed(Keys.NUM_5)) {
				active = 4;
			}
			if(max >= 6 && Gdx.input.isKeyPressed(Keys.NUM_6)) {
				active = 5;
			}
			if(max >= 7 && Gdx.input.isKeyPressed(Keys.NUM_7)) {
				active = 6;
			}
			
		TileType[] type = readInventory(inventory, max);
		if(max != 0) {
			tile = type[active];
		}
		
			
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
			if(Gdx.input.isButtonPressed(Buttons.RIGHT)) {
				
				if(tile != null && inventory[active] > 0) {
					map.setTile(tile,1, pos.x, pos.y);
					inventory[active] -= 1;
				}
				
			}
			if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
				TileType topTile = map.getTileTypeByLocation(1, pos.x, pos.y);
				TileType bottomTile = map.getTileTypeByLocation(0, pos.x, pos.y);
				if (topTile != null  || bottomTile != null && topTile != TileType.SKY && bottomTile != TileType.SKY && topTile != TileType.CLOUD && bottomTile != TileType.CLOUD) {
					
					if(topTile == TileType.GRASS || bottomTile == TileType.GRASS) {
						inventory[0] += 1;
					}
					
					if(topTile == TileType.DIRT || bottomTile == TileType.DIRT) {
						inventory[1] += 1;
					}
					
					if(topTile == TileType.STONE || bottomTile == TileType.STONE) {
						inventory[2] += 1;
					}
					
					if(topTile == TileType.WOOD || bottomTile == TileType.WOOD) {
						inventory[3] += 1;
					}
					
					if(topTile == TileType.MANGOLEFT || topTile == TileType.MANGORIGHT) {
						inventory[4] += 1;
					}
					
					if(topTile == TileType.LEAF || bottomTile == TileType.LEAF) {
						inventory[5] += 1;
					}
					
					if(topTile == TileType.LAVA || bottomTile == TileType.LAVA) {
						inventory[6] += 1;
					}
				}
				map.setTile(TileType.SKY,1, pos.x, pos.y);
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
