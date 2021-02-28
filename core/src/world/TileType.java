package world;

import java.util.HashMap;

public enum TileType {
	
	GRASS(1, true, "Grass"),
	DIRT(2, true, "Dirt"),
	SKY(3, false, "Sky"),
	LAVA(4, true, "Lava"),
	CLOUD(5, false, "Cloud"),
	STONE(6, true, "Stone"),
	MANGO(7,true, "MangoLeft"),
	WOOD(9,true, "Wood"),
	LEAF(10,true, "Leaf");
	
	public static final int TILE_SIZE = 16;
	
	private int id;
	private boolean collidable;
	private String name;
	private float damage;

	private TileType(int id, boolean collidable, String name) {
		this(id, collidable, name, 0.0f);
	}
	
	private TileType(int id, boolean collidable, String name, float damage) {
		this.id = id;
		this.collidable = collidable;
		this.name = name;
		this.damage = damage;
	}

	public int getId() {
		return id;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public String getName() {
		return name;
	}

	public float getDamage() {
		return damage;
	}
	
	private static HashMap<Integer, TileType> tileMap;

	static {
		tileMap = new HashMap<Integer, TileType>();
		for(TileType tileType : TileType.values()) {
			tileMap.put(tileType.getId(), tileType);
		}
	}
	
	public static TileType getTileById(int id) {
		return tileMap.get(id);
	}
	
}
