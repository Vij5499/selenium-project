package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

// This class definition MUST be inside a file named PaymentPage.java
public class PaymentPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By nameOnCardInput = By.name("name_on_card");
    private final By cardNumberInput = By.name("card_number");
    private final By cvcInput = By.name("cvc");
    private final By expiryMonthInput = By.name("expiry_month");
    private final By expiryYearInput = By.name("expiry_year");
    private final By payAndConfirmButton = By.id("submit");

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterNameOnCard(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameOnCardInput)).sendKeys(name);
    }

    public void enterCardNumber(String cardNumber) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumberInput)).sendKeys(cardNumber);
    }

    public void enterCVC(String cvc) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cvcInput)).sendKeys(cvc);
    }

    public void enterExpiryMonth(String month) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(expiryMonthInput)).sendKeys(month);
    }

    public void enterExpiryYear(String year) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(expiryYearInput)).sendKeys(year);
    }

    public OrderConfirmationPage clickPayAndConfirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(payAndConfirmButton)).click();
        return new OrderConfirmationPage(driver);
    }
}