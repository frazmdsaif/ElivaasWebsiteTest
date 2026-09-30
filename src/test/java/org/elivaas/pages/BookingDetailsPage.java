package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Booking detail page (/booking-details/&lt;BOOKING_ID&gt;) — read-only
 * confirmation view. Covers: Booking ID, check-in/check-out dates, amounts
 * (Amount Paid / Amount Due) and "Primary Guest Details".
 */
public class BookingDetailsPage {

    WebDriver driver;

    public BookingDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //write all the locators here

    @FindBy(xpath = "//*[contains(normalize-space(),'Booking ID')]")
    private WebElement bookingIdText;

    @FindBy(xpath = "//*[contains(normalize-space(),'Amount Paid')]")
    private WebElement amountPaidText;

    @FindBy(xpath = "//*[contains(normalize-space(),'Amount Due')]")
    private WebElement amountDueText;

    @FindBy(xpath = "//*[normalize-space()='Primary Guest Details']")
    private WebElement primaryGuestDetailsHeading;

    //write all methods here

    /**
     * Opens the booking detail page for the given booking id.
     */
    public void open(String bookingId) {
        driver.get("https://www.elivaas.com/booking-details/" + bookingId);
        SeleniumHelper.waitFor2Second(driver);
    }

    public String bookingIdText() {
        SeleniumHelper.waitForElementToBeVisible(driver, bookingIdText);
        return bookingIdText.getText();
    }

    public double amountPaid() {
        SeleniumHelper.waitForElementToBeVisible(driver, amountPaidText);
        return SeleniumHelper.parsePriceFromText(amountPaidText.getText());
    }

    public double amountDue() {
        SeleniumHelper.waitForElementToBeVisible(driver, amountDueText);
        return SeleniumHelper.parsePriceFromText(amountDueText.getText());
    }

    public WebElement primaryGuestDetailsVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, primaryGuestDetailsHeading);
        return primaryGuestDetailsHeading;
    }

    /**
     * Verifies the read-only confirmation: booking id is shown, amounts parse
     * to non-negative numbers and the primary guest section is visible.
     */
    public void verifyConfirmationReadOnly() {
        String idText = bookingIdText();
        if (idText == null || idText.trim().isEmpty()) {
            throw new RuntimeException("Booking ID text is missing on the booking detail page");
        }
        double paid = amountPaid();
        double due = amountDue();
        if (paid < 0 || due < 0) {
            throw new RuntimeException("Amounts could not be parsed on the booking detail page");
        }
        primaryGuestDetailsVisible();
    }
}
