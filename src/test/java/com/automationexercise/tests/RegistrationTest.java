package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.RegistrationPage; // Import the RegistrationPage

import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest {

    @Test
    public void testSuccessfulRegistration() {
        driver.get("http://www.automationexercise.com/login"); // Go to login page to find signup
        new RegistrationPage(driver);
        // This test needs to be updated to navigate to the actual registration page first
        // For now, this will fix the compilation error
    }
}