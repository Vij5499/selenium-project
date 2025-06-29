package com.automationexercise.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions; // <-- IMPORT ADDED
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {

    public static WebDriver getDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                // DEFINITIVE FIX for CI/CD environment
                ChromeOptions options = new ChromeOptions();
                // Run Chrome in headless mode (no UI)
                options.addArguments("--headless");
                // The next two arguments are required for running in a Linux/Docker container like GitHub Actions
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                // Set a custom window size, which is important for headless mode
                options.addArguments("--window-size=1920,1080");
                
                return new ChromeDriver(options);

            case "firefox":
                // Similar options can be added for Firefox if needed
                return new FirefoxDriver();
                
            case "edge":
                // Similar options can be added for Edge if needed
                return new EdgeDriver();
                
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}