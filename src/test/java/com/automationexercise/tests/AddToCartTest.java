package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.CartPage;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import java.time.Duration;

public class AddToCartTest extends BaseTest {

    @Test
    public void addSingleProduct() {
        // Add explicit wait for the Products link
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Try multiple strategies to find the Products link
        try {
            // First try exact link text
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Products"))).click();
        } catch (Exception e1) {
            try {
                // Try partial link text
                wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Products"))).click();
            } catch (Exception e2) {
                try {
                    // Try xpath with contains
                    wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'Products')]"))).click();
                } catch (Exception e3) {
                    // Try navigating directly to products page
                    driver.get(config.getBaseUrl() + "/products");
                }
            }
        }
        
        ProductsPage products = new ProductsPage(driver);

        // add "Blue Top"
        products.addProductToCart("Blue Top");
        products.clickViewCart();

        // assert cart shows 1 item
        CartPage cart = new CartPage(driver);
        Assert.assertEquals(cart.getItemCount(), 1, "Cart should have 1 item");
    }
}