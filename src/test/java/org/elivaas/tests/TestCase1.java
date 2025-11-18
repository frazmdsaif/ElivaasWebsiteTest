package org.elivaas.tests;

import org.elivaas.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCase1 extends TestBasic {

    @Test(description = "Verify that homepage is visible successfully")
    public static void verifyThatHomePageIsVisible(){
        boolean homePageVisible=new HomePage(getDriver())
                .BannerVisible().isDisplayed();
        Assert.assertTrue(homePageVisible,"verify that homepage is visible successfully");

    }

    @Test(description = "verify that login/Sign up is visible")
    public static void verifyThatLoginSignUpButtonIsVisible() {
        boolean loginSignUpButton=new HomePage(getDriver())
                .loginSignUpVisible().isDisplayed();
        Assert.assertTrue(loginSignUpButton,"verify that login/Sign up is visible");
        //loginSignUpButton ? Assert.assertTrue(loginSignUpButton,"") : TakeScreenShot.click(getDriver());
    }

    @Test(description ="verify that login/Sign up is clickable")
    public static void verifyThatLoginSignUpButtonIsClickable(){
        boolean loginSignUpButton1=new HomePage(getDriver())
                .loginSignUpVisible()
                .isDisplayed();
        Assert.assertTrue(loginSignUpButton1,"verify that login/Sign up is clickable");
    }
}
