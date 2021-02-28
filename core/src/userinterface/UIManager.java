package userinterface;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

import entities.Camera;

public class UIManager {

	float xStart = 0;
	float yStart = 0;
	
	BitmapFont font;
	Sprite background;
	
	public void loadFont() {
//		File fontFile = new File(new File("").getAbsolutePath() + "/ui/mcfont18.fnt");
//		FileHandle fontFileHandle = new FileHandle(fontFile);
//		File texFile = new File(new File("").getAbsolutePath() + "/ui/mcfont18.png");
//		FileHandle texFileHandle = new FileHandle(texFile);
//		font = new BitmapFont(fontFileHandle,texFileHandle,false);
//		font.setColor(1,0,0,1);
		File fontFile = new File(new File("").getAbsolutePath() + "/ui/Minecraft.ttf");
		FileHandle fontFileHandle = new FileHandle(fontFile);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFileHandle);
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 18;
		font = generator.generateFont(parameter);
	}
	
	public void loadTextures() {
		File texFile = new File(new File("").getAbsolutePath() + "/ui/bg.png");
		FileHandle texFileHandle = new FileHandle(texFile);
		Texture texture = new Texture(texFileHandle);
		background = new Sprite(texture);
	}
	
	public void update(Camera camera) {
		Vector3 pos = camera.unproject(new Vector3(0, 0, 0));
		xStart = pos.x;
		yStart = pos.y;
	}
	
	public void drawPlayerInformation(SpriteBatch batch, String health, Color color1, int[] inventory, int selected) {
		float x = 0;
		drawTextWithBackground(batch, "Health " +health, color1, Color.LIGHT_GRAY, 15, 20, 5);
		
		String[] inv = readInventory(inventory);
		for(int i = 0; i < inv.length; i++) {
			if(inv[i] != "") {
				Color color = Color.BLACK;
				Color bg = Color.LIGHT_GRAY;
				
				if(i == selected) {
					bg = Color.WHITE;
				}
				
				if(i == 0) {
					color = Color.GREEN;
				} 
				if(i == 1) {
					color = Color.BROWN;
				}
				if(i == 2) {
					color = Color.DARK_GRAY;
				}
				if(i == 3) {
					color = Color.GOLD;
				}
				if(i == 4) {
					color = Color.CORAL;
				}
				if(i == 5) {
					color = Color.GREEN;
				}
				if(i == 6) {
					color = Color.MAROON;
				}
				
				if(i == 0) {
					drawTextWithBackground(batch, inv[i], color, Color.LIGHT_GRAY, 15, 430, 5);
					x += 15 + getTextWidth(inv[i]);
				} else {
					drawTextWithBackground(batch, inv[i], color, Color.LIGHT_GRAY, 20 + x, 430, 5);
					x += 20 + getTextWidth(inv[i]);
				}
			}
		}
	}
	
	public String[] readInventory(int[] inventory) {
		String[] str = new String[inventory.length];
		for(int i = 0; i < inventory.length; i++) {
			if(inventory[i] != 0) {
				if(i == 0) {
					str[i] = inventory[i] + " Grass";
				} 
				if(i == 1) {
					str[i] = inventory[i] + " Dirt";
				}
				if(i == 2) {
					str[i] = inventory[i] + " Stone";
				}
				if(i == 3) {
					str[i] = inventory[i] + " Wood";
				}
				if(i == 4) {
					str[i] = inventory[i] + " Mango";
				}
				if(i == 5) {
					str[i] = inventory[i] + " Leaf";
				}
				if(i == 6) {
					str[i] = inventory[i] + " Lava";
				}
			} else {
				str[i] = "";
			}
		}
		return str;
	}
	
	public void drawTextWithBackground(SpriteBatch batch, String text, Color foregroundColor, Color backgroundColor, float x, float y, float margin) {
		batch.setColor(backgroundColor);
		drawBG(batch, x, y, getTextWidth(text) + (margin * 2), getTextHeight(text) + (margin * 2));
		batch.setColor(Color.WHITE);
		drawText(batch, text, foregroundColor, x + margin, y + margin);
	}
	
	public void drawText(SpriteBatch batch, String text, Color color, float x, float y) {
		font.setColor(color);
		font.draw(batch, text,  xStart + x, yStart - y);
	}
	
	public void drawBG(SpriteBatch batch, float x, float y, float width, float height) {
		batch.draw(background, xStart + x, yStart - y - height, width, height);
	}
	
	public float getTextWidth(String text) {
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, text);
		return layout.width;
	}
	
	public float getTextHeight(String text) {
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, text);
		return layout.height;
	}
	
}
