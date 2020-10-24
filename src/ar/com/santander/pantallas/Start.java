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

public class Start {
	
	private Map<String, String> screen;
	private Map<String,Map<String,String>> data;
	
	public Start() {
		screen = Excel.getData("Start","Screens"); // Xpaths for this screen, taken from the Screens Excel.
		data = Excel.retrieveLongData("Data", Launcher.getSheet()); // Entry data from the Data Excel (ID and attributes)
		System.out.println(screen.toString()); // Xpaths for this screen printed on the console.
	}
		
	public void writeAlexa(String y, String caseName) throws IOException { // There's a picture and a new record for the report in case it fails.
		
		try { 
			
			Cases.getLogger().info("Writing the text '" + data.get(y).get("USER").trim() + "' from the Excel...");
			Utilities.sendKeysXpath(screen.get("textBox"), data.get(y).get("USER").trim());
			Cases.getLogger().pass("The text '" + data.get(y).get("USER").trim() + "' has been written.");
			
		} catch (Exception e) {
			
			Cases.getLogger().fail("The text " + data.get(y).get("USER").trim() + "' can't be written.");
			Utilities.picture("Error textBox", caseName, "fail", y);
			
		}
		
	}
		
	public void clickSearch(String y, String caseName) throws IOException {
		
		try {
			
			Cases.getLogger().info("Clicking on search...");
			Utilities.clickXpath(screen.get("searchButton"));
			Cases.getLogger().pass("The search button has been clicked on.");
			
		} catch (Exception e) {
			
			Cases.getLogger().fail("The search button couldn't be clicked on.");
			Utilities.picture("Error search button", caseName, "fail", y);
			
		}
		
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
	
	public void skipTodaysDeals(String y, String caseName) throws IOException {
		
		try {
			
			Cases.getLogger().info("Clicking on the third item (the Today's Deals have been skipped)...");
			
			Utilities.swipeToXpath(screen.get("todaysDealThirdItem"));
			Utilities.picture("Third item", caseName, "info", y);
			
			Utilities.clickXpath(screen.get("todaysDealThirdItem"));
			Cases.getLogger().pass("The third item has been clicked on.");
			
		} catch (Exception e) {
			
			Cases.getLogger().fail("The third item couldn't be clicked on.");
			Utilities.picture("Error click third item", caseName, "fail", y);
			
		}
		
	}
		
	public boolean checkForTodaysDeals(String y, String caseName) throws IOException {
		
		try {
			
			Utilities.recognizeXpathQuickly(screen.get("checkTodaysDeals"));
			return true;
			
		} catch (Exception e) {
			
			return false;
			
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
