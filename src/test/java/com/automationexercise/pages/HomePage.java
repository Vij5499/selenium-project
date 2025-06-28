package com.automationexercise.pages;


import com.automationexercise.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class HomePage {

    private WebDriver driver;

    // Locators
    private final By logo          = By.xpath("//img[@alt='Website for automation practice']");
    private final By signupLogin   = By.linkText("Signup / Login");
    private final By productsLink = By.xpath("//a[@href='/products']");


   


    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Page actions
    public boolean isLogoVisible() {
        return driver.findElement(logo).isDisplayed();
    }

    public LoginPage clickSignupLogin() {
        driver.findElement(signupLogin).click();
        return new LoginPage(driver);
    }

    // Convenience: open homepage
    public static HomePage open(WebDriver driver) {
        driver.get("https://www.automationexercise.com/");
        return new HomePage(driver);
    }


    public ProductsPage clickProducts() {
        WaitUtils.waitClickable(driver, productsLink, 8).click();
        return new ProductsPage(driver);
    }

}
