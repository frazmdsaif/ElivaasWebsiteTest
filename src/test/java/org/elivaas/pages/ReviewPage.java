package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReviewPage {

    WebDriver driver;

    public ReviewPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//span[@role='link']")
    WebElement reviewOnHeader;

    public String getTextFromReviewOnHeader(){
        SeleniumHelper.waitForElementToBeVisible(driver,reviewOnHeader);
        return reviewOnHeader.getText();
    }
}
