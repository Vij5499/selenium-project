package com.automationexercise.utils;

import org.openqa.selenium.*;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Captures a PNG screenshot whenever a test fails.
 * The image is stored under target/screenshots with the test-method name and timestamp.
 *
 * Requires each test class to inherit from BaseTest (where the WebDriver is kept
 * in a protected field named "driver").
 *
 * Register the listener either:
 *  • via @Listeners(ScreenshotListener.class) on BaseTest, or
 *  • in testng.xml inside a <listeners> section.
 */
public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        try {
            // Reflectively get the 'driver' field from BaseTest
            Object currentInstance = result.getInstance();
            WebDriver driver = (WebDriver) currentInstance
                    .getClass()
                    .getSuperclass()          // BaseTest
                    .getDeclaredField("driver")
                    .get(currentInstance);

            if (driver == null) {
                System.err.println("[Screenshot] WebDriver was null, no screenshot taken.");
                return;
            }

            // Ensure the output directory exists
            Path dir = Paths.get("target", "screenshots");
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
        }

            // Filename: <testMethod>_yyyyMMdd_HHmmss.png
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path out = dir.resolve(result.getName() + "_" + timestamp + ".png");

            // Take screenshot
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), out, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("[Screenshot] saved to " + out.toAbsolutePath());

        } catch (Exception e) {
            System.err.println("!! Failed to capture screenshot: " + e.getMessage());
        }
    }

    /* All other ITestListener methods can be left empty */
}
