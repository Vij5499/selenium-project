package com.automationexercise.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReporterNG implements ITestListener {
    private static ExtentReports extent = ExtentManager.getInstance();
    // Use ThreadLocal to handle parallel test execution safely
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test entry in the report for each test method
        ExtentTest test = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log failure status and the exception details
        extentTest.get().log(Status.FAIL, "Test Failed");
        extentTest.get().log(Status.FAIL, result.getThrowable());

        // The ScreenshotListener will add the screenshot
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        // Write all the test information to the HTML report
        extent.flush();
    }
    
    // Helper method to be called by the ScreenshotListener
    public static void addScreenshotToReport(String screenshotPath) {
        if (extentTest.get() != null) {
            extentTest.get().addScreenCaptureFromPath(screenshotPath, "Screenshot on Failure");
        }
    }
}