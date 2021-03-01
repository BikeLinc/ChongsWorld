package world;

import java.util.HashMap;

public enum TileType {
	
	GRASS(1, true, false,"Grass"),
	DIRT(2, true, false, "Dirt"),
	STONE(3, true, false, "Stone"),
	COBBLE(4, true, false, "Cobble"),
	WOOD_LOG(5, true, false, "Wood Log"),
	LEAF(6, true, false, "Leaf"),
	WOOD_PLANK(7, true, false, "Wood Plank"),
	GOLD_ORE(8, true, false, "Gold Ore"),
	IRON_ORE(9, true, false, "Iron Ore"),
	COAL_ORE(10, true, false, "Coal Ore"),
	FURNACE_UNLIT(11, true, true, "Furnace"),
	FURNACE_LIT(12, true, true, "Furnace"),
	CHEST(13, true, true, "Chest"),
	WATER_1(14, true, false, "Water"),
	WATER_2(15, true, false, "Water"),
	LAVA_1(16, true, false, "Lava"),
	LAVA_2(17, true, false, "Lava"),
	LADDER(18, true, true, "Ladder"),
	MANGO(19, true, true, "Mango"),
	GOLD(20, true, true, "Gold"),
	IRON(21, true, true, "Iron"),
	COAL(22, true, true, "Coal"),
	AXE_IRON(23, true, true, "Iron Axe"),
	AXE_GOLD(24, true, true, "Gold Axe"),
	AXE_WOOD(25, true, true, "Wood Axe"),
	SKY(26, false, true, "Sky"),
	AIR(27, false, false, "Air"),
	DEBUG(28, false, false, "Debugging Tile");
	
	
	public static final int TILE_SIZE = 16;
	
	private int id;
	private boolean collidable;
	private boolean item;
	private String name;
	private float damage;

	private TileType(int id, boolean collidable, boolean item, String name) {
		this(id, collidable, item, name, 0.0f);
	}
	
	private TileType(int id, boolean collidable, boolean item, String name, float damage) {
		this.id = id;
		this.collidable = collidable;
		this.item = item;
		this.name = name;
		this.damage = damage;
	}

	public int getId() {
		return id;
	}

	public boolean isCollidable() {
		return collidable;
	}
	
	public boolean isItem() {
		return item;
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
