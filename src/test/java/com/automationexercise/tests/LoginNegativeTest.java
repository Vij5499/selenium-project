package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginNegativeTest extends BaseTest {

    @Test
    public void invalidLogin() {
        loginPage.login("wrong@mail.com", "badPass");
        
        // Give just a tiny moment for error to appear if needed
        if (!loginPage.errorVisible()) {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
        
        Assert.assertTrue(loginPage.errorVisible(),
                "Red banner should appear for wrong creds");
    }
}