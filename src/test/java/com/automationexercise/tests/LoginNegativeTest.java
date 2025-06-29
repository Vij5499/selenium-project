package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.LoginPage;
import org.testng.annotations.Test;

public class LoginNegativeTest extends BaseTest {

    @Test
    public void testInvalidLogin() {
        driver.get("https://www.automationexercise.com/");
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickSignupLogin();

        // FIX: Changed method names to be specific to login
        loginPage.enterLoginEmail("invalid@email.com");
        loginPage.enterLoginPassword("invalidpassword");
        loginPage.clickLogin();
        // TODO: Add an assertion here to check for an error message
    }
}