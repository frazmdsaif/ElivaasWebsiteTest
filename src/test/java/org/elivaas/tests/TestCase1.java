package org.elivaas.tests;

import org.elivaas.pages.HomePage;
import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCase1 extends TestBasic {
    


    @Test(description = "Verify that homepage is visible successfully")
    public void verifyThatHomePageIsVisible() {
        SeleniumHelper.waitFor2Second(getDriver());
        boolean homePageVisible =new HomePage(getDriver())
                .BannerVisible().isDisplayed();
        Assert.assertTrue(homePageVisible, "Homepage banner should be visible");
    }

    @Test(description = "Verify that login/Sign up button is visible")
    public void verifyThatLoginSignUpButtonIsVisible() {
        HomePage homePage=new HomePage(getDriver());
        SeleniumHelper.waitForElementToBeVisible(getDriver(), homePage.loginSignUpVisible());
        boolean loginSignUpButton =new  HomePage(getDriver()).loginSignUpVisible().isDisplayed();
        Assert.assertTrue(loginSignUpButton, "Login/Sign up button should be visible");
    }

    @Test(description = "Verify that login/Sign up button is clickable")
    public void verifyThatLoginSignUpButtonIsClickable() {
        SeleniumHelper.waitFor2Second(getDriver());
        boolean isClickable =new HomePage(getDriver()).loginSignUpVisible().isEnabled();
        Assert.assertTrue(isClickable, "Login/Sign up button should be clickable");
    }
    
    @Test(description = "Verify that banner is clickable")
    public void verifyBannerIsClickable() {
        SeleniumHelper.waitFor2Second(getDriver());
        boolean isBannerClickable =new HomePage(getDriver()).BannerVisible().isEnabled();
        Assert.assertTrue(isBannerClickable, "Banner should be clickable");
    }
    
    @Test(description = "Verify page title")
    public void verifyPageTitle() {
        SeleniumHelper.waitFor2Second(getDriver());
        String pageTitle = getDriver().getTitle();
        Assert.assertNotNull(pageTitle, "Page title should not be null");
        Assert.assertNotEquals(pageTitle, "", "Page title should not be empty");
    }
    
    @Test(description = "Verify login/signup button text")
    public void verifyLoginSignUpButtonText() {
        HomePage homePage=new HomePage(getDriver());
        SeleniumHelper.waitFor2Second(getDriver());
        String buttonText =homePage.loginSignUpVisible().getText().trim();
        Assert.assertEquals(buttonText, "Log In / Sign Up", "Button text should be 'Log In / Sign Up'");
    }

    @Test(description = "Verify partner with us button is visible")
    public void verifyPartnerWithUsButtonIsVisible() {
        SeleniumHelper.waitFor2Second(getDriver());
        boolean partnerWithUsButton =new HomePage(getDriver()).partnerWithUsVisible().isDisplayed();
        Assert.assertTrue(partnerWithUsButton, "Partner with us button should be visible");
    }

    @Test(description = "Verify partner with us button is clickable")
    public void verifyPartnerWithUsButtonIsClickable() {
        SeleniumHelper.waitFor2Second(getDriver());
        boolean isClickable =new HomePage(getDriver()).partnerWithUsVisible().isEnabled();
        Assert.assertTrue(isClickable, "Partner with us button should be clickable");
    }

    @Test(description = "Verify partner with us button text")
    public void verifyPartnerWithUsButtonText() {
        SeleniumHelper.waitFor2Second(getDriver());
        String buttonText =new HomePage(getDriver()).partnerWithUsVisible().getText().trim();
        Assert.assertEquals(buttonText, "Partner with Us", "Button text should be 'Partner with Us'");
    }



}
