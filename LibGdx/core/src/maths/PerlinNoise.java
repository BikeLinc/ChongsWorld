package maths;

import java.util.Random;

public class PerlinNoise {
	
	int[] heights;
	int minHeight = 24 ;
	
	public void generateHeights(int width, int height) {
		this.heights = new int[width];
		
		Random random = new Random();
		
		heights[0] = (int) ((random.nextInt((height/2) - minHeight) + 1) + minHeight);
		
		for(int i = 1; i < width; i++) {
			int v = random.nextInt(3);
			int offset = random.nextInt(2);
			
			if(v == 0) {
				heights[i] = heights[i-1]+offset;
			} else if (v == 1){
				heights[i] = heights[i-1]-offset;
			} else {
				heights[i] = heights[i-1];
			}
		}
	}
	
	public int getHeight(int x) {
		return heights[x];
	}
}
