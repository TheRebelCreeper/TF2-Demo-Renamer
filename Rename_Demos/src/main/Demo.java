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
			// If the file name has no letters except for the extension
			if (demo.getName().contains(".dem")&& Pattern.matches("([a-zA-Z]*)(_?)([^a-zA-Z]+)", demo.getName().replace(".dem", "")))
			{
				mapName = readDemo(demo);
				name = demo.getName().replace(".dem", "");
				File newDemo = new File(path + name + "_" + mapName + ".dem");
				//File newDemo = new File(path + TimeConvert(name) + "_" + mapName + ".dem");
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
			//File newJson = new File(path + TimeConvert(name) + "_" + mapName + ".json");
			return json.renameTo(newJson);
		}
	}
	
	private String TimeConvert(String tmp)
	{
		String date = tmp.substring(0, tmp.indexOf("_") + 1);	// YYYY-MM-DD_hh-mm-ss
		String hour = tmp.substring(tmp.indexOf("_") + 1, tmp.indexOf("_") + 3);
		String end = tmp.substring(tmp.indexOf("_") + 3);
		
		int intHour = new Integer(hour);
		
		String pm = (intHour < 12) ? "_am" : "_pm";
		
		if (intHour % 12 == 0)
			intHour = 12;
		else
			intHour %= 12;
		
		hour = "" + intHour;
		if (intHour < 10)
			hour = "0" + hour;
		
		return date + hour + end;
		//return date + hour + end + pm;
	}
	
	private String readDemo(File demo) throws IOException
	{
		String tmp = "";
		FileReader file = new FileReader(demo);
		char[] cbuf = new char[65536];
		while(!tmp.contains(".bsp"))
		{
			file.read(cbuf, 0, 65536);
			tmp = new String(cbuf);
		}
		file.close();
		return tmp.substring(tmp.indexOf("maps\\") + 5, tmp.indexOf(".bsp"));
	}
}
