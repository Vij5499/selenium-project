package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void testHomePageNavigation() {
        driver.get("https://www.automationexercise.com/");
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isLogoVisible(), "Logo should be visible on the home page.");
        homePage.clickSignupLogin();   // Add an assertion to verify navigation to login page
    }
}