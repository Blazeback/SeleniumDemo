package ar.com.santander.pantallas;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import ar.com.santander.casos.Cases;
import ar.com.santander.generales.Excel;
import ar.com.santander.generales.Screenshot;
import ar.com.santander.generales.Utilities;
import ar.com.santander.launcher.Launcher;

public class Item {
	
	private Map<String, String> screen;
	private Map<String,Map<String,String>> data;
	
	public Item() {
		screen = Excel.getData("Item","Screens"); // Xpaths for this screen, taken from the Screens Excel.
		data = Excel.retrieveLongData("Data", Launcher.getSheet()); // Entry data from the Data Excel (ID and attributes)
		System.out.println(screen.toString()); // Xpaths for this screen printed on the console.
	}
	
	public void clickThirdItem(String y, String caseName) throws IOException {
		
		try {
			
			Cases.getLogger().info("Clicking on the third item...");
			
			Utilities.swipeToXpath(screen.get("thirdItem"));
			Utilities.picture("Third item", caseName, "info", y);
			
			Utilities.clickXpath(screen.get("thirdItem"));
			Cases.getLogger().pass("The third item has been clicked on.");
			
		} catch (Exception e) {
			
			Cases.getLogger().fail("The third item couldn't be clicked on.");
			Utilities.picture("Error click third item", caseName, "fail", y);
			
		}
		
	}
	
	public void checkAlexaAvailability(String y, String caseName) throws IOException {
		
		try {
			
			Utilities.recognizeXpathQuickly(screen.get("stockWarning"));
			
			Cases.getLogger().fail("There's no stock for this item.");
			Utilities.picture("Stock error", caseName, "fail", y);
			
		} catch (Exception e) {
			
			try {
				
				Utilities.recognizeXpathQuickly(screen.get("shippingWarning"));
				
				Cases.getLogger().fail("There's no shipping option for this item.");
				Utilities.picture("Shipping error", caseName, "fail", y);
				
			} catch (Exception e1) {
				
				Cases.getLogger().pass("This item is available for purchase!.");
				
			}
			
		}

	}
		
}
