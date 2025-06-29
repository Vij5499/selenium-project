package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartFunctionalityTest extends BaseTest {

    @Test
    public void testProductQuantityInCart() {
        String productName = "Blue Top";
        String desiredQuantity = "2";

        driver.get("https://www.automationexercise.com/");
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.navigateToProducts();
        ProductDetailsPage detailsPage = productsPage.viewProduct(productName);

        detailsPage.setQuantity(desiredQuantity);
        detailsPage.clickAddToCart();
        CartPage cartPage = detailsPage.clickViewCart();

        Assert.assertEquals(cartPage.getItemQuantity(productName), desiredQuantity);
    }

    @Test
    public void testRemoveProductFromCart() {
        String productName = "Stylish Dress";

        driver.get("https://www.automationexercise.com/");
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.navigateToProducts();
        productsPage.addProductToCart(productName);
        CartPage cartPage = productsPage.clickViewCart();

        cartPage.removeItemFromCart(productName);
        Assert.assertTrue(cartPage.isCartEmpty());
    }
}