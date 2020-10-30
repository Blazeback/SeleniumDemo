package ar.com.santander.casos;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.MediaEntityBuilder;

import ar.com.santander.generales.Excel;
import ar.com.santander.generales.Screenshot;
import ar.com.santander.generales.Utilities;
import ar.com.santander.launcher.Launcher;
import ar.com.santander.pantallas.Start;

public class CaseDemo extends Cases {
	
	private static String timeStamp;
	
	public static void test(Map<String,Map<String,String>> datos, String caseName) throws InterruptedException, IOException {
		
		System.out.println("****************************************");
		System.out.println("CASE: DEMO");
		
		try {
			
			for (int i = 1; i <= Excel.rowAmount("Data" , Launcher.getSheet()); i++) { // Gets the amount of rows from the Data Excel, I usually automate applications with big amounts of entry data (this is reflected on the case report, it records and separates each iteration), in this case we only have a single row.
				
				String y = Integer.toString(i); // ID of the row.
				
				if (i > 1 && i <= Excel.rowAmount("Data" , Launcher.getSheet())) { // Reloads the page each time an iteration is done.
					
					Launcher.reloadPage();
					extent.flush(); // Each time the flush() function is called, the report gets new data.
					
				}
				
				logger = extent.createTest("ID: " + datos.get(y).get("ID").trim() + " - Name: " + datos.get(y).get("USER").trim()); // Records the iteration on the report.
				
				logger.info("Browser: " + Launcher.getBrowser());
				
				timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				logger.info("Timestamp: " + timeStamp); // Records a timestamp on the report.
				
				try {
					
					screen.start.writeAlexa(y, caseName); // You can read what the program does in an easy way.
					
					screen.start.clickSearch(y ,caseName);
					
					Utilities.picture("Items", caseName, "info", y); // A picture for the report.
					
					if (screen.search.checkForTodaysDeals(y, caseName)) { 
						
						screen.search.skipTodaysDeals(y, caseName); // Clicks on the proper third item in case the "Today's Deals" show up.
						
					} else {
						
						screen.item.clickThirdItem(y, caseName);
						
					}
					
					screen.item.checkAlexaAvailability(y, caseName);
					
					Utilities.picture("Data of the third item", caseName, "info", y);
					
				} catch (Exception e) {

					Cases.getLogger().fail("Webpage error");
					Utilities.picture("Web error", caseName, "fail", y);
					
				}

			}
			
		} catch (Exception e) {
			System.out.println("EXCEL ERROR " + e);
			Cases.getLogger().fail("Error when retrieving Excel data.");
			
		}	
		
		timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		logger.info("Timestamp: " + timeStamp);
		
		extent.flush();
		
	}
	
}
