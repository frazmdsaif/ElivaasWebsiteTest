package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * "My Bookings" page (/my-bookings) — LOGIN-WALLED, so tests using it must
 * log in first (see {@link org.elivaas.utils.LoginBy#getOTP()} — requires a
 * physical ADB-connected device with the configured phone number).
 *
 * Covers the status tabs ("Upcoming Stay", "Completed", "Cancelled") and the
 * booking cards ("Booking ID:", "Booked On:", "Amount Paid", "Amount Due",
 * check-in/out dates, "Pay Now" button).
 */
public class MyBookingsPage {

    WebDriver driver;

    public MyBookingsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //write all the locators here

    @FindBy(xpath = "//button[normalize-space()='Upcoming Stay']")
    private WebElement upcomingStayTab;

    @FindBy(xpath = "//button[normalize-space()='Completed']")
    private WebElement completedTab;

    @FindBy(xpath = "//button[normalize-space()='Cancelled']")
    private WebElement cancelledTab;

    @FindBy(xpath = "//*[contains(normalize-space(),'Booking ID:')]")
    private List<WebElement> bookingIdElements;

    @FindBy(xpath = "//button[normalize-space()='Pay Now']")
    private List<WebElement> payNowButtons;

    //write all methods here

    /**
     * Navigates directly to the My Bookings page (requires a logged-in session).
     */
    public void navigate() {
        driver.get("https://www.elivaas.com/my-bookings");
        SeleniumHelper.waitFor2Second(driver);
    }

    /**
     * Switches to a status tab: "Upcoming Stay", "Completed" or "Cancelled".
     */
    public void openTab(String tabName) {
        WebElement tab = driver.findElement(By.xpath("//button[normalize-space()='" + tabName + "']"));
        SeleniumHelper.waitForElementToBeClickable(driver, tab);
        tab.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    public WebElement upcomingStayTabVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, upcomingStayTab);
        return upcomingStayTab;
    }

    public WebElement completedTabVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, completedTab);
        return completedTab;
    }

    public WebElement cancelledTabVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, cancelledTab);
        return cancelledTab;
    }

    public int bookingCardCount() {
        return bookingIdElements.size();
    }

    /**
     * The "Booking ID: &lt;id&gt;" texts of all visible cards.
     */
    public List<String> bookingIds() {
        List<String> ids = new ArrayList<>();
        for (WebElement element : bookingIdElements) {
            ids.add(element.getText());
        }
        return ids;
    }

    /**
     * True when every visible card shows the core fields: Booking ID,
     * Booked On, Amount Paid and Amount Due.
     */
    public boolean cardsShowCoreFields() {
        List<WebElement> bookedOn = driver.findElements(By.xpath("//*[contains(normalize-space(),'Booked On:')]"));
        List<WebElement> amountPaid = driver.findElements(By.xpath("//*[contains(normalize-space(),'Amount Paid')]"));
        List<WebElement> amountDue = driver.findElements(By.xpath("//*[contains(normalize-space(),'Amount Due')]"));
        int count = bookingCardCount();
        return count > 0
                && bookedOn.size() >= count
                && amountPaid.size() >= count
                && amountDue.size() >= count;
    }

    public int payNowButtonCount() {
        return payNowButtons.size();
    }

    /**
     * Opens the booking detail page for the first listed booking card.
     * Reads the booking id from the card text ("Booking ID: &lt;id&gt;").
     */
    public String openFirstBookingDetail() {
        String cardText = bookingIdElements.get(0).getText();
        String bookingId = cardText.replace("Booking ID:", "").trim();
        driver.get("https://www.elivaas.com/booking-details/" + bookingId);
        SeleniumHelper.waitFor2Second(driver);
        return bookingId;
    }
}
