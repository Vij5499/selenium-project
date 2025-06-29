package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest {

    @Test
    public void testAddProductToCart() {
        driver.get("https://www.automationexercise.com/");
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.navigateToProducts();
        productsPage.addProductToCart("Blue Top");

        CartPage cartPage = productsPage.clickViewCart();
        Assert.assertEquals(cartPage.getItemCount(), 1, "The cart should have one item.");
    }
}