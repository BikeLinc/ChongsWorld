package userinterface;

import java.io.File;

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
		File fontFile = new File(new File("").getAbsolutePath() + "/ui/wayfarers.ttf");
		FileHandle fontFileHandle = new FileHandle(fontFile);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFileHandle);
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 7;
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
	
	public void drawPlayerInformation(SpriteBatch batch, String health, String[] inventory, int selItem) {
		Color bg = Color.LIGHT_GRAY;
		Color color = Color.MAROON;
		
		// Draws Health
		drawTextWithBackground(batch, "Health " + health, color, bg, 15, 20, 5);
		
		color = Color.BLACK;
		float xOffset = 0;
		// Draws Inventory
		for(String str : inventory) {
			System.out.println(str + " " + inventory[selItem]);
			if(str != "" && str != null) {
				if(str == inventory[selItem]) {
					bg = Color.WHITE;
				} else {
					bg = Color.LIGHT_GRAY;
				}
				if(str == inventory[0]) { // First Inventory Slot
					drawTextWithBackground(batch, str, color, bg, 15, 430, 5);
					xOffset += 15 + getTextWidth(str);
				} else {				  // Non-First Inventory Slot
					drawTextWithBackground(batch, str, color, bg, 20 + xOffset, 430, 5);
					xOffset += 20 + getTextWidth(str);
				}
			}
		}
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
