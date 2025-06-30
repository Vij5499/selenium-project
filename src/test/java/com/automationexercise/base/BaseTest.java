package com.automationexercise.base;

import com.automationexercise.utils.ScreenshotListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters; // <-- IMPORT ADDED

@Listeners(ScreenshotListener.class)
public class BaseTest {

    protected WebDriver driver;

    // This method now accepts a 'browser' parameter from testng.xml
    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser) {
        // Get the driver instance for the specified browser
        driver = DriverFactory.getDriver(browser);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    public WebDriver getDriver() {
        // This method needs to be updated to get the current thread's driver
        return DriverFactory.getDriverFromThread();
    }
}