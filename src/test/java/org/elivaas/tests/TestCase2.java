package org.elivaas.tests;


import org.elivaas.pages.HomePage;
import org.elivaas.pages.SearchFunction;

import org.elivaas.utils.PropertiesLoader;
import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class TestCase2 extends TestBasic{


    @Test(description = "Verify that search function is visible")
    public void verifyThatSearchFunctionIsVisible() {
        SeleniumHelper.waitForElementToBeClickable(getDriver(), new SearchFunction(getDriver()).selectDestinationVisible());
        boolean searchFunctionVisible =new SearchFunction(getDriver()).selectDestinationVisible().isDisplayed();
        Assert.assertTrue(searchFunctionVisible, "Search function should be visible");
    }

    @Test(description = "Verify that search function is clickable")
    public void verifyThatSearchFunctionIsClickable() {
        SearchFunction searchFunction=new SearchFunction(getDriver());
        SeleniumHelper.waitForElementToBeClickable(getDriver(), searchFunction.selectDestinationVisible());
        boolean isClickable =new SearchFunction(getDriver()).selectDestinationVisible().isEnabled();
        //SeleniumHelper.waitFor2Second(getDriver());
        Assert.assertTrue(isClickable, "Search function should be clickable");
    }

    @Test(description = "verify that city input is working")
    public void verifyThatCityInputIsWorking() throws IOException {
        String cityName = PropertiesLoader.loadProperty("city");
        SearchFunction searchFunction=new SearchFunction(getDriver());
        searchFunction.cityInputVisible(cityName);
        SeleniumHelper.waitFor2Second(getDriver());
    }

    @Test(description = "print list of cities")
    public void printListOfCities() throws IOException {
        String cityName = PropertiesLoader.loadProperty("city");
        SearchFunction searchFunction=new SearchFunction(getDriver());
        List<WebElement> result=searchFunction.listOfCityVisible(cityName);
        SeleniumHelper.waitFor2Second(getDriver());
        System.out.println(result.size());
        for(WebElement r:result){
            System.out.println(r.getText());
        }
        int count=result.size();
        Assert.assertTrue(count>0, "List should not be empty");
    }

    @Test(description = "verify that first city in list clickable")
    public void verifyThatFirstCityInListShouldOpenNewCityPage() throws IOException,InterruptedException {
        String cityName = PropertiesLoader.loadProperty("city");
        SearchFunction searchFunction=new SearchFunction(getDriver());
        List<WebElement> result=searchFunction.listOfCityVisible(cityName);
        result.get(0).click();
        Thread.sleep(2000);
        searchFunction.searchSubmitButton().click();
        Thread.sleep(3000);
        String url=getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains(cityName.toLowerCase()), "URL should contain city name");
    }

    @Test(description = "verify that submit button is clickable")
    public void verifyThatSubmitButtonIsClickable() {
        SearchFunction searchFunction=new SearchFunction(getDriver());
        SeleniumHelper.waitForElementToBeClickable(getDriver(), searchFunction.searchSubmitButton());
        boolean isClickable =new SearchFunction(getDriver()).searchSubmitButton().isEnabled();
        Assert.assertTrue(isClickable, "Submit button should be clickable");
    }

    @Test(description = "verify that check-in date is clickable")
    public void verifyThatCheckInButtonIsClickable() {
        SearchFunction searchFunction=new SearchFunction(getDriver());
        SeleniumHelper.waitForElementToBeClickable(getDriver(), searchFunction.checkInDateButton());
        boolean isClickable =new SearchFunction(getDriver()).checkInDateButton().isEnabled();
        Assert.assertTrue(isClickable, "Check-in button should be clickable");
    }

    @Test(description = "verify that check-out date is clickable")
    public void verifyThatCheckOutButtonIsClickable() {
        SearchFunction searchFunction=new SearchFunction(getDriver());
        SeleniumHelper.waitForElementToBeClickable(getDriver(), searchFunction.checkOutDateButton());
        boolean isClickable =new SearchFunction(getDriver()).checkOutDateButton().isEnabled();
        Assert.assertTrue(isClickable, "Check-out button should be clickable");
    }



}
