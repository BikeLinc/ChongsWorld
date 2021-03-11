package userinterface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

import entities.Camera;
import inventory.Inventory;
import utils.AssetManager;

public class UIManager {

	float xStart = 0;
	float yStart = 0;
	
	BitmapFont font;
	Sprite background;
	Sprite slot;
	
	public void loadFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(AssetManager.getFileHandle("/ui/wayfarers.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 7; // Sets Font Size
		font = generator.generateFont(parameter);
	}
	
	public void update(Camera camera) {
		Vector3 pos = camera.unproject(new Vector3(0, 0, 0));
		xStart = pos.x;
		yStart = pos.y;
	}
	
	public void drawHUD(SpriteBatch batch, float x, float y, float width, float height, Inventory inventory, int selItem) {
		for(int slotNo = 0; slotNo < 6; slotNo++) {
			if(selItem == slotNo) {
				batch.setColor(Color.WHITE);
			} else {
				batch.setColor(Color.LIGHT_GRAY);
			}
			batch.draw(AssetManager.getSprite("/ui/inventorySlot.png"), xStart + x, yStart - (y * (slotNo + 1)) - (height * (slotNo + 1)), width, height);
		}
		if(!inventory.isEmpty()) {
			for(int i = 0; i < inventory.getSize() && i < 6; i++) {
				int id = inventory.getItemByIndex(i).getType().getId();
				int tileY =	(id -1)/6;
				int tileX = (id -1) - (tileY * 6);
				batch.setColor(Color.WHITE);
				if(i == 0) {
					batch.draw(AssetManager.getTiles()[tileY][tileX], xStart + x + 8, yStart - (y) - (height) + 8, 16, 16);
					drawText(batch, inventory.getItemByIndex(i).getNumber(), Color.WHITE, x - 3,(height) - 10);
				} else {
					batch.draw(AssetManager.getTiles()[tileY][tileX], xStart + x + 8, yStart - (y * (i + 1)) - (height * (i + 1)) + 8, 16, 16);
					drawText(batch, inventory.getItemByIndex(i).getNumber(), Color.WHITE, x - 3,(y * (i + 1)) + (height * (i + 1)) - y - 10);
				}
			}
		}
	}
	
	public void drawInventory(SpriteBatch batch, float x, float y, float width, float height) {
		batch.draw(AssetManager.getSprite("/ui/inventoryMain.png"), xStart + x, yStart - y - height);
	}
	
	
	public void drawTextWithBackground(SpriteBatch batch, String text, Color foregroundColor, Color backgroundColor, float x, float y, float margin) {
		batch.setColor(backgroundColor);
		drawBG(batch, x, y, getTextWidth(text) + (margin * 2), getTextHeight(text) + (margin * 2));
		batch.setColor(Color.WHITE);
		drawText(batch, text, foregroundColor, x + margin, y + margin);
	}
	
	public void drawText(SpriteBatch batch, int text, Color color, float x, float y) {
		font.setColor(color);
		font.draw(batch, Integer.toString(text),  xStart + x, yStart - y);
	}
	
	public void drawText(SpriteBatch batch, String text, Color color, float x, float y) {
		font.setColor(color);
		font.draw(batch, text,  xStart + x, yStart - y);
	}
	
	public void drawBG(SpriteBatch batch, float x, float y, float width, float height) {
		batch.draw(AssetManager.getSprite("/ui/bg.png"), xStart + x, yStart - y - height, width, height);
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
