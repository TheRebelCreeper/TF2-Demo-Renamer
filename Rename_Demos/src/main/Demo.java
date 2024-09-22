package main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

public class Demo
{

	private String mapName = "";
	private String path;
	private String name = "";
	
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
		
		for (File demo: rootDir.listFiles())
		{
			System.out.println(demo.getName());
			// If the file name has no letters except for the extension
			if (demo.getName().contains(".dem")&& Pattern.matches("([a-zA-Z]*)(_?)([^a-zA-Z]+)", demo.getName().replace(".dem", "")))
			{
				mapName = readDemo(demo);
				name = demo.getName().replace(".dem", "");
				File newDemo = new File(path + name + "_" + mapName + ".dem");
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
			name = json.getName().replace(".json", "");
			File newJson = new File(path + name + "_" + mapName + ".json");
			return json.renameTo(newJson);
		}
	}
	
	private String readDemo(File demo) throws IOException
	{
		String tmp = "";
		FileReader file = new FileReader(demo);
		char[] cbuf = new char[65536];
		System.out.println("START");
		while (file.read(cbuf, 0, 65536) != -1)
		{
			tmp = new String(cbuf);
			if (tmp.contains(".bsp"))
			{
				file.close();
				return tmp.substring(tmp.indexOf("maps\\") + 5, tmp.indexOf(".bsp"));
			}
		}
		file.close();
		return "error";
	}
}
