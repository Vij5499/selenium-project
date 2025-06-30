package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- Locators ---
    private final By searchInput = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By allProductsContainer = By.cssSelector(".features_items");
    private final By searchedProductsTitle = By.xpath("//h2[text()='Searched Products']");
    private final By viewCartInModal = By.xpath("//div[@id='cartModal']//a[@href='/view_cart']");
    
    // --- Dynamic Locators ---
    // DEFINITIVE FIX: This locator finds the product's container based on its name. It correctly handles apostrophes.
    private final String productContainerByName = "//div[contains(@class, 'product-image-wrapper') and .//p[text()=\"%s\"]]";
    // These locators find child elements from within the container.
    private final By addToCartFromContainer = By.xpath(".//a[contains(@class, 'add-to-cart')]");
    private final By viewProductFromContainer = By.xpath(".//a[contains(@href, '/product_details')]");


    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // --- Core Action Methods ---

// In your ProductsPage.java file...

    public void addProductToCart(String productName) {
        performSearchAndWait(productName);
        By productLocator = By.xpath(String.format(productContainerByName, productName));
        WebElement productContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));
        WebElement addToCartButton = productContainer.findElement(addToCartFromContainer);
        // Use a JS click for maximum stability
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
    }

    public ProductDetailsPage viewProduct(String productName) {
        performSearchAndWait(productName);
        By productLocator = By.xpath(String.format(productContainerByName, productName));
        WebElement productContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));
        WebElement viewProductLink = productContainer.findElement(viewProductFromContainer);
        // Use a JS click for maximum stability
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewProductLink);
        return new ProductDetailsPage(driver);
    }
    
    public CartPage clickViewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartInModal)).click();
        return new CartPage(driver);
    }
    
    // --- Helper & Verification Methods ---
    
    public void searchForProduct(String query) {
        performSearchAndWait(query);
    }

    public boolean isSearchedProductsTitleVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(searchedProductsTitle)).isDisplayed();
    }
    
    public int getDisplayedProductsCount() {
        return driver.findElements(By.cssSelector(".features_items .product-image-wrapper")).size();
    }
    
    private void performSearchAndWait(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(allProductsContainer));
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).sendKeys(productName);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchedProductsTitle));
    }
}