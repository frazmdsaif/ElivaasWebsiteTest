package org.elivaas.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }

    @FindBy(xpath = "(//div)[2]")
    private WebElement banner;

    @FindBy(xpath = "//*[text()='Log In / Sign Up']")
    private WebElement loginSignUp ;

    @FindBy(xpath = "//*[text()='Partner with Us']")
    private WebElement partnerWithUs;



    public  WebElement partnerWithUsVisible(){
        return partnerWithUs;
    }

    public  WebElement BannerVisible(){
        return banner;
    }

    public  WebElement loginSignUpVisible(){
        return loginSignUp;
    }



}
