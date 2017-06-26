package main;

import java.io.File;
import java.io.FileReader;
import java.net.URLDecoder;
import java.util.regex.Pattern;

public class Demo
{

	private String mapName = "";
	private String path;
	
	public Demo() throws Exception
	{
		start();
	}
	
	private void start() throws Exception
	{
		path = Demo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = URLDecoder.decode(path, "UTF-8");
	    path = path.substring(0, path.lastIndexOf("/")) + "/";
	    
		File rootDir = new File(path);
		
		FileReader file;
		
		for (File demo: rootDir.listFiles())
		{
			if (demo.getName().contains(".dem")&& Pattern.matches("[^a-zA-Z]+", demo.getName().replace(".dem", "")))
			{
				String tmp = "";
				file = new FileReader(demo);
				char[] cbuf = new char[4276];
				while(!tmp.contains(".bsp"))
				{
					file.read(cbuf, 0, 4276);
					tmp = new String(cbuf);
				}
				file.close();
				tmp = tmp.substring(tmp.indexOf("maps\\") + 5, tmp.indexOf(".bsp"));
				mapName = tmp;
				File newDemo = new File(path + (demo.getName().replace(".dem", "")) + "_" + mapName + ".dem");
				checkJson(demo, path);
				demo.renameTo(newDemo);
			}
		}
	}
	
	private boolean checkJson(File file, String path)
	{
		File json = new File(path + file.getName().replace(".dem", ".json"));
		if (json.exists() && !json.isDirectory());
		{
			File newJson = new File(path + (json.getName().replace(".json", "")) + "_" + mapName + ".json");
			return json.renameTo(newJson);
		}
	}
}
