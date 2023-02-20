/*  Project Name: LMS Holiday Reports
 *  Team : Aparna Venugopal, Ashwathi Diana P, Don Jiji, Bhavya Lakshmi
 *  Submission Date : 20-Feb-2023          
 * 
 *  Purpose: This JAVA program handles all the objects and methods for LMS holiday list page
 *  
 */


package com.lms.pages;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.lms.common.LoggerHelper;

public class LMSHolidayListPage {

	private WebDriver driver;
	LoggerHelper logger = new LoggerHelper();

	/*Declare all the variables required in test execution*/
	
	//Row and column count of the holiday list table
	int rowCount, columnCount; 
	//Holiday type - Public or optional
	String holidayType;       
	//For writing the excel output
	FileOutputStream outputStream = null; 
	//Path to generate the excel report based on holiday type
	String reportPath = "C:\\Users\\Aparna.Venugopal\\eclipse\\ShiftGear_CapstoneProj_LMS\\HolidayReport.xlsx"; 
	//Variables for excel workbook, sheet, row, header font and header style
	Workbook workbook = new XSSFWorkbook();
	Sheet sheetPublicHoliday;
	Sheet sheetOptionalHoliday;
	Row headerPublic;
	Row headerOptional;
	Font headerfont = workbook.createFont();
	CellStyle headerStyle = workbook.createCellStyle();

	// Constructor of the page class - To instantiate the driver
	public LMSHolidayListPage(WebDriver driver) {
		this.driver = driver;
	}

	 private By windowTitle = By.xpath("//div/h4[contains(text(),\"Office Holiday list\")]");

		// Method to get the title of the LMS Holiday List window
		public String getWindowTitle() throws InterruptedException {

			// Title of the window
			Thread.sleep(5000);
			String holidayListTitle = driver.findElement(windowTitle).getText();
			logger.UpdateLog("Page title is displayed as :" + holidayListTitle);

			return holidayListTitle;
		}
	
	// Method to get the table headers
		public List<WebElement> getTableHeader() {

			// Get the headers
			List<WebElement> tableHeaders = driver
					.findElements(By.xpath("//*[@id=\"display-holidaylist-form\"]/table/thead/tr[1]/th"));

			return tableHeaders;
		}

		// Method to get the table rows
		public List<WebElement> getTableRows() {

			// Get all the row elements
			List<WebElement> rowElements = driver
					.findElements(By.xpath("//*[@id=\"display-holidaylist-form\"]/table/tbody/tr"));
			return rowElements;
		}

		// Method to get the table columns
		public List<WebElement> getTableColumns() {

			// Get all the row elements
			List<WebElement> colElements = driver
					.findElements(By.xpath("//*[@id=\"display-holidaylist-form\"]/table/tbody/tr[1]/td"));

			return colElements;
		}

	// Method to count the Public Holidays
	public void countOfPublicHolidays() throws IOException {

		logger.UpdateLog("Validate whether the count of public holidays is greater than 10");
		// Get the row count of the Holiday List table
		int rowSize = getTableRows().size();
		logger.UpdateLog("Number of rows in the Holiday list table is: " + rowSize);

		// Get the column count of the Holiday List table
		int colSize = getTableColumns().size();
		logger.UpdateLog("Number of columns in the Holiday list table is: " + colSize);

		// Get the count of Public Holidays from the Holiday List table
		int publicHolidaycounter = 0;
		for (int i = 1; i <= rowSize; i++) {
			for (int j = 1; j <= colSize; j++) {

				// Get the content and check if the holiday type is Public Holiday
				String cellContent = driver
						.findElement(By
								.xpath("//*[@id=\"display-holidaylist-form\"]/table/tbody/tr[" + i + "]/td[" + j + "]"))
						.getText() + "  ";

				if (cellContent.contains("Public Holiday")) {
					// Increment the counter
					publicHolidaycounter++;
				}

			}
		}
		// Check if the count of public holidays is greater than or equal to 10
		if (publicHolidaycounter >= 10) {
			logger.UpdateLog("Number of public holidays is " + publicHolidaycounter);
			logger.UpdateLog("Count of Public Holdiays is greater than or equal to ten- PASS");
		} else {
			logger.UpdateLog("Number of public holidays:" + publicHolidaycounter);
			logger.UpdateLog("Count of Public Holdiays is greater than or equal to ten- FAIL");

	}

	}

	// Method to create the excel report
	public void generateHoldiayExcelReport() throws IOException {
		try {
			logger.UpdateLog("Verify that the user is able to generate a report based on the Holiday type");

		// Get the table headers and data
		List<WebElement> headerData = getTableHeader();
		List<WebElement> rowData = getTableRows();
		List<WebElement> colData = getTableColumns();

		// Create sheets for Public Holidays and Optional Holidays
		sheetPublicHoliday = workbook.createSheet("Public Holidays");
		sheetOptionalHoliday = workbook.createSheet("Optional Holidays");
		logger.UpdateLog("Sheets are created successully in the excel workbook");

		headerPublic = sheetPublicHoliday.createRow(0);
		headerOptional = sheetOptionalHoliday.createRow(0);
		logger.UpdateLog("Rows are created successully in the excel workbook");

		// Set a font for the headings
		headerfont.setBold(true);
		headerfont.setFontHeightInPoints((short) 12);
		headerfont.setColor(IndexedColors.BLACK.index);

		// Create cell style for headers
		headerStyle.setFont(headerfont);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);

		// Set the headers in the sheet
		for (int header = 1; header <= headerData.size(); header++) {
			String headerText = driver
					.findElement(By.xpath("//*[@id=\"display-holidaylist-form\"]/table/thead/tr[1]/th[" + header + "]"))
					.getText();

			Cell cell1 = headerPublic.createCell(header - 1);
			cell1.setCellValue(headerText);
			cell1.setCellStyle(headerStyle);

			Cell cell2 = headerOptional.createCell(header - 1);
			cell2.setCellValue(headerText);
			cell2.setCellStyle(headerStyle);
		}

		// Set the values for holiday type in both the sheets
		String holidayType = null;
		int publicHolidaycounter = 1;
		
		//Holiday type - Public, the details are captured in the Public Holidays sheet
		for (int row = 1; row <= rowData.size(); row++) {
			holidayType = driver
					.findElement(By.xpath("//*[@id=\"display-holidaylist-form\"]/table/tbody/tr[" + row + "]/td[4]"))
					.getText();
			Row rowvalue = sheetPublicHoliday.createRow(publicHolidaycounter);

			if (holidayType.equalsIgnoreCase("Public Holiday")) {
				for (int col = 1; col <= colData.size(); col++) {
					List<WebElement> cellcontent = driver.findElements(By.xpath(
							"//*[@id=\"display-holidaylist-form\"]/table/tbody/tr[" + row + "]/td[" + col + "]"));

					for (WebElement loop : cellcontent) {
						String detail = loop.getText();
						rowvalue.createCell(col - 1).setCellValue(detail);
					}

				}
				publicHolidaycounter++;
			}
		}
		
		int optionalHolidaycounter = 1;
		//Holiday type - Optional, the details are captured in the Public Holidays sheet
		for (int row = 1; row <= rowData.size(); row++) {
			holidayType = driver
					.findElement(By.xpath("//*[@id=\"display-holidaylist-form\"]/table/tbody/tr[" + row + "]/td[4]"))
					.getText();
			Row rowvalue = sheetOptionalHoliday.createRow(optionalHolidaycounter);
			if (holidayType.equalsIgnoreCase("Optional Holiday")) {
				for (int col = 1; col <= colData.size(); col++) {
					List<WebElement> cellcontent = driver.findElements(By.xpath(
							"//*[@id=\"display-holidaylist-form\"]/table/tbody/tr[" + row + "]/td[" + col + "]"));

					for (WebElement loop : cellcontent) {
						String detail = loop.getText();
						rowvalue.createCell(col - 1).setCellValue(detail);
					}

				}
				optionalHolidaycounter++;
			}
		}

		//Write the output in excel sheet 
		//Creating separate sheets for Public Holiday and Optional holiday and writing it in the excel file
		outputStream = new FileOutputStream(reportPath);
		workbook.write(outputStream);
		outputStream.close();
		workbook.close();
		logger.UpdateLog("Excel report generated successfully based on holiday type - PASS");
		}
		catch (Exception exception)
		{
			logger.UpdateLog("Excel report not generated",exception);

		}
	}
}