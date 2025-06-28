package com.automationexercise.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class SampleTest {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void openHomePage() {
        driver.get("https://www.automationexercise.com/");
        assert driver.getTitle().contains("Automation");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
