package org.elivaas.tests;

import org.elivaas.pages.PropertyDetailPage;
import org.elivaas.pages.UserLogin;
import org.elivaas.utils.LoginBy;
import org.elivaas.utils.PropertiesLoader;
import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestCase5 extends TestBasic{

    @Test(description = "Verify that property details page is visible successfully")
    public void verifyThatPropertyDetailsPageIsVisible() throws InterruptedException {
        PropertyDetailPage propertyDetailPage=new PropertyDetailPage(getDriver());
        String propertyName=propertyDetailPage.CurrentPropertyName();
        System.out.println(propertyName);

    }
    @Test(description = "verify property price")
    public void verifyPropertyPrice() throws InterruptedException {
        PropertyDetailPage propertyDetailPage=new PropertyDetailPage(getDriver());
        String propertyPrice=propertyDetailPage.CurrentPropertyPrice();
        int price = Integer.parseInt(propertyPrice);
        System.out.println("Price as integer: " + price);
        assert price > 1 : "Price should be greater than 1";
    }

    @Test(description = "verify that checkin button is visible")
    public void verifyThatCheckinButtonIsVisible() throws InterruptedException {
        PropertyDetailPage propertyDetailPage=new PropertyDetailPage(getDriver());
        Boolean status_of_button=propertyDetailPage.clickOnCheckin().isDisplayed();
        assert status_of_button : "Checkin button should be visible";
    }

    @Test(description = "verify that checkout button is visible")
    public void verifyThatCheckoutButtonIsVisible() throws InterruptedException {
        PropertyDetailPage propertyDetailPage=new PropertyDetailPage(getDriver());
        Boolean status_of_button=propertyDetailPage.clickOnCheckout().isDisplayed();
        assert status_of_button : "Checkout button should be visible";
    }

    @Test(description = "date selection on pdp page")
    public void verifyThatdateSelectionOnPdpPageIsWorking() throws InterruptedException {
        PropertyDetailPage propertyDetailPage=new PropertyDetailPage(getDriver());
        propertyDetailPage.dateSelection();
        String name=propertyDetailPage.get_text_of_discount();
        assert name.equals("Discount Coupon");
    }

    @Test(description = "click on proceed pn pay without login")
    public void verifythatProceedToPayIsworking() throws InterruptedException{
        PropertyDetailPage propertyDetailPage=new PropertyDetailPage(getDriver());
        propertyDetailPage.dateSelection();
        propertyDetailPage.dragPage(200);
        assert propertyDetailPage.click_on_proceed_to_pay().isEnabled();
    }

    @Test(description = "verify that click on proceed to will open login modal")
    public void verifythatProceedOpenLoginWithOTP() throws InterruptedException, IOException {
        PropertyDetailPage propertyDetailPage=new PropertyDetailPage(getDriver());
        propertyDetailPage.dateSelection();
        propertyDetailPage.dragPage(400);
        propertyDetailPage.click_on_proceed_to_pay().click();
        Thread.sleep(3000);
        UserLogin userLogin = new UserLogin(getDriver());
        userLogin.enterPhoneNumber(PropertiesLoader.loadProperty("phone"));
        userLogin.clickContinueButton();
        LoginBy login = new LoginBy();
        userLogin.enterOtp(login.getOTP());
        Thread.sleep(2000);

    }




}
