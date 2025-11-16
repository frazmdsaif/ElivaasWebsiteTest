package org.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;


import java.time.Duration;

public class SeleniumHelper {

    public static void waitForElementToBeVisible(WebDriver driver, WebElement locator){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5L));
        wait.until(ExpectedConditions.visibilityOf(locator));
    }

    public static void waitForElementToBeClickable(WebDriver driver, By locator){
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.refreshed(elementToBeClickable(locator)));
    }


}
