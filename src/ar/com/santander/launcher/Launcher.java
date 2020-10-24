package ar.com.santander.launcher;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import ar.com.santander.casos.Cases;
import ar.com.santander.configuraciones.Configuration;
import ar.com.santander.generales.Logs;
import ar.com.santander.generales.Screenshot;
import ar.com.santander.generales.Utilities;
import ar.com.santander.pantallas.Screen;

public class Launcher {

	private static WebDriver d;
	private static Configuration config = new Configuration();  // The Configuration Excel, where we can set the browser, webpage and what cases to run.
	private static Cases cases = new Cases(); // The Case class from which all cases inherit and get all of the necessary common attributes.
	private static DesiredCapabilities capabilities = new DesiredCapabilities();
	
	public static void main(String[] args) throws IOException, InterruptedException, AWTException {
		
		Logs.generateLog(); // A log located in the "logs" folder, it saves everything printed to the console.
		
		closeDriverTasks(); // This is useful for ending the selenium browser tasks, they don't close by themselves.
		
		switch (config.getBrowser()) {
		
			case "Chrome":
				System.setProperty("webdriver.chrome.driver", "Driver\\Chrome\\chromedriver.exe"); // I put the libraries, extensions and drivers along with the project. I know that it's not very efficient and that I should figure out something better, maybe put it in the repository and use Maven for the libraries.
				ChromeOptions options = new ChromeOptions();
//				options.addExtensions(new File("Extensions\\Chrome\\ChroPath\\6.1.9_0.crx")); // Extensions for Chrome in case I want to use some.
				options.addArguments("--lang=en"); // I set the browser language to English.
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setAcceptInsecureCerts(true); // This is useful in case one tries to automate a web page that is in develped and doesn't yet have a proper certificate.
				d = new ChromeDriver(capabilities);
				executeMain();
				
			break;	
			
			case "Explorer":
				System.setProperty("webdriver.ie.driver", "Driver\\Explorer\\IEDriverServer.exe");
				d = new InternetExplorerDriver(capabilities);
				executeMain();
			break;
			
			default:
				Cases.getLogger().fail("Error in the 'Configuration' Excel.");
			break;
		
		}
		
		JDialog dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
		
		int answer = JOptionPane.showConfirmDialog(dialog, "Close the browser?");
		if (JOptionPane.OK_OPTION == answer){
			
			d.close();
		
		}
		
		closeDriverTasks();
		
		System.exit(1); // Fully ends the program.
		
	}
	
	public static WebDriver getDriver() {
		return d;
	}
	
	public static void executeMain() throws InterruptedException, IOException, AWTException {
		
		d.get(config.getWebPage()); // Opens the page.
		
		d.manage().window().maximize();
		
		new Screen(); // Creates all of the screens (in this case we only have a single one), it helps to keep them organized.
		
		new Screenshot(d); // Screenshot class for the reports, one can also see them inside of the "Reports" folder.
		
		new Utilities(d); // Various utilities.
		
		cases.executeCases(config.getCase());
		
	}
	
	public static void reloadPage() {
		
		d.get(config.getWebPage());
		
		d.manage().window().maximize();
		
	}
	
	public static void closeBrowser() {
		
		d.close();
		
	}
	
	public static void closeDriverTasks() throws IOException {
		
		Runtime.getRuntime().exec("taskkill /IM chromedriver.exe /F");
		Runtime.getRuntime().exec("taskkill /IM IEDriverServer.exe /F");
		
	}
	
	public static String getSheet() {
		
		return config.getCase();
		
	}
	
	public static String getBrowser() {
		
		return config.getBrowser();
		
	}
	
}
