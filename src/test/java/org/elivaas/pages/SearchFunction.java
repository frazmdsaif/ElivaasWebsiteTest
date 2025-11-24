package org.elivaas.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchFunction {

    WebDriver driver;

    public SearchFunction(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }

    //write all the locators here

    @FindBy(xpath = "//*[text()='Select destination']")
    private WebElement selectDestination;

    @FindBy(xpath = "//input[@placeholder='Where are you planning to stay?']")
    private WebElement cityInput;

    @FindBy(xpath = "//div[@class='space-y-1']/div")
    private WebElement listOfCity;

    //write all methods here



    public  void cityInputVisible(String cityname){
        selectDestination.click();
        cityInput.sendKeys(cityname);
    }

    public  WebElement selectDestinationVisible(){
        return selectDestination;
    }

}
