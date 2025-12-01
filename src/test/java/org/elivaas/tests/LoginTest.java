package org.elivaas.tests;

import org.elivaas.core.BaseTest;
import org.elivaas.pages.HomePage;
import org.elivaas.pages.UserLogin;
import org.elivaas.utils.LoginBy;
import org.elivaas.utils.PropertiesLoader;
import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends BaseTest {

    @Test(description = "Verify that login with gmail is working")
    public void verifyLoginWithGmailIsWorking() throws IOException, InterruptedException {
        LoginBy login = new LoginBy();
        login.gmail(driver);
    }

    @Test(description = "verify that login with otp is working")
    public void verifyLoginWithOtpIsWorking() throws IOException, InterruptedException {
        HomePage homePage = new HomePage(driver);
        SeleniumHelper.waitForElementToBeClickable(driver, homePage.loginSignUpVisible());
        homePage.loginSignUpVisible().click();
        UserLogin userLogin = new UserLogin(driver);
        String number = PropertiesLoader.loadProperty("phone");
        userLogin.enterPhoneNumber(number);
        userLogin.clickContinueButton();
        LoginBy login = new LoginBy();
        String otpReceived = login.getOTP();
        userLogin.enterOtp(otpReceived);
        Thread.sleep(2000);
        driver.navigate().refresh();
        String nameAfterLogin = driver.findElement(By.xpath("//span[@class='text-sm font-semibold']")).getText();
        System.out.println(nameAfterLogin);
        Assert.assertEquals(nameAfterLogin, "SF");
    }


}
