package utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureLoader {

	public static Texture loadTexture(String path) {
		return new Texture(FileTool.getPath() + path);
	}
	
	public static TextureRegion[][] loadTextureAtlas(String path, int height, int width) {
		return TextureRegion.split(new Texture(FileTool.getPath() + path), height, width);
	}
	
}
