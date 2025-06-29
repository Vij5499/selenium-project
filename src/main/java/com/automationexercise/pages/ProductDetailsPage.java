package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductDetailsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators for the product details page
    private final By productName = By.xpath("//div[@class='product-information']/h2");
    private final By productCategory = By.xpath("//div[@class='product-information']/p[contains(., 'Category')]");
    private final By productPrice = By.xpath("//div[@class='product-information']/span/span");
    private final By addToCartButton = By.xpath("//button[@type='button']");
    private final By viewCartLink = By.xpath("//u[text()='View Cart']");
    private final By quantityInput = By.id("quantity");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Gets the displayed name of the product.
     * @return The product name as a String.
     */
    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }

    /**
     * Clicks the 'Add to cart' button.
     */
    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    /**
     * Clicks the 'View Cart' link that appears after adding an item.
     * @return A new instance of the CartPage.
     */
    public CartPage clickViewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartLink)).click();
        return new CartPage(driver);
    }
    public void setQuantity(String quantity) {
    WebElement quantityField = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
    quantityField.clear();
    quantityField.sendKeys(quantity);
    }
}