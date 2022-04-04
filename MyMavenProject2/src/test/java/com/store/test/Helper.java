package com.store.test;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class Helper {
	
	public static String captureScreenshot(WebDriver driver) {
		String screenshotPath = System.getProperty("user.dir")+"/Screenshots/Store_" + getCurrentTime() +".png";
		File src =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		try {
			FileHandler.copy(src, new File(screenshotPath));
			System.out.println("Screenshot captured");
		} catch (Exception e) {
			System.out.println("Unable to capture screenshot " + e.getMessage());
		}
		return screenshotPath;
	}
	
	public static String getCurrentTime() {
		Date currentDate = new Date();
		DateFormat format = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
		return format.format(currentDate);
	}
}
