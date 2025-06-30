package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.LoginPage;
import org.testng.annotations.Test;
import org.testng.Assert;

public class LoginPositiveTest extends BaseTest {

    @Test
    public void testValidLogin() {
        driver.get("https://www.automationexercise.com/");
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickSignupLogin();

        // FIX: Changed method names to be specific to login
        // TODO: Replace with your actual credentials
        loginPage.enterLoginEmail("your-email@example.com");
        loginPage.enterLoginPassword("your-password");
        loginPage.clickLogin();
        // TODO: Add an assertion here to verify successful login
       
    }
}