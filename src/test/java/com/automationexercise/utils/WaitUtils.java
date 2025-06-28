package com.automationexercise.utils;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class WaitUtils {

    public static WebElement waitVisible(WebDriver driver, By by, int sec) {
        return new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }


    public static void scrollToTop(WebDriver driver) {
    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0);");
    }

    /** Wait for element clickable and return it */
    public static WebElement waitClickable(WebDriver driver, By by, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.elementToBeClickable(by));
    }

}
