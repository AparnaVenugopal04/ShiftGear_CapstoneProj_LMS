/*  Project Name: LMS Holiday Reports
 *  Team : Aparna Venugopal, Ashwathi Diana P, Don Jiji, Bhavya Lakshmi
 *  Submission Date : 20-Feb-2023          
 * 
 *  Purpose: This JAVA program is to capture the execution steps in the log file
 *  
 */


package com.lms.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerHelper {

	//Setting the logger object
	private static final Logger logger = LogManager.getLogger(LoggerHelper.class);

	
	//Method to log messages
	public void UpdateLog(String message)
    {
        logger.info(message);
    }
	
	//Method to log messages and assertion exception
	public void UpdateLog(String message,AssertionError e)
    {
        logger.error(message,e);
    }
	
	//Method to log messages and any exception
	public void UpdateLog(String message,Exception exception)
    {
        logger.error(message,exception);
    }
}
