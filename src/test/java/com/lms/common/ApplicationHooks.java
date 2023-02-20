/*  Project Name: LMS Holiday Reports
 *  Team : Aparna Venugopal, Ashwathi Diana P, Don Jiji, Bhavya Lakshmi
 *  Submission Date : 20-Feb-2023          
 * 
 *  Purpose: This JAVA program is for all the steps to be performed before an after a test run
 *  @Before - Before the test is run, config file is read and browser is set based on the value in the config
 *  @After - After the test is complete, quitting the browser and taking a screenshot
 *  
 */




package com.lms.common;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ApplicationHooks {

	//Declaring the objects for the java files
	private DriverProperties driverProp;
	private ConfigReader configReader;
	Properties prop;
	//LoggerHelper is used to log all the execution details
	LoggerHelper logger = new LoggerHelper();

	//Getting the values from the config file for the browser
	@Before(order = 0)
	public void getProperty() {
		configReader = new ConfigReader();
		prop = configReader.init_prop();
	}

	//Setting and getting the value of the browser driver
	@Before(order = 1)
	public void launchBrowser() {
		String browserName = prop.getProperty("browser");
		driverProp = new DriverProperties();
		driverProp.init_driver(browserName);

	}

	//Quitting the browser after the execution is complete
	@After(order = 0)
	public void quitBrowser() {
		driverProp.getDriver().quit();
		logger.UpdateLog("Closing the browser after test execution");
		logger.UpdateLog("*************************************************");
	}

	//Taking a screenshot of after the execution is complete
	@After(order = 1)
	public void tearDown(Scenario scenario) throws IOException {
		// take screenshot:
		String screenshotName = scenario.getName().replaceAll(" ", "_");

		byte[] sourcePath = ((TakesScreenshot) driverProp.getDriver()).getScreenshotAs(OutputType.BYTES);
		scenario.attach(sourcePath, "image/png", screenshotName);
		logger.UpdateLog("Screenshot captured for the test case");
	
		
	}
}
