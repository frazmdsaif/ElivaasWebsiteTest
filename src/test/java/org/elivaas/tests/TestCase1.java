package org.elivaas.tests;

import org.elivaas.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCase1 extends TestBasic {
    


    @Test(description = "Verify that homepage is visible successfully")
    public void verifyThatHomePageIsVisible() {
        boolean homePageVisible =new HomePage(getDriver())
                .BannerVisible().isDisplayed();
        Assert.assertTrue(homePageVisible, "Homepage banner should be visible");
    }

    @Test(description = "Verify that login/Sign up button is visible")
    public void verifyThatLoginSignUpButtonIsVisible() {
        boolean loginSignUpButton =new  HomePage(getDriver()).loginSignUpVisible().isDisplayed();
        Assert.assertTrue(loginSignUpButton, "Login/Sign up button should be visible");
    }

    @Test(description = "Verify that login/Sign up button is clickable")
    public void verifyThatLoginSignUpButtonIsClickable() {
        boolean isClickable =new HomePage(getDriver()).loginSignUpVisible().isEnabled();
        Assert.assertTrue(isClickable, "Login/Sign up button should be clickable");
    }
    
    @Test(description = "Verify that banner is clickable")
    public void verifyBannerIsClickable() {
        boolean isBannerClickable =new HomePage(getDriver()).BannerVisible().isEnabled();
        Assert.assertTrue(isBannerClickable, "Banner should be clickable");
    }
    
    @Test(description = "Verify page title")
    public void verifyPageTitle() {
        String pageTitle = getDriver().getTitle();
        Assert.assertNotNull(pageTitle, "Page title should not be null");
        Assert.assertNotEquals(pageTitle, "", "Page title should not be empty");
    }
    
    @Test(description = "Verify login/signup button text")
    public void verifyLoginSignUpButtonText() {
        String buttonText =new  HomePage(getDriver()).loginSignUpVisible().getText().trim();
        Assert.assertEquals(buttonText, "Log In / Sign Up", "Button text should be 'Log In / Sign Up'");
    }

    @Test(description = "Verify partner with us button is visible")
    public void verifyPartnerWithUsButtonIsVisible() {
        boolean partnerWithUsButton =new HomePage(getDriver()).partnerWithUsVisible().isDisplayed();
        Assert.assertTrue(partnerWithUsButton, "Partner with us button should be visible");
    }

    @Test(description = "Verify partner with us button is clickable")
    public void verifyPartnerWithUsButtonIsClickable() {
        boolean isClickable =new HomePage(getDriver()).partnerWithUsVisible().isEnabled();
        Assert.assertTrue(isClickable, "Partner with us button should be clickable");
    }

    @Test(description = "Verify partner with us button text")
    public void verifyPartnerWithUsButtonText() {
        String buttonText =new HomePage(getDriver()).partnerWithUsVisible().getText().trim();
        Assert.assertEquals(buttonText, "Partner with Us", "Button text should be 'Partner with Us'");
    }



}
