package com.automationexercise.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions; // <-- IMPORT ADDED

public class DriverFactory {

    // ThreadLocal will store a driver instance for each thread
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(String browser) {
        // This method now correctly creates a new driver only if one doesn't exist for the current thread
        if (driver.get() == null) {
            WebDriver newDriver;
            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--window-size=1920,1080");
                    newDriver = new ChromeDriver(chromeOptions);
                    break;
                
                case "firefox":
                    // DEFINITIVE FIX: Add headless options for Firefox
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("-headless");
                    newDriver = new FirefoxDriver(firefoxOptions);
                    break;
                    
                case "edge":
                    newDriver = new EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            driver.set(newDriver);
        }
        return driver.get();
    }

    // This is a new helper method for the listener to get the correct driver
    public static WebDriver getDriverFromThread() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // This is crucial to prevent memory leaks
        }
    }
}