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

public class Search {
	
	private Map<String, String> screen;
	private Map<String,Map<String,String>> data;
	
	public Search() {
		screen = Excel.getData("Search","Screens"); // Xpaths for this screen, taken from the Screens Excel.
		data = Excel.retrieveLongData("Data", Launcher.getSheet()); // Entry data from the Data Excel (ID and attributes)
		System.out.println(screen.toString()); // Xpaths for this screen printed on the console.
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
		
}
