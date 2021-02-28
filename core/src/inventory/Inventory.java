package inventory;

import java.util.ArrayList;
import java.util.List;

import world.TileType;

public class Inventory {
	
	List<InventoryItem> items = new ArrayList<>();
	
	public void add(TileType tile) {
		
		boolean exists = addNumberToItem(tile, 1);
		if(!exists) {
			items.add(new InventoryItem(tile, 1));
		}
	}
	
	public boolean addNumberToItem(TileType type, int amt) {
		for(InventoryItem inventoryItem : items) {
			if(inventoryItem.getType() == type) {
				inventoryItem.addNumber(amt);
				return true;
			}
		}
		return false;
	}
	
	public void setNumberToItem(InventoryItem item, int amt) {
		for(InventoryItem inventoryItem : items) {
			if(inventoryItem.getType() == item.getType()) {
				inventoryItem.setNumber(amt);
				break;
			}
		}
	}

	public InventoryItem getItem(InventoryItem item) {
		for(InventoryItem inventoryItem : items) {
			if(inventoryItem.getType() == item.getType()) {
				return inventoryItem;
			}
		}
		return null;
	}
	
	public String[] getInventoryAsLabeledStringArray() {
		String[] str = new String[items.size()];
		for(int i = 0; i < str.length; i++) {
			InventoryItem item = items.get(i);
			if(item.getType() != null) {
				str[i] = item.getNumber() + " " + item.getType().toString();
			}
		}
		return str;
	}
}

