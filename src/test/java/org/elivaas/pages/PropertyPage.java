package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PropertyPage {

     WebDriver driver;

    public PropertyPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }

    @FindBy(xpath = "//div[text()='Overview']")
    WebElement overview;

    @FindBy(xpath = "//div[text()='About Home']")
    WebElement aboutHome;

    @FindBy(xpath = "//div[text()='Services']")
    WebElement services;

    @FindBy(xpath = "//div[text()='Amenities']")
    WebElement amenities;

    @FindBy(xpath = "//div[text()='Spaces']")
    WebElement spaces;

    @FindBy(xpath = "//div[text()='Location']")
    WebElement location;

    @FindBy(xpath = "//div[text()='Cancellation']")
    WebElement cancellation;

    @FindBy(xpath = "//div[text()='Reviews']")
    WebElement reviews;

    public WebElement reviewsVisible(){
        SeleniumHelper.waitForElementToBeVisible(driver,reviews);
        return reviews;
    }

    public WebElement cancellationVisible(){
        SeleniumHelper.waitForElementToBeVisible(driver,cancellation);
        return cancellation;
    }

    public WebElement locationVisible(){
        SeleniumHelper.waitForElementToBeVisible(driver,location);
        return location;
    }

    public WebElement spacesVisible(){
        SeleniumHelper.waitForElementToBeVisible(driver,spaces);
        return spaces;
    }

    public WebElement amenitiesVisible(){
        SeleniumHelper.waitForElementToBeVisible(driver,amenities);
        return amenities;
    }

    public WebElement servicesVisible(){
        SeleniumHelper.waitForElementToBeVisible(driver,services);
        return services;
    }

    public WebElement aboutHomeVisible(){
        SeleniumHelper.waitForElementToBeVisible(driver,aboutHome);
        return aboutHome;
    }

    public WebElement overviewVisible(){
        SeleniumHelper.waitForElementToBeVisible(driver,overview);
        return overview;
    }



}
