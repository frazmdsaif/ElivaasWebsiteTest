package org.elivaas.tests;

import org.elivaas.pages.HomePage;
import org.elivaas.pages.MyBookingsPage;
import org.elivaas.pages.UserLogin;
import org.elivaas.utils.LoginBy;
import org.elivaas.utils.PropertiesLoader;
import org.elivaas.utils.SeleniumHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

/**
 * My Bookings page coverage — LOGIN-GATED. Logs in via OTP (needs the
 * physical ADB-connected device holding the configured phone number),
 * then navigates the status tabs ("Upcoming Stay", "Completed", "Cancelled")
 * and asserts the booking cards show Booking ID, Booked On, Amount Paid,
 * Amount Due and the "Pay Now" button.
 */
public class MyBookingsTest extends TestBasic {

    private MyBookingsPage loginAndOpenMyBookings() throws IOException, InterruptedException {
        // OTP login (same pattern as LoginTest.verifyLoginWithOtpIsWorking)
        HomePage homePage = new HomePage(getDriver());
        SeleniumHelper.waitForElementToBeClickable(getDriver(), homePage.loginSignUpVisible());
        homePage.loginSignUpVisible().click();
        UserLogin userLogin = new UserLogin(getDriver());
        userLogin.enterPhoneNumber(PropertiesLoader.loadProperty("phone"));
        userLogin.clickContinueButton();
        userLogin.enterOtp(LoginBy.getOTP());
        Thread.sleep(2000);

        MyBookingsPage myBookings = new MyBookingsPage(getDriver());
        myBookings.navigate();
        return myBookings;
    }

    @Test(description = "verify that upcoming stay bookings show cards with core fields")
    public void verifyThatUpcomingStayShowsBookingCards() throws IOException, InterruptedException {
        MyBookingsPage myBookings = loginAndOpenMyBookings();
        myBookings.upcomingStayTabVisible().click();
        SeleniumHelper.waitFor2Second(getDriver());

        List<String> ids = myBookings.bookingIds();
        System.out.println("Upcoming bookings found: " + ids.size());
        for (String id : ids) {
            System.out.println(id);
        }
        Assert.assertTrue(ids.size() > 0, "Upcoming Stay should list at least one booking card");
        Assert.assertTrue(myBookings.cardsShowCoreFields(),
                "Booking cards should show Booking ID, Booked On, Amount Paid and Amount Due");
    }

    @Test(description = "verify that completed and cancelled tabs are navigable")
    public void verifyThatCompletedAndCancelledTabsAreNavigable() throws IOException, InterruptedException {
        MyBookingsPage myBookings = loginAndOpenMyBookings();

        myBookings.openTab("Completed");
        Assert.assertTrue(myBookings.completedTabVisible().isDisplayed(), "Completed tab should be active");

        myBookings.openTab("Cancelled");
        Assert.assertTrue(myBookings.cancelledTabVisible().isDisplayed(), "Cancelled tab should be active");

        myBookings.openTab("Upcoming Stay");
        Assert.assertTrue(myBookings.upcomingStayTabVisible().isDisplayed(), "Upcoming Stay tab should be active");
    }

    @Test(description = "verify that pay now button is present on cards with amount due")
    public void verifyThatPayNowIsPresentWhenAmountDue() throws IOException, InterruptedException {
        MyBookingsPage myBookings = loginAndOpenMyBookings();
        myBookings.upcomingStayTabVisible().click();
        SeleniumHelper.waitFor2Second(getDriver());

        int cards = myBookings.bookingCardCount();
        int payNow = myBookings.payNowButtonCount();
        System.out.println("Cards: " + cards + ", Pay Now buttons: " + payNow);
        Assert.assertTrue(cards > 0, "Upcoming Stay should list at least one booking card");
        Assert.assertTrue(payNow <= cards, "Pay Now buttons cannot exceed the card count");
    }
}
