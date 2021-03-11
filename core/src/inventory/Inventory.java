package inventory;

import java.util.ArrayList;
import java.util.List;

import world.TileType;

public class Inventory {
	
	List<InventoryItem> items = new ArrayList<>();
	
	public void add(TileType tile) {
		if(isTileTypeInInventory(tile)) {
			increase(getItemByTileType(tile));
		} else {
			items.add(new InventoryItem(tile, 1));
		}
	}
	
	public void increase(InventoryItem item) {
		for(InventoryItem inventoryItem : items) {
			if(inventoryItem.getType() == item.getType()) {
				inventoryItem.addNumber(1);
				break;
			}
		}
	}
	
	public void decrease(InventoryItem item) {
		for(InventoryItem inventoryItem : items) {
			if(inventoryItem.getType() == item.getType()) {
				if(inventoryItem.getNumber() -1 < 1) {
					items.remove(inventoryItem);
				} else {
					inventoryItem.addNumber(-1);
				}
				break;
			}
		}
	}
	
	public void setItemValue(InventoryItem item, int amt) {
		for(InventoryItem inventoryItem : items) {
			if(inventoryItem.getType() == item.getType()) {
				inventoryItem.setNumber(amt);
				break;
			}
		}
	}

	public InventoryItem getItemByItem(InventoryItem item) {
		for(InventoryItem inventoryItem : items) {
			if(inventoryItem.getType() == item.getType()) {
				return inventoryItem;
			}
		}
		return null;
	}
	
	public InventoryItem getItemByIndex(int index) {
		if(index > items.size() || index < 0) {
			return null;
		} else {
			return items.get(index);
		}
	}
	
	public InventoryItem getItemByTileType(TileType type) {
		if(isTileTypeInInventory(type)) {
			for(InventoryItem item : items) {
				if(item.getType() == type) {
					return item;
				}
			}
		}
		return null;
	}
	
	public List<InventoryItem> getItems() {
		return items;
	}
	
	public String[] getInventoryAsLabeledStringArray() {
		String[] str = new String[items.size()];
		for(int i = 0; i < str.length; i++) {
			InventoryItem item = items.get(i);
			if(item.getType() != null) {
				str[i] = item.getNumber() + " " + item.getType().getName();
			}
		}
		return str;
	}
	
	public int getSize() {
		return items.size();
	}
	
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	public boolean isItemInInventory(InventoryItem item) {
		return items.contains(item);
	}
	
	public boolean isTileTypeInInventory(TileType tile) {
		for(InventoryItem item : items) {
			if(item.getType() == tile) {
				return true;
			}
		}
		return false;
	}
}

