package com.automationexercise.utils;

import com.automationexercise.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // Get the WebDriver instance from the test class
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();

        if (driver instanceof TakesScreenshot) {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
            String screenshotName = result.getName() + "_" + timestamp + ".png";
            Path destination = Paths.get("target/screenshots", screenshotName);

            try {
                Files.createDirectories(destination.getParent());
                Files.copy(screenshotFile.toPath(), destination);
                
                // NEW: Add the screenshot to the ExtentReport
                ExtentReporterNG.addScreenshotToReport(destination.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}