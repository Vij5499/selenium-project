package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPositiveTest extends BaseTest {

    @Test
    public void validLoginAndLogout() {
        loginPage.login(config.getValidEmail(), config.getValidPassword());
        
        // Give just a tiny moment for login to complete if needed
        if (!loginPage.isLoggedIn()) {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
        
        Assert.assertTrue(loginPage.isLoggedIn(), "Should be logged-in");

        loginPage.clickLogout();
        
        // Immediate check for logout
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), 
                "Should be back on login page after logout");
    }
}