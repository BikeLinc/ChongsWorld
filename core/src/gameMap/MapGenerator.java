package gameMap;

import java.util.Random;

import maths.PerlinNoise;
import world.TileType;

public class MapGenerator {
	
	public static MapData generateRandomMap (String id, String name, int size) {
		long startTime = System.nanoTime();
		
		MapData mapData = new MapData();
		mapData.id = id;
		mapData.name = id;
		mapData.map = new int[6][size][size];
		
		// 0 - BG
		// 1 - Walls
		// 2 - Events (Triggers)
		// 3 - Liquids
		// 4 - Items
		// 5 - Blocks (Collider)
		
		Random rand = new Random();
		
		int[] trees = genTreeLocations(size, rand);
		
		PerlinNoise noise = new PerlinNoise();
		noise.generateHeights(size,size);
		
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				
				// Layer 0 
				mapData.map[0][row][col] = TileType.SKY.getId();
				
				// Layer 1
				int rowHeight = noise.getHeight(col);
				
				if(row >= rowHeight) {
					if (row - rowHeight == 0) {
						mapData.map[5][row][col] = TileType.GRASS.getId();
					} else if (row - rowHeight == 1 || row - rowHeight == 2 || row - rowHeight == 3 ) {
						mapData.map[5][row][col] = TileType.DIRT.getId();
					} else {
						int prob = rand.nextInt(20);
						if(prob >  4) {
							mapData.map[5][row][col] = TileType.STONE.getId();
						} else {
							mapData.map[5][row][col] = TileType.IRON_ORE.getId();
						}
						
					}
				}
				
				if(trees[col] == 1 && col > 3 && col < (size - 3)) {
					if(row - rowHeight  + 1 == 0 || row - rowHeight  + 2 == 0 || row - rowHeight  + 3 == 0  || row - rowHeight  + 4 == 0 || row - rowHeight  + 5 == 0) {
						mapData.map[1][row][col] = TileType.WOOD_LOG.getId();
					}
					
					if(row - rowHeight  + 6 == 0 || row - rowHeight  + 7 == 0 || row - rowHeight  + 8 == 0  || row - rowHeight  + 9 == 0) {
						mapData.map[1][row][col] = TileType.WOOD_LOG.getId();
						mapData.map[1][row][col - 1] = TileType.LEAF.getId();
						mapData.map[1][row][col + 1] = TileType.LEAF.getId();
						mapData.map[4][row][col - 1] = getLeaf(rand);
						mapData.map[4][row][col + 1] = getLeaf(rand);
					}
					if(row - rowHeight  + 10 == 0 || row - rowHeight  + 11 == 0) {
						mapData.map[1][row][col] = TileType.LEAF.getId();
						mapData.map[1][row][col - 1] = TileType.LEAF.getId();
						mapData.map[4][row][col] = getLeaf(rand);
						mapData.map[1][row][col - 1] = getLeaf(rand);
					}
				}
				if(col > 3 && col < (size - 3) && trees[col - 1] == 1) {
					if(row - rowHeight  + 6 == 0 || row - rowHeight  + 7 == 0 || row - rowHeight  + 8 == 0  || row - rowHeight  + 9 == 0) {
						mapData.map[1][row][col] = TileType.LEAF.getId();
						mapData.map[4][row][col] = getLeaf(rand);
					}
					if(row - rowHeight  + 10 == 0 || row - rowHeight  + 11 == 0) {
						mapData.map[1][row][col] = TileType.LEAF.getId();;
						mapData.map[4][row][col] = getLeaf(rand);
					}
				}
				


				if(mapData.map[5][row][col] == 0) {
					mapData.map[5][row][col] = TileType.AIR.getId();
				} else if(mapData.map[3][row][col] == 0) {
					mapData.map[3][row][col] = TileType.AIR.getId();
				} else if(mapData.map[1][row][col] == 0) {
					mapData.map[1][row][col] = TileType.AIR.getId();
				}
			}
		}
		
		System.out.println("Map Generated In: " + ((System.nanoTime() - startTime)/1000000.0) + "ms");
		
		return mapData;
	}
	
	private static int[] genTreeLocations(int size, Random rand) {
		int[] trees = new int[size];
		for(int i = 0; i < size; i++) {
			int prob = rand.nextInt(20);
			if(prob <  2) {
				trees[i] = 1;
			} else {
				trees[i] = 0;
			}
		}
		return trees;
	}
	
	private static int getLeaf(Random rand) {
		int prob = rand.nextInt(15);
		if(prob <  2) {
			return TileType.MANGO.getId();
		} else {
			return 0;
		}
	}
}
