/*  Project Name: LMS Holiday Reports
 *  Team : Aparna Venugopal, Ashwathi Diana P, Don Jiji, Bhavya Lakshmi
 *  Submission Date : 20-Feb-2023          
 * 
 *  Purpose: This JAVA program is for all the steps to be performed on the LMS home page
 *  
 */

package com.lms.stepdefinition;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import com.lms.common.LoggerHelper;
import com.lms.common.DriverProperties;
import com.lms.pages.LMSHomePage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LMSHomePageSteps {
	// Declare the variable for WebDriver
	WebDriver driver;
	// Instantiate the driver
	private LMSHomePage homePage = new LMSHomePage(DriverProperties.getDriver());
	LoggerHelper logger = new LoggerHelper();
	String expectedPageTitle = "LMS | Leave Management System- Home";

	// Method to enter the LMS url and display the home page
	@Given("user enters valid {string}")
	public void user_enters_valid(String url) {
		logger.UpdateLog("*******TestCase 1 : LMS Homepage vaidation*********");
		DriverProperties.getDriver().get(url);
		logger.UpdateLog("Verify that the user is able to enter the url for LMS :" + url);

	}

	// Method to verify whether the LMS homepage is displayed correctly
	@Then("LMS homepage is displayed")
	public void lms_homepage_is_displayed() {
		// Validate whether the LMS home page is loaded
		String actualPageTitle = homePage.validatePageHeading();

		// Compare the page title with the expected Page title
		try {
			Assert.assertEquals(expectedPageTitle, actualPageTitle);
			logger.UpdateLog("LMS HomePage title is matching as expected");
			logger.UpdateLog("TestCase 1 : LMS Homepage validation - PASS");
		} catch (AssertionError error) {
			logger.UpdateLog("TestCase 1 : LMS Homepage vaidation", error);
		}
	}
}
