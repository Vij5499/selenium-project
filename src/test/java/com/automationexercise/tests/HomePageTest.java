package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.LoginPage;
import com.automationexercise.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void verifyLogoAndNavigateToLogin() {
        HomePage home = HomePage.open(driver);

        Assert.assertTrue(home.isLogoVisible(), "Logo not visible on Home page");

        LoginPage login = home.clickSignupLogin();
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Not navigated to Login page");
    }
}
