package org.elivaas.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;


import java.time.Duration;

public class SeleniumHelper {

    public static void waitForElementToBeVisible(WebDriver driver, WebElement locator){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5L));
        wait.until(ExpectedConditions.visibilityOf(locator));
    }

    public static void waitForElementToBeClickable(WebDriver driver, WebElement locator){
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.refreshed(elementToBeClickable(locator)));
    }

    public static void waitFor2Second(WebDriver driver){
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.withTimeout(Duration.ofSeconds(3));
    }



    /**
     * Switches the driver to the window/tab that is NOT the currently active one.
     * Used after clicking a property card on the listing page, which opens the
     * property detail page in a new window.
     */
    public static void switchToNewWindow(WebDriver driver){
        String currentWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(currentWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    /**
     * Parses a price string like "₹12,450" or "₹ 12,450.00" into a double.
     * Strips the rupee symbol, whitespace, commas and any trailing text.
     */
    public static double parsePrice(String priceText){
        if (priceText == null) {
            return 0.0;
        }
        String cleaned = priceText.replace("₹", "").replace(",", "").trim();
        StringBuilder digits = new StringBuilder();
        boolean dotSeen = false;
        for (char c : cleaned.toCharArray()) {
            if (Character.isDigit(c)) {
                digits.append(c);
            } else if (c == '.' && !dotSeen) {
                dotSeen = true;
                digits.append(c);
            } else if (digits.length() > 0) {
                break;
            }
        }
        if (digits.length() == 0) {
            return 0.0;
        }
        return Double.parseDouble(digits.toString());
    }

    /**
     * Extracts the first ₹ amount found inside a longer text,
     * e.g. "Proceed to Pay ₹12,450" -> 12450.0.
     */
    public static double parsePriceFromText(String text){
        if (text == null) {
            return 0.0;
        }
        int index = text.indexOf('₹');
        if (index < 0) {
            return parsePrice(text);
        }
        return parsePrice(text.substring(index));
    }

}
