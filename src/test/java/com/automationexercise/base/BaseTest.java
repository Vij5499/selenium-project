package com.automationexercise.base;

import com.automationexercise.utils.ScreenshotListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

@Listeners(ScreenshotListener.class)
public class BaseTest {

    // This is a placeholder for the driver instance for the current test
    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser) {
        // This is the ONLY place where a driver is initialized for a test
        driver = DriverFactory.getDriver(browser);
        // Maximizing window is not needed in headless but doesn't hurt
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        // This safely quits the driver for the current thread
        DriverFactory.quitDriver();
    }

    // DEFINITIVE FIX: This method now correctly gets the driver for the
    // current thread, which is used by the ScreenshotListener.
    public WebDriver getDriver() {
        return DriverFactory.getDriverFromThread();
    }
}