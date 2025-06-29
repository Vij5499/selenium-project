package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductDetailsTest extends BaseTest {

    @Test
    public void testProductDetailsAndAddToCart() {
        String productName = "Blue Top";

        driver.get("https://www.automationexercise.com/");
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.navigateToProducts();
        ProductDetailsPage detailsPage = productsPage.viewProduct(productName);

        Assert.assertEquals(detailsPage.getProductName(), productName);

        detailsPage.clickAddToCart();
        CartPage cartPage = detailsPage.clickViewCart();
        
        Assert.assertEquals(cartPage.getCartItemName(productName), productName);
    }
}