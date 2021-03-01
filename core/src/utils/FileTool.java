package utils;

import java.io.File;

public class FileTool {

	public static String getPath() {
		File file = new File("");
		String path = file.getAbsolutePath().substring(0, file.getAbsoluteFile().toString().length() - 7) + "core/";
		return path;
	}
	
	
}
