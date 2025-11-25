package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchFunction {

    WebDriver driver;

    public SearchFunction(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    //write all the locators here

    @FindBy(xpath = "//*[text()='Select destination']")
    private WebElement selectDestination;

    @FindBy(xpath = "//input[@placeholder='Where are you planning to stay?']")
    private WebElement cityInput;

    @FindBy(xpath = "//div[@class='space-y-1']/div")
    private List<WebElement> listOfCity;

    @FindBy(xpath = "//button[@class=\"gap-2 whitespace-nowrap text-sm font-medium transition-all disabled:pointer-events-none disabled:opacity-50 [&_svg]:pointer-events-none [&_svg:not([class*='size-'])]:size-4 shrink-0 [&_svg]:shrink-0 outline-none focus-visible:border-ring focus-visible:ring-ring/50 focus-visible:ring-[3px] aria-invalid:ring-destructive/20 dark:aria-invalid:ring-destructive/40 aria-invalid:border-destructive shadow-xs px-6 has-[>svg]:px-4 flex cursor-pointer items-center justify-center text-white bg-accent-red-900 hover:bg-accent-red-900/90 rounded-2xl h-[54px] w-[54px]\"]")
    private WebElement searchSubmitButton;

    @FindBy(xpath = "//*[text()='Check-In']")
    private WebElement checkInDate;


    @FindBy(xpath = "//*[text()='Check-Out']" )
    private WebElement checkOutDate;



    //write all methods here

    public WebElement checkOutDateButton(){
        SeleniumHelper.waitForElementToBeVisible(driver, checkOutDate);
        return checkOutDate;
    }

    public WebElement checkInDateButton(){
        SeleniumHelper.waitForElementToBeVisible(driver, checkInDate);
        return checkInDate;
    }

    public WebElement searchSubmitButton() {
        SeleniumHelper.waitForElementToBeVisible(driver, searchSubmitButton);
        return searchSubmitButton;
    }


    public void cityInputVisible(String cityname) {
        SeleniumHelper.waitForElementToBeVisible(driver, selectDestinationVisible());
        selectDestinationVisible().click();
        cityInput.sendKeys(cityname);
        SeleniumHelper.waitFor2Second(driver);
    }

    public WebElement selectDestinationVisible() {
        return selectDestination;
    }

    public List<WebElement> listOfCityVisible(String n) {
        cityInputVisible(n);
        return listOfCity;
    }


}
