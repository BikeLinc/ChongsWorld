package inventory;

import world.TileType;

public class InventoryItem {

	private TileType type;
	private int number;
	
	public InventoryItem(TileType type, int number) {
		this.type = type;
		this.number = number;
	}

	public TileType getType() {
		return type;
	}

	public int getNumber() {
		return number;
	}
	
	public void addNumber(int amt) {
		number += amt;
	}
	
	public void setNumber(int amt) {
		number = amt;
	}
	
}
