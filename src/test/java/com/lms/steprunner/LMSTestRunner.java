/*  Project Name: LMS Holiday Reports
 *  Team : Aparna Venugopal, Ashwathi Diana P, Don Jiji, Bhavya Lakshmi
 *  Submission Date : 20-Feb-2023          
 * 
 *  Purpose: This JAVA program is the runner file for the framework
 */


package com.lms.steprunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions
	(features = { "src/test/resources/Features/" }, 
		glue = { "com.lms.stepdefinition","com.lms.common" }, 
		plugin = { "pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" }, 
		monochrome = true)

public class LMSTestRunner {
	
}
