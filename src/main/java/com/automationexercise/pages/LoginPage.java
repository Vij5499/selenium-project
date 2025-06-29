package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By loginEmailInput = By.xpath("//input[@data-qa='login-email']");
    private final By loginPasswordInput = By.xpath("//input[@data-qa='login-password']");
    private final By loginButton = By.xpath("//button[@data-qa='login-button']");
    private final By signupNameInput = By.xpath("//input[@data-qa='signup-name']");
    private final By signupEmailInput = By.xpath("//input[@data-qa='signup-email']");
    private final By signupButton = By.xpath("//button[@data-qa='signup-button']");

    // Ad-related locators
    private final By adFrameByTitle = By.xpath("//iframe[@title='Advertisement']");
    private final By adCloseButton = By.id("dismiss-button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void handleAdPopup() {
        try {
            wait.withTimeout(Duration.ofSeconds(5)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(adFrameByTitle));
            wait.until(ExpectedConditions.elementToBeClickable(adCloseButton)).click();
        } catch (Exception e) {
            System.out.println("Ad on Login page did not appear or could not be closed. Continuing...");
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    public void enterLoginEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginEmailInput)).sendKeys(email);
    }

    public void enterLoginPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPasswordInput)).sendKeys(password);
    }

    public void clickLogin() {
        handleAdPopup();
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
    
    public void enterSignupName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signupNameInput)).sendKeys(name);
    }

    public void enterSignupEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signupEmailInput)).sendKeys(email);
    }

    public RegistrationPage clickSignup() {
        handleAdPopup();
        wait.until(ExpectedConditions.elementToBeClickable(signupButton)).click();
        return new RegistrationPage(driver);
    }
}