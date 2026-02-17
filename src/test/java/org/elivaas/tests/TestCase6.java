package org.elivaas.tests;

import org.elivaas.pages.PropertyDetailPage;
import org.elivaas.pages.ReviewPage;
import org.elivaas.pages.UserLogin;
import org.elivaas.utils.LoginBy;
import org.elivaas.utils.PropertiesLoader;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestCase6 extends TestBasic{

    @Test(description = "verify that review page is opening successfully")
    public void verifyThatReviewPageIsWorking() throws InterruptedException, IOException {
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
        Thread.sleep(6000);
        ReviewPage reviewPage=new ReviewPage(getDriver());
        String textfromReview=reviewPage.getTextFromReviewOnHeader();
        assert textfromReview.equals("Review Booking");
    }


}
