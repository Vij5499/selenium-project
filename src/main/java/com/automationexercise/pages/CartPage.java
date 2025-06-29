package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- Locators ---
    private final By cartItemsTable = By.id("cart_items");
    // This is the most precise locator for the quantity, which is inside a button
    private final String itemQuantityLocator = "//td[@class='cart_description']/h4/a[text()='%s']/ancestor::tr//button[@class='disabled']";
    private final String removeItemButtonLocator = "//td[@class='cart_description']/h4/a[text()='%s']/ancestor::tr//a[@class='cart_quantity_delete']";
    private final By emptyCartMessage = By.xpath("//b[text()='Cart is empty!']");
    private final By proceedToCheckoutButton = By.xpath("//a[contains(text(),'Proceed To Checkout')]");


    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Increased wait for CI
    }

    private void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemsTable));
    }

    public String getItemQuantity(String productName) {
        waitForPageLoad();
        By quantityLocator = By.xpath(String.format(itemQuantityLocator, productName));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(quantityLocator)).getText();
    }

    public void removeItemFromCart(String productName) {
        waitForPageLoad();
        By removeButtonLocator = By.xpath(String.format(removeItemButtonLocator, productName));
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(removeButtonLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", removeButton);
    }
    // --- Verification and Navigation Methods ---

    public boolean isCartEmpty() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartMessage)).isDisplayed();
    }

    public String getCartItemName(String productName) {
        waitForPageLoad();
        By itemLocator = By.xpath(String.format("//a[text()='%s']", productName));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemLocator)).getText();
    }
    
    // FIX: Re-added the missing method for CheckoutTest
    public CheckoutPage clickProceedToCheckout() {
        waitForPageLoad();
        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton)).click();
        return new CheckoutPage(driver);
    }

    // FIX: Re-added the missing method for AddToCartTest
    public int getItemCount() {
        waitForPageLoad();
        List<WebElement> items = driver.findElements(By.xpath("//tbody/tr"));
        return items.size();
    }
}