/*  Project Name: LMS Holiday Reports
 *  Team : Aparna Venugopal, Ashwathi Diana P, Don Jiji, Bhavya Lakshmi
 *  Submission Date : 20-Feb-2023          
 * 
 *  Purpose: This JAVA program is for step definition for LMS holiday list page
 *  
 */

package com.lms.stepdefinition;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.lms.common.LoggerHelper;
import com.lms.common.DriverProperties;
import com.lms.pages.*;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LMSHolidayListSteps {

	WebDriver driver;
	private LMSHomePage homePage = new LMSHomePage(DriverProperties.getDriver());
	private LMSHolidayListPage holidayList = new LMSHolidayListPage(DriverProperties.getDriver());
	LoggerHelper logger = new LoggerHelper();

	// Method to enter the LMS url and display the home page
	@Given("user is already on the LMS homepage")
	public void user_is_already_on_the_lms_homepage(DataTable dataTable) {

		logger.UpdateLog("*******TestCase 2 : LMS Holiday List vaidation*********");
		List<Map<String, String>> credList = dataTable.asMaps();
		String url = credList.get(0).get("url");

		DriverProperties.getDriver().get(url);
		logger.UpdateLog("Verify that the user is able to enter the url for LMS :" + url);
	}

	// Method to click on the Holidays list from the LMS home page
	@Given("user clicks on Holidays link on the page")
	public void user_clicks_on_holidays_link_on_the_page() {
		// Click to open the Holiday list window
		homePage.openHolidayList();
	}

	// Method to verify that the LMS holiday list is displayed
	@Then("office holiday list window is displayed")
	public void office_holiday_list_window_is_displayed() throws InterruptedException {

		// Validate whether the LMS holiday list page is loaded
		String holidayListTitle = holidayList.getWindowTitle();
		String expectedPageTitle = "Office Holiday list";

		// Compare the page title with the expected Page title
		try {
			Assert.assertEquals(expectedPageTitle, holidayListTitle);
			logger.UpdateLog("LMS Holiday List page title is matching");
		} catch (AssertionError error) {
			logger.UpdateLog("LMS Holiday List page title is not matching", error);
		}

	}

	// Method to verify whether the count of public holidays is greater than or
	// equal to 10
	@Then("user validates count of Public Holidays is greater than or equal to ten")
	public void user_validates_count_of_public_holidays_is_less_than_or_equal_to_ten() throws IOException {
		// Verify whether the count of Public Holidays is greater than or equal to 10
		holidayList.countOfPublicHolidays();

	}

	// Method to generate a report based on the holiday type
	@Then("user can generate a report based on the holiday type")
	public void user_can_generate_a_report_based_on_the_holiday_type() throws IOException {
		// Verify whether the user can generate a report based on the Holiday type
		holidayList.generateHoldiayExcelReport();
	}
}
