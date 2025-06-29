package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- Locators ---
    private final By featuresItemsTitle = By.xpath("//h2[text()='Features Items']");
    private final By productsLink = By.xpath("//a[@href='/products']");
    private final By logo = By.xpath("//img[@alt='Website for automation practice']");
    private final By signupLoginLink = By.xpath("//a[contains(.,'Signup / Login')]");
    private final By searchInput = By.id("search_product");
    private final By searchButton = By.id("submit_search");

    // --- Ad Locators ---
    private final By adFrameByTitle = By.xpath("//iframe[@title='Advertisement']");
    private final By adCloseButton = By.id("dismiss-button");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased default wait time
    }
    
    // --- Core Page Interaction Methods ---

    private void waitForPageLoad() {
        // This is a crucial wait that ensures the main content is ready.
        wait.until(ExpectedConditions.visibilityOfElementLocated(featuresItemsTitle));
    }
    
    private void handleAdPopup() {
        try {
            wait.withTimeout(Duration.ofSeconds(5)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(adFrameByTitle));
            wait.until(ExpectedConditions.elementToBeClickable(adCloseButton)).click();
        } catch (Exception e) {
            System.out.println("Ad did not appear or could not be closed. Continuing...");
        } finally {
            driver.switchTo().defaultContent();
        }
    }


    public ProductsPage navigateToProducts() {
        waitForPageLoad();
        handleAdPopup();
        wait.until(ExpectedConditions.elementToBeClickable(productsLink)).click();
        return new ProductsPage(driver);
    }

    public LoginPage clickSignupLogin() {
        waitForPageLoad();
        handleAdPopup();
        wait.until(ExpectedConditions.elementToBeClickable(signupLoginLink)).click();
        return new LoginPage(driver);
    }
    
    public boolean isLogoVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(logo)).isDisplayed();
    }
}