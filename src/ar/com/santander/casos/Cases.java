package ar.com.santander.casos;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.JOptionPane;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import ar.com.santander.generales.Excel;
import ar.com.santander.launcher.Launcher;
import ar.com.santander.pantallas.Screen;

public class Cases {
		
		static WebDriver d;
		static Actions actions;
		static Screen screen = new Screen(); // The Screen class from which all screens inherit and get all of the necessary common attributes.
		protected static ExtentHtmlReporter reporter; // The reporter and extent tool are used to make the .html web report of every case.
	    protected static ExtentReports extent;
	    protected static ExtentTest logger;
	    static Robot r; // I used the Robot and Pointer classes in other projects, along with many other utilities and classes that I developed. I deleted most of them for this demo.
		static Point pointer;
		
		protected static Map<String,Map<String,String>> data; // Data map used for every test, it will contain the IDs and entry data from the Excel.
		
	public void executeCases(String sheet) throws InterruptedException, IOException, AWTException {
		
		d = Launcher.getDriver();
		actions = new Actions(d);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		reporter = new ExtentHtmlReporter("Reports\\Report_" + sheet + "_" + dateFormat.format(date).toString() + ".html"); // Creating the report.
		
        reporter.config().setDocumentTitle("KinCarta");
        reporter.config().setReportName("Demo");
        reporter.config().setEncoding("ANSI");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        r = new Robot();
        pointer = new Point(300, 300);
        
        switch (sheet) { // Here we choose a single case from the Configuration Excel, I could also change it to take many cases at once.
        	
        	case "Demo":
        		data = Excel.retrieveLongData("Data", "Demo"); // This retrieves the data from the Configuration Excel and puts it in a Hashmap, they can be accessed to by their ID and attribute name.
        		CaseDemo.test(data, sheet);
        	break;
        	
        	default:
        		Cases.getLogger().fail("Error in the 'Configuration' Excel (non-existent case).");
        	break;
        
        }
		 
	}
	
	public static ExtentTest getLogger() { // The logger getter for the cases that inherit from the "Cases" class.
		return logger;
	}

}
