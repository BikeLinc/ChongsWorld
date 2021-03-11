package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import gameMap.GameMap;

public class Camera extends OrthographicCamera{
	
	float width;
	float height;
	
	public Camera() {
		setup();
	}
	
	/**
	 * Camera Setup Prior To Rendering
	 */
	public void setup() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		this.setToOrtho(false, width, height);
		this.update();
	}
	
	/**
	 * Update Cameras Position Based On Players Location
	 * @param map
	 */
	public void followPlayer(GameMap map) {
		this.position.set(new Vector3(map.getPlayer().getPos().x, map.getPlayer().getPos().y, 0));
	}
	
	public void update(GameMap map) {
		float newWidth = Gdx.graphics.getWidth();
		float newHeight = Gdx.graphics.getHeight();
		if(newWidth != width || newHeight != height) {
			this.setToOrtho(false, newWidth / 4, newHeight / 4);
		}
		height = newHeight;
		width = newWidth;
		followPlayer(map);
		super.update();
	}
	
		
	
}
