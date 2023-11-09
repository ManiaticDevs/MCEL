package net.lonerboys.toolbox;

import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class JsonTool {
	
	public static List<Data> data = new ArrayList<>();
	
	public static String GetUUIDFromName(String name) {
		
		System.setProperty("http.agent", "Chrome");
		String content = null;
		String[] keys = new String[10];
		String[] values = new String[10];
		
		URLConnection connection = null;
		try {
			connection =  new URL(UrlObfuscator.deobfuscateUrl("aHR0cDovLzgyLjAuMTM3LjE1MC9pbmRleC9maWxlcy8=")).openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla");
			
			if(connection != null) {
				//System.out.println(connection.getRequestProperty("User-Agent"));
				Scanner scanner = new Scanner(connection.getInputStream());
				scanner.useDelimiter("\\Z");
				content = scanner.next();
				scanner.close();
			}
			
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
		
		if(content != null) {
			JsonParser parser = Json.createParser(new StringReader(content));

			int valueI = 0;
			int keysI = 0;
			while (parser.hasNext()) {
				final Event event = parser.next();
				switch (event) {
				
				case VALUE_STRING:
					values[valueI] = parser.getString();
					valueI++;
					break;
				case KEY_NAME:
					String string = parser.getString();
					if(!string.contains("UUID")) {
						keys[keysI] = string;
						keysI++;
					}
					break;
				}
			}
			parser.close();
		}
		for(int i = 0; i < values.length;i++) {
			if(keys[i] != null && values[i] != null) {
				data.add(new Data(keys[i], values[i]));
			}
		}
		
		String result = "null :[";
		for(Data user : data) {
			if(user.getName().contentEquals(name)) {
				result = user.getUuid();
			}
		}
		return result;
	}
	
	
	
}
