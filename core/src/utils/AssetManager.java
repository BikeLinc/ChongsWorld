package utils;

import java.io.File;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {
	
	private static TextureRegion[][] tilesAtlas = TextureLoader.loadTextureAtlas("/assets/tiles4.png",16,16);

	public static TextureRegion[][] getTiles() {
		return tilesAtlas;
	}
	
	public static Sprite getSprite(String fileName) {
		File texFile = new File(new File("").getAbsolutePath() + fileName);
		FileHandle slotFileHandle = new FileHandle(texFile);
		Texture texture = new Texture(slotFileHandle);
		return new Sprite(texture);
	}
	
	public static FileHandle getFileHandle(String fileName) {
		File file = new File(new File("").getAbsolutePath() + fileName);
		return new FileHandle(file);
	}
}
