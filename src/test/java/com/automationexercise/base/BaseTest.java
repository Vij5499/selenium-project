package com.automationexercise.base;

import com.automationexercise.utils.ScreenshotListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(ScreenshotListener.class)
public class BaseTest {

    // This is now a ThreadLocal WebDriver, managed by the DriverFactory
    protected WebDriver driver;

    public WebDriver getDriver() {
        return DriverFactory.getDriver("chrome");
    }

    @BeforeMethod
    public void setUp() {
        // Get the driver instance for the current thread
        driver = DriverFactory.getDriver("chrome");
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        // Quit the driver and remove it from the ThreadLocal pool
        DriverFactory.quitDriver();
    }
}