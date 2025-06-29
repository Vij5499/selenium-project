package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By commentTextArea = By.xpath("//textarea[@name='message']");
    private final By placeOrderButton = By.xpath("//a[contains(text(),'Place Order')]");
    
    // Ad-related locators
    private final By adFrameByTitle = By.xpath("//iframe[@title='Advertisement']");
    private final By adCloseButton = By.id("dismiss-button");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void handleAdPopup() {
        try {
            wait.withTimeout(Duration.ofSeconds(5)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(adFrameByTitle));
            wait.until(ExpectedConditions.elementToBeClickable(adCloseButton)).click();
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            System.out.println("Ad on Checkout page did not appear or could not be closed. Continuing...");
            driver.switchTo().defaultContent();
        }
    }

    public void enterComment(String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(commentTextArea)).sendKeys(comment);
    }

    public PaymentPage clickPlaceOrder() {
        handleAdPopup(); // Handle potential ads
        WebElement placeOrderBtn = wait.until(ExpectedConditions.presenceOfElementLocated(placeOrderButton));
        // Use JavaScript to scroll and click to avoid interception
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeOrderBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeOrderBtn);
        return new PaymentPage(driver);
    }
}