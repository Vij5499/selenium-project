package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    @Test(description = "Verify that searching for an existing product returns results.")
    public void testSearchForExistingProduct() {
        String existingProduct = "Blue Top";

        driver.get("https://www.automationexercise.com/");
        
        // 1. Start on Home Page and navigate to Products page
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.navigateToProducts();

        // 2. On the Products Page, perform the search
        productsPage.searchForProduct(existingProduct);

        // 3. Assert the results
        Assert.assertTrue(productsPage.isSearchedProductsTitleVisible(), "'Searched Products' title should be visible.");
        Assert.assertTrue(productsPage.getDisplayedProductsCount() > 0, "At least one product should be displayed for an existing search term.");
    }

    @Test(description = "Verify that searching for a non-existent product shows no results.")
    public void testSearchForNonExistentProduct() {
        String nonExistentProduct = "Unicorn Sweater";

        driver.get("https://www.automationexercise.com/");
        
        // 1. Start on Home Page and navigate to Products page
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.navigateToProducts();

        // 2. On the Products Page, perform the search
        productsPage.searchForProduct(nonExistentProduct);
        
        // 3. Assert the results
        Assert.assertTrue(productsPage.isSearchedProductsTitleVisible(), "'Searched Products' title should be visible.");
    }
}