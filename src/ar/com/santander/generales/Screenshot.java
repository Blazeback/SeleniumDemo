package ar.com.santander.generales;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {

	static WebDriver d;
	
	public Screenshot(WebDriver driver) {
		d = driver;
	}
	
	public static void captureScreenshot(String pictureName, String caseName, String type) { 
		
		try {
			File scrFile = ((TakesScreenshot)d).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("Reports\\Screenshots\\" + caseName + "\\" + type + "\\" + pictureName + ".png"));
		} catch (IOException e) {
			System.err.println("Error when making a picture.");
		}
		
	}

}
