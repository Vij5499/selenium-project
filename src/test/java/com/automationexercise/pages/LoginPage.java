package com.automationexercise.pages;

import org.openqa.selenium.*;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.automationexercise.utils.WaitUtils;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Improved Page-object for "Signup / Login" page.
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final WebDriverWait shortWait;

    /* ------------------------------------------------------------------
     * Locators
     * ------------------------------------------------------------------ */

    // login form fields & button
    private final By emailField      = By.name("email");
    private final By passwordField   = By.name("password");
    private final By loginBtn        = By.xpath("//button[contains(text(),'Login')]");

    // banner shown after a failed login
    private final By errorBanner = By.xpath(
        "//form[@action='/login']//p" +
        "[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')," +
        " 'your email or password is incorrect')]");

    // More comprehensive login success indicators
    private final By loggedInAsText = By.xpath("//*[contains(text(), 'Logged in as')]");
    private final By logoutLink = By.xpath("//a[contains(text(),'Logout') or contains(@href,'logout')]");
    private final By deleteAccountLink = By.xpath("//a[contains(text(),'Delete Account') or contains(@href,'delete_account')]");
    private final By accountLink = By.xpath("//a[contains(@href,'account')]");
    private final By userIcon = By.xpath("//i[contains(@class,'fa-user')]");
    
    // Alternative login success indicators
    private final By headerUserSection = By.xpath("//header//li[contains(@class,'dropdown') or contains(.,'Account')]");
    private final By navigationBar = By.xpath("//ul[@class='nav navbar-nav']");

    /* ------------------------------------------------------------------
     * Constructor
     * ------------------------------------------------------------------ */

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Reduced from 10
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(2)); // For quick checks
    }

    /* ------------------------------------------------------------------
     * Actions
     * ------------------------------------------------------------------ */

    /**
     * Navigate to login page if not already there
     */
    public void navigateToLoginPage() {
        if (!driver.getCurrentUrl().contains("/login")) {
            driver.get(driver.getCurrentUrl().replace(driver.getCurrentUrl().split("/")[driver.getCurrentUrl().split("/").length - 1], "login"));
        }
    }

    /**
     * Attempts to log in with the supplied credentials.
     */
    public void login(String email, String password) {        
        // If already logged in, return
        if (isLoggedIn()) {
            System.out.println("Already logged in, skipping login process");
            return;
        }
        
        // Navigate to login page if not already there
        if (!driver.getCurrentUrl().contains("/login")) {
            navigateToLoginPage();
        }
        
        // Clear and enter email
        WebElement emailElement = wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailElement.clear();
        emailElement.sendKeys(email);

        // Clear and enter password
        WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordElement.clear();
        passwordElement.sendKeys(password);

        // Click login button
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
        loginButton.click();

        // Wait for login to complete - either success or error
        shortWait.until(driver -> {
            return isLoggedIn() || errorVisible() || !driver.getCurrentUrl().contains("/login");
        });
    }

    public void clickLogout() {
        if (!isLoggedIn()) {
            System.out.println("User is not logged in, cannot logout");
            return;
        }
        
        try {
            // Try to find logout link with shorter wait
            WebElement logoutElement = shortWait.until(ExpectedConditions.elementToBeClickable(logoutLink));
            logoutElement.click();
        } catch (Exception e) {
            System.out.println("Could not find logout link: " + e.getMessage());
            throw new RuntimeException("Logout failed: " + e.getMessage());
        }
    }

    /* ------------------------------------------------------------------
     * Assertions / state helpers
     * ------------------------------------------------------------------ */

    /** 
     * Check if error message is visible after failed login
     */
    public boolean errorVisible() {
        try {
            // Shorter wait for error to appear
            WebElement errorElement = shortWait.until(ExpectedConditions.visibilityOfElementLocated(errorBanner));
            return errorElement.isDisplayed();
        } catch (Exception e) {
            // Try alternative error message locators with no wait
            try {
                List<WebElement> errorElements = driver.findElements(By.xpath("//*[contains(text(), 'incorrect') or contains(text(), 'invalid') or contains(text(), 'wrong')]"));
                return !errorElements.isEmpty() && errorElements.get(0).isDisplayed();
            } catch (Exception e2) {
                return false;
            }
        }
    }

    /** 
     * Optimized method to check if user is logged in.
     * Removed debug output for faster execution.
     */
    public boolean isLoggedIn() {
        try {
            // Quick check: if not on login page and no error, likely logged in
            boolean notOnLoginPage = !driver.getCurrentUrl().contains("/login");
            if (notOnLoginPage && !errorVisible()) {
                return true;
            }
            
            // Fast element checks without waits
            List<WebElement> loggedInElements = driver.findElements(loggedInAsText);
            if (!loggedInElements.isEmpty() && loggedInElements.get(0).isDisplayed()) {
                return true;
            }
            
            List<WebElement> logoutElements = driver.findElements(logoutLink);
            if (!logoutElements.isEmpty() && logoutElements.get(0).isDisplayed()) {
                return true;
            }
            
            List<WebElement> deleteElements = driver.findElements(deleteAccountLink);
            if (!deleteElements.isEmpty() && deleteElements.get(0).isDisplayed()) {
                return true;
            }
            
            // Check navigation bar for user-specific elements
            List<WebElement> navElements = driver.findElements(navigationBar);
            if (!navElements.isEmpty()) {
                String navText = navElements.get(0).getText().toLowerCase();
                if (navText.contains("logout") || navText.contains("account") || navText.contains("profile")) {
                    return true;
                }
            }
            
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Debug method to print all available elements that might indicate login status
     */
    public void debugLoginElements() {
        System.out.println("=== DEBUG: Available elements ===");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
        
        // Print all links on the page
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        System.out.println("All links on page:");
        for (WebElement link : allLinks) {
            try {
                String text = link.getText().trim();
                String href = link.getAttribute("href");
                if (!text.isEmpty() || (href != null && !href.isEmpty())) {
                    System.out.println("  Link: '" + text + "' -> " + href);
                }
            } catch (Exception e) {
                // Skip problematic elements
            }
        }
        
        // Print page source (first 1000 characters)
        String pageSource = driver.getPageSource();
        System.out.println("Page source snippet: " + pageSource.substring(0, Math.min(1000, pageSource.length())));
    }
}