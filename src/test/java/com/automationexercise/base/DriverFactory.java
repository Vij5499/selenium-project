package com.automationexercise.base;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(String browser) {
        if (driver.get() == null) {
            try {
                // DEFINITIVE FIX: Read the Selenium Hub host from an environment variable.
                // This makes the framework work both locally and in Docker.
                String hubHost = System.getenv("HUB_HOST") != null ? System.getenv("HUB_HOST") : "localhost";
                URL hubUrl = new URL("http://" + hubHost + ":4444/wd/hub");

                MutableCapabilities capabilities;

                switch (browser.toLowerCase()) {
                    case "chrome":
                        capabilities = new ChromeOptions();
                        break;
                    case "firefox":
                        capabilities = new FirefoxOptions();
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported browser: " + browser);
                }
                
                driver.set(new RemoteWebDriver(hubUrl, capabilities));
            } catch (Exception e) {
                // Throw an exception to fail the test immediately if the driver can't be created.
                throw new RuntimeException("Failed to create RemoteWebDriver", e);
            }
        }
        return driver.get();
    }
    
    public static WebDriver getDriverFromThread() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}