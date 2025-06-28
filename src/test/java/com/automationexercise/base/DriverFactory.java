package com.automationexercise.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /** Initialise driver based on -Dbrowser param (default chrome) */
    public static WebDriver initDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                tlDriver.set(new FirefoxDriver());
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                tlDriver.set(new EdgeDriver());
            }
            default -> {                       // chrome
                WebDriverManager.chromedriver().setup();
                tlDriver.set(new ChromeDriver());
            }
        }
        tlDriver.get().manage().window().maximize();
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}
