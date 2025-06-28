package com.automationexercise.pages;

import org.openqa.selenium.*;
import com.automationexercise.utils.WaitUtils;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page-object for Shopping Cart page.
 */
public class CartPage {

    private final WebDriver driver;

    /* ------------------------------------------------------------------
     * Locators
     * ------------------------------------------------------------------ */

    // Cart page elements
    private final By cartTable = By.id("cart_info_table");
    private final By cartItems = By.xpath("//table[@id='cart_info_table']//tbody/tr");
    private final By emptyCartMessage = By.xpath("//span[contains(text(),'Cart is empty')]");
    private final By proceedToCheckoutBtn = By.xpath("//a[contains(text(),'Proceed To Checkout')]");
    
    /* ------------------------------------------------------------------
     * Constructor
     * ------------------------------------------------------------------ */

    public CartPage(WebDriver driver) {
        this.driver = driver;
        // Verify we're on the cart page
        if (!driver.getCurrentUrl().contains("view_cart") && !driver.getCurrentUrl().contains("cart")) {
            throw new IllegalStateException("Not on the Cart page. Current URL: " + driver.getCurrentUrl());
        }
    }

    /* ------------------------------------------------------------------
     * Methods
     * ------------------------------------------------------------------ */

    /**
     * Gets the number of items in the cart
     */

    public CheckoutPage proceedToCheckout() {
        driver.findElement(proceedBtn).click();
        return new CheckoutPage(driver);
    }
    public int getItemCount() {
        try {
            // Wait a moment for the page to load
            Thread.sleep(1000);
            
            // Check if cart is empty first
            List<WebElement> emptyMessages = driver.findElements(emptyCartMessage);
            if (!emptyMessages.isEmpty() && emptyMessages.get(0).isDisplayed()) {
                return 0;
            }
            
            // Wait for cart table to be visible
            WaitUtils.waitVisible(driver, cartTable, 10);
            
            // Count the cart items (excluding header row)
            List<WebElement> items = driver.findElements(cartItems);
            
            // Filter out any non-product rows (like headers)
            int itemCount = 0;
            for (WebElement item : items) {
                try {
                    // Check if this row contains product information
                    // Look for elements that indicate this is a product row
                    if (item.findElements(By.cssSelector("td.cart_product")).size() > 0 ||
                        item.findElements(By.cssSelector("td img")).size() > 0) {
                        itemCount++;
                    }
                } catch (Exception e) {
                    // Skip rows that don't contain product info
                }
            }
            
            return itemCount;
            
        } catch (Exception e) {
            System.out.println("Error getting item count: " + e.getMessage());
            // Fallback: try alternative method
            try {
                List<WebElement> productImages = driver.findElements(By.xpath("//table[@id='cart_info_table']//td[@class='cart_product']//img"));
                return productImages.size();
            } catch (Exception e2) {
                System.out.println("Fallback method also failed: " + e2.getMessage());
                return 0;
            }
        }
    }
    
    /**
     * Checks if the cart is empty
     */
    public boolean isCartEmpty() {
        try {
            List<WebElement> emptyMessages = driver.findElements(emptyCartMessage);
            if (!emptyMessages.isEmpty() && emptyMessages.get(0).isDisplayed()) {
                return true;
            }
            return getItemCount() == 0;
        } catch (Exception e) {
            return true; // Assume empty if we can't determine
        }
    }
    
    /**
     * Gets the name of a product in the cart by index (0-based)
     */
    public String getProductName(int index) {
        try {
            By productNameLocator = By.xpath("//table[@id='cart_info_table']//tbody/tr[" + (index + 1) + "]//td[@class='cart_description']//a");
            WebElement productName = WaitUtils.waitVisible(driver, productNameLocator, 5);
            return productName.getText();
        } catch (Exception e) {
            throw new RuntimeException("Could not get product name at index " + index + ": " + e.getMessage());
        }
    }
    
    /**
     * Clicks the "Proceed To Checkout" button
     */
    public void proceedToCheckout() {
        WebElement checkoutBtn = WaitUtils.waitClickable(driver, proceedToCheckoutBtn, 10);
        checkoutBtn.click();
    }
    
    /**
     * Removes a product from cart by index (0-based)
     */
    public void removeProduct(int index) {
        try {
            By deleteButtonLocator = By.xpath("//table[@id='cart_info_table']//tbody/tr[" + (index + 1) + "]//a[@class='cart_quantity_delete']");
            WebElement deleteBtn = WaitUtils.waitClickable(driver, deleteButtonLocator, 5);
            deleteBtn.click();
            
            // Wait for item to be removed
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException("Could not remove product at index " + index + ": " + e.getMessage());
        }
    }
}