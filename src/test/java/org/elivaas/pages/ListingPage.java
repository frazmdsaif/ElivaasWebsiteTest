package org.elivaas.pages;

import org.elivaas.utils.PropertiesLoader;
import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;

public class ListingPage {
    WebDriver driver;

    public ListingPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }

    //write all the locators here

    @FindBy(xpath = "//pre[@class='inline-block font-mono']")
    private WebElement villaCountInCity;

    public void openListingPageViaTest() throws IOException,InterruptedException {
        String cityName = PropertiesLoader.loadProperty("city");
        SearchFunction searchFunction=new SearchFunction(driver);
        List<WebElement> result=searchFunction.listOfCityVisible(cityName);
        result.get(0).click();
        searchFunction.searchSubmitButton().click();
        Thread.sleep(3000);
    }

    public String villaCountInCityVisible() throws IOException,InterruptedException{
        //openListingPageViaTest();
        SeleniumHelper.waitForElementToBeVisible(driver, villaCountInCity);
        return villaCountInCity.getText();
    }
}
