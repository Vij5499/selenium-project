package com.automationexercise.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.automationexercise.utils.WaitUtils;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;


/**
 * Page-object for Products page.
 */
public class ProductsPage {

    private final WebDriver driver;

    /* ------------------------------------------------------------------
     * Locators
     * ------------------------------------------------------------------ */

    // Products page elements
    private final By productsContainer = By.cssSelector(".features_items");
    private final By viewCartLink = By.xpath("//a[contains(text(),'View Cart')]");
    private final By continueShoppingBtn = By.xpath("//button[contains(text(),'Continue Shopping')]");
    
    /* ------------------------------------------------------------------
     * Constructor
     * ------------------------------------------------------------------ */

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        // Wait for products container to be visible
        WaitUtils.waitVisible(driver, productsContainer, 10);
    }

    /* ------------------------------------------------------------------
     * Actions
     * ------------------------------------------------------------------ */

    /**
     * Adds a product to cart by product name
     */
    public void addProductToCart(String productName) {
        try {
            // Find the product by name and click "Add to cart"
            By productLocator = By.xpath(
                "//div[@class='productinfo text-center']//p[contains(text(),'" + productName + "')]" +
                "/following-sibling::a[contains(@class,'add-to-cart')]"
            );
            
            WebElement addToCartBtn = WaitUtils.waitClickable(driver, productLocator, 10);
            
            // Scroll to element before clicking
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);
            Thread.sleep(500); // Brief pause after scroll
            
            addToCartBtn.click();
            
            // Handle the modal that appears after adding to cart
            handleAddToCartModal();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to add product '" + productName + "' to cart: " + e.getMessage());
        }
    }
    
    /**
     * Handles the modal that appears after adding a product to cart
     */
    private void handleAddToCartModal() {
        try {
            // Wait for the modal to appear and then click "Continue Shopping" if it exists
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            try {
                WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn));
                continueBtn.click();
            } catch (Exception e) {
                // Modal might not appear or button might be different, continue anyway
                System.out.println("Continue Shopping button not found or clicked, proceeding...");
            }
        } catch (Exception e) {
            // If modal handling fails, just continue
            System.out.println("Modal handling failed, continuing: " + e.getMessage());
        }
    }

    /**
     * Clicks the "View Cart" link
     */
    public void clickViewCart() {
        try {
            // Try to find View Cart in the modal first
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            try {
                WebElement modalViewCart = shortWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='modal-content']//a[contains(text(),'View Cart')]")
                ));
                modalViewCart.click();
                return;
            } catch (Exception e) {
                // Modal view cart not found, try regular view cart
            }
            
            // Try regular view cart link
            WebElement viewCart = WaitUtils.waitClickable(driver, viewCartLink, 10);
            viewCart.click();
            
        } catch (Exception e) {
            // If view cart link not found, navigate directly to cart
            driver.get(driver.getCurrentUrl().replace("/products", "/view_cart"));
        }
    }
    
    /**
     * Gets the total number of products displayed on the page
     */
    public int getProductCount() {
        By productItems = By.cssSelector(".features_items .col-sm-4");
        return driver.findElements(productItems).size();
    }
}