package game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.Camera;
import gameMap.Map;
import inventory.Inventory;
import userinterface.UIManager;

public class MainGameLoop extends ApplicationAdapter {
	
	UIManager ui = new UIManager();
	
	float seconds = 0;
	
	SpriteBatch batch;
	Camera camera;
	Map map;
	
	
	
	@Override
	public void create() {
		ui.loadFont();
		ui.loadTextures();
		
		batch = new SpriteBatch();
		camera = new Camera();
		map = new Map();
	}

	@Override
	public void render() {
		long start = System.nanoTime();
		float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f);
		
		clear();
		
		// World Update
		camera.update(map);
		map.getPlayer().build(camera, map);
		map.update(delta);
		map.render(camera, batch);
				
		// UI Update
		batch.begin();
		ui.update(camera);
		ui.drawPlayerInformation(batch, Integer.toString(map.getPlayer().health), map.getPlayer().inventory.getInventoryAsLabeledStringArray(), map.getPlayer().selItem);
		batch.end();
		
		
		if(seconds > 25) { 
			map.getPlayer().animate();
			seconds = 0;
		}
		seconds += ((System.nanoTime() - start)/1000000.0);
	}
	
	private void clear() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	@Override
	public void dispose() {
		
		map.dispose();
		batch.dispose();
	}
}
