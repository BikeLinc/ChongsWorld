package gameMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class MapLoader {

	private static Json json = new Json();
	private static final int SIZE = 100;
	
	/**
	 * Loads Map From FileName.
	 * Loads Existing Map If Directory Is Present, If Not Generates New Map
	 * @param id
	 * @param name
	 * @return MapData
	 */
	public static MapData loadMap(String id, String name) {
		Gdx.files.local("maps/").file().mkdirs();
		FileHandle file = Gdx.files.local("maps/" + id + ".chongo");
		if (file.exists()) {
			MapData data = json.fromJson(MapData.class, file.readString());
			return data;
		} else {
			MapData data = MapGenerator.generateRandomMap(id, name, SIZE);
			saveMap(data);
			return data;
		}
	}
	
	/**
	 * Saves MapData
	 * @param id
	 * @param name
	 * @param map
	 */
	public static void saveMap(MapData data) {
		Gdx.files.local("maps/").file().mkdirs();
		FileHandle file = Gdx.files.local("maps/" + data.id + ".chongo");
		file.writeString(json.prettyPrint(data), false);
	}
}