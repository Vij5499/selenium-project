package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    @Test(description = "Verify checkout process after registration")
    public void testCheckoutProcess() {
        String email = "testuser" + System.currentTimeMillis() + "@example.com";
        String name = "Test User";

        driver.get("https://www.automationexercise.com/");

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickSignupLogin();

        loginPage.enterSignupName(name);
        loginPage.enterSignupEmail(email);
        RegistrationPage registrationPage = loginPage.clickSignup();

        registrationPage.enterPassword("password123");
        registrationPage.enterFirstName("Test");
        registrationPage.enterLastName("User");
        registrationPage.enterAddress("123 Main St");
        registrationPage.enterState("California");
        registrationPage.enterCity("Los Angeles");
        registrationPage.enterZipcode("90001");
        registrationPage.enterMobileNumber("1234567890");
        registrationPage.clickCreateAccount();
        
        Assert.assertTrue(registrationPage.isAccountCreatedMessageVisible(), "Account Created! message should be visible.");
        registrationPage.clickContinue();

        // FIX: Added the 'ProductsPage' type declaration
        ProductsPage productsPage = homePage.navigateToProducts();
        productsPage.addProductToCart("Blue Top");
        CartPage cartPage = productsPage.clickViewCart();

        CheckoutPage checkoutPage = cartPage.clickProceedToCheckout();

        checkoutPage.enterComment("This is a test order.");
        PaymentPage paymentPage = checkoutPage.clickPlaceOrder();

        paymentPage.enterNameOnCard("Test Card");
        paymentPage.enterCardNumber("1234567890123456");
        paymentPage.enterCVC("123");
        paymentPage.enterExpiryMonth("12");
        paymentPage.enterExpiryYear("2030");
        OrderConfirmationPage orderConfirmationPage = paymentPage.clickPayAndConfirmOrder();

        Assert.assertTrue(orderConfirmationPage.isOrderConfirmed(), "Order confirmation message should be visible.");
    }
}