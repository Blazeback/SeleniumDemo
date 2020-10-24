package ar.com.santander.generales;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import ar.com.santander.casos.Cases;
import ar.com.santander.generales.Screenshot;

public class Utilities {

	protected static Actions actions;
	protected static WebDriver d;
		
	public Utilities(WebDriver driver) {
		
		d = driver;
		actions = new Actions(driver);
		
	}
	
	public static void recognizeXpath(String xpath) {	
		new WebDriverWait(d, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	}

	public static  void recognizeXpathQuickly(String xpath) {	
		new WebDriverWait(d, 2).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	}
	
	public static void clickXpath(String xpath) {
		recognizeXpath(xpath);
		d.findElement(By.xpath(xpath)).click();
	}
	
	public static void sendKeysXpath(String xpath, String keys) {
		recognizeXpath(xpath);
		WebElement element = d.findElement(By.xpath(xpath)); 
		element.sendKeys(keys);
	}

	public static void swipeToXpath(String xpath) {
		WebElement element = d.findElement(By.xpath(xpath));
		actions.moveToElement(element);
		actions.perform();
	}
	
	public static void picture(String pictureName, String caseName, String type, String ID) throws IOException {
		
		caseName = caseName.replace(" ", "");
		
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    		Date date = new Date();
			
			switch (type){
			
				case "info":
					Screenshot.captureScreenshot(pictureName + " -" + dateFormat.format(date).toString() + "- " + ID, caseName, "Passed");
					Cases.getLogger().info(pictureName + " -" + dateFormat.format(date).toString() + "- " + ID, MediaEntityBuilder.createScreenCaptureFromPath("Screenshots\\" + caseName + "\\Passed\\" + pictureName + " -" + dateFormat.format(date).toString() + "- " + ID + ".png").build());
					break;
				
				case "pass":
					Screenshot.captureScreenshot(pictureName + " -" + dateFormat.format(date).toString() + "- " + ID, caseName, "Passed");
					Cases.getLogger().pass(pictureName + " -" + dateFormat.format(date).toString() + "- " + ID, MediaEntityBuilder.createScreenCaptureFromPath("Screenshots\\" + caseName + "\\Passed\\" + pictureName + " -" + dateFormat.format(date).toString() + "- " + ID + ".png").build());
				break;
				
				case "fail":
					Screenshot.captureScreenshot(pictureName + " -" + dateFormat.format(date).toString() + "- " + ID, caseName, "Errors");
					Cases.getLogger().fail(pictureName + " -" + dateFormat.format(date).toString() + "- " + ID, MediaEntityBuilder.createScreenCaptureFromPath("Screenshots\\" + caseName + "\\Errors\\" + pictureName + " -" + dateFormat.format(date).toString() + "- " + ID + ".png").build());
				break;
				
				case "error":
					Screenshot.captureScreenshot(pictureName + " -" + dateFormat.format(date).toString() + "- " + ID, caseName, "Errors");
					Cases.getLogger().error(pictureName + " -" + dateFormat.format(date).toString() + "- " + ID, MediaEntityBuilder.createScreenCaptureFromPath("Screenshots\\" + caseName + "\\Errors\\" + pictureName + " -" + dateFormat.format(date).toString() + "- " + ID + ".png").build());
				break;
				
			}
			
		} catch (Exception e) {
			
			System.out.println("PICTURE ERROR");
			Cases.getLogger().error("Error when taking a picture.");
			System.out.println(e);
			
		}
		
	}
	
}
