package com.store.test;

import static org.testng.Assert.assertEquals;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TestBase {
	WebDriver driver;
	
	ExtentReports extent;
	ExtentHtmlReporter htmlReporter;
	ExtentTest logger;
	String htmlReportPath = System.getProperty("user.dir")+"/Reports/Base_"+Helper.getCurrentTime()+".html";
	
	// BeforeSuite, BeforeTest, BeforeClass, BeforeMethod, Test, AfterMethod, AfterClass, AfterTest, AfterSuite
	@BeforeSuite
	public void SetupSuite() {
		Reporter.log("Setting up reports and test is about to start.", true);
		htmlReporter = new ExtentHtmlReporter(htmlReportPath);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		logger = extent.createTest("Google test --> Test");
		Reporter.log("Setting up reports and test is about to start.", true);
	}
	
	@BeforeClass
	public void setup() {
		Reporter.log("the browser is about to launch",true);
	System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	driver =new ChromeDriver();
	Reporter.log("the browser is launched",true);
		}

	@Test
	public void test1() {
		Reporter.log("the url is about to open",true);
		driver.get("https://www.google.com/");
		assertEquals(driver.getTitle(), "Google");
		Reporter.log("the url is open",true);
	}
	@Test
	public void test2() {
		Reporter.log("itwill open the url ",true);
		driver.get("https://www.google.com/");
		assertEquals(driver.getTitle(), "google");
		Reporter.log("title verified",true);
	}
	@AfterMethod
	public void getResult(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + ">> FAiled due to following reason:", ExtentColor.RED));
			try {
				logger.log(Status.FAIL, result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(Helper.captureScreenshot(driver)).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " >> TEst Passed." , ExtentColor.GREEN));
			try {
				logger.log(Status.PASS, "test passed", MediaEntityBuilder.createScreenCaptureFromPath(Helper.captureScreenshot(driver)).build());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Reporter.log("result is generated",true);
	}
	
	
	@AfterClass
	public void tearDown() {
		driver.close();
	}
	@AfterTest
	public void endTest() {
		extent.flush();
		Reporter.log("testis ended");
	}
}
