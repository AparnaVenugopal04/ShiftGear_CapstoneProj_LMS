/*  Project Name: LMS Holiday Reports
 *  Team : Aparna Venugopal, Ashwathi Diana P, Don Jiji, Bhavya Lakshmi
 *  Submission Date : 20-Feb-2023          
 * 
 *  Purpose: This JAVA program is to set and get the values for the driver based on the browser selected
 *  
 */


package com.lms.common;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverProperties {
	
	public WebDriver driver;
	LoggerHelper logger = new LoggerHelper();
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();


	public WebDriver init_driver(String browser) {

		System.out.println("browser value is: " + browser);
        
		//Setting the web driver based on the browser chosen
		if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver());
			logger.UpdateLog("Initiated Chrome driver");
		} else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver());
			logger.UpdateLog("Initiated Firefox driver");
		} else if (browser.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			tlDriver.set(new EdgeDriver());
			logger.UpdateLog("Initiated Edge driver");
		} else {
			System.out.println("Please pass the correct browser value: " + browser);
			logger.UpdateLog("Invalid browser type");
		}

		//Maximising the browser window
		getDriver().manage().window().maximize();
		return getDriver();

	}
	
 public static WebDriver getDriver()
 {
	 //Getting the value of the web driver
	 return tlDriver.get();
 }
}
