package org.elivaas.tests;


import org.elivaas.pages.HomePage;
import org.elivaas.pages.SearchFunction;

import org.elivaas.utils.PropertiesLoader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestCase2 extends TestBasic{


    @Test(description = "Verify that search function is visible")
    public void verifyThatSearchFunctionIsVisible() {
        boolean searchFunctionVisible =new SearchFunction(getDriver()).selectDestinationVisible().isDisplayed();
        Assert.assertTrue(searchFunctionVisible, "Search function should be visible");
    }

    @Test(description = "Verify that search function is clickable")
    public void verifyThatSearchFunctionIsClickable() {
        boolean isClickable =new SearchFunction(getDriver()).selectDestinationVisible().isEnabled();
        Assert.assertTrue(isClickable, "Search function should be clickable");
    }

    @Test(description = "verify that city input is working")
    public void verifyThatCityInputIsWorking() throws IOException {
        String cityName = PropertiesLoader.loadProperty("city");
        new SearchFunction(getDriver()).cityInputVisible(cityName);
    }
}
