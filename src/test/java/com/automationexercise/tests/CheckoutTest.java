package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    @Test
    public void endToEndCheckout() {
        // (1) Go to Products
        navBar.goToProducts();
        productsPage.addProductToCartByName("Blue Top");

        // (2) Cart → Checkout (login happens automatically if cached;
        //     otherwise call `loginPage.doLogin()` first)
        CartPage cart      = navBar.goToCart();
        CheckoutPage co    = cart.proceedToCheckout();

        // (3) Address comment & Place Order
        PaymentPage pay    = co.placeOrder("Please deliver ASAP.");

        // (4) Fake card – any values pass in automationexercise
        OrderConfirmationPage conf =
                pay.pay("QA Tester", "4111111111111111", "123", "12", "2028");

        Assert.assertTrue(conf.isOrderSuccessful(), "Order confirmation banner should appear");
    }
}
