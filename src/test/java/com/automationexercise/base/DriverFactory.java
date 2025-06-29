package com.automationexercise.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {

    public static WebDriver getDriver(String browser) {
        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "chrome":
                // Selenium Manager will automatically download the correct driver
                driver = new ChromeDriver();
                break;
            case "firefox":
                // Selenium Manager will automatically download the correct driver
                driver = new FirefoxDriver();
                break;
            case "edge":
                // Selenium Manager will automatically download the correct driver
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        return driver;
    }
}