package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor; // <-- IMPORT ADDED
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement; // <-- IMPORT ADDED
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegistrationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By passwordInput = By.id("password");
    private final By firstNameInput = By.id("first_name");
    private final By lastNameInput = By.id("last_name");
    private final By address1Input = By.id("address1");
    private final By stateInput = By.id("state");
    private final By cityInput = By.id("city");
    private final By zipcodeInput = By.id("zipcode");
    private final By mobileNumberInput = By.id("mobile_number");
    private final By createAccountButton = By.xpath("//button[@data-qa='create-account']");
    private final By accountCreatedTitle = By.xpath("//h2[@data-qa='account-created']");
    private final By continueButton = By.xpath("//a[@data-qa='continue-button']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // THE DEFINITIVE FIX IS IN THIS METHOD
    public void clickCreateAccount() {
        WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(createAccountButton));
        // Use a JavaScript click to bypass any overlays or ads.
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createButton);
    }

    // --- Other methods remain the same ---

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput)).sendKeys(lastName);
    }

    public void enterAddress(String address) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(address1Input)).sendKeys(address);
    }
    
    public void enterState(String state) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(stateInput)).sendKeys(state);
    }

    public void enterCity(String city) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityInput)).sendKeys(city);
    }

    public void enterZipcode(String zipcode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(zipcodeInput)).sendKeys(zipcode);
    }

    public void enterMobileNumber(String mobileNumber) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(mobileNumberInput)).sendKeys(mobileNumber);
    }

    public boolean isAccountCreatedMessageVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accountCreatedTitle)).isDisplayed();
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }
}