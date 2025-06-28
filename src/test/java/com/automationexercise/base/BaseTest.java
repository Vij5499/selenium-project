package com.automationexercise.base;

import com.automationexercise.pages.LoginPage;
import com.automationexercise.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;
import java.time.Duration;

@Listeners(com.automationexercise.utils.ScreenshotListener.class)
public abstract class BaseTest {

    protected WebDriver driver;
    protected ConfigReader config;
    protected LoginPage loginPage;

    @BeforeClass
    public void setUpClass() {
        WebDriverManager.chromedriver().setup();
        config = new ConfigReader("src/test/resources/config.properties");
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get(config.getBaseUrl());
        
        // Add explicit wait for page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Wait for and click "Signup / Login" with better error handling
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Signup / Login"))).click();
        } catch (Exception e) {
            // Try alternative locators
            try {
                wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(),'Signup') and contains(text(),'Login')]"))).click();
            } catch (Exception e2) {
                // Navigate directly to login page
                driver.get(config.getBaseUrl() + "/login");
            }
        }
        
        loginPage = new LoginPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}