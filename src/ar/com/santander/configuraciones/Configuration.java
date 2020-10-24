package ar.com.santander.configuraciones;

import java.util.Map;

import ar.com.santander.generales.Excel;


public class Configuration {
	Map<String, String> data;
	public Configuration() {
		data = Excel.getData("Data", "Configuration"); // This retrieves the data from the Configuration Excel and puts it in a Hashmap, they can be accessed to by their name.
	}

	public String getWebPage() {
		return data.get("webPage");
	}
	
	public String getBrowser() {
		return data.get("browser");
	}

	public String getCase() {
		return data.get("sheet");
	}
	
}
