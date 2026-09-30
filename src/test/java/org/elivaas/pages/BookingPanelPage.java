package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Booking panel on the property detail page (/villa-in-&lt;destination&gt;/&lt;slug&gt;).
 *
 * Covers: the date selector button ("Check-In ... Check-Out ..."), the guests
 * selector ("Guests / 1 Adult", adults/children stepper), the meals selector
 * ("Meals / Property Only"), "Discount Coupon" ("View All" / "Add Coupon" /
 * "Remove"), "Pay using Elicash", the "Price Details" expander (Base Price,
 * Coupon Discounts, GST, Total Amount — "includes taxes and platform fee"),
 * the "Proceed to Pay" button and the "Privacy Policy" / "T&amp;C" links.
 *
 * Price reads go through the shared {@link PriceDetailsPanel} component.
 */
public class BookingPanelPage {

    WebDriver driver;
    private final PriceDetailsPanel priceDetails;

    public BookingPanelPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.priceDetails = new PriceDetailsPanel(driver);
    }

    //write all the locators here

    @FindBy(xpath = "//button[contains(normalize-space(),'Check-In') and contains(normalize-space(),'Check-Out')]")
    private WebElement dateSelectorButton;

    @FindBy(xpath = "//button[contains(normalize-space(),'Guests')]")
    private WebElement guestsSelectorButton;

    @FindBy(xpath = "//button[contains(normalize-space(),'Meals')]")
    private WebElement mealsSelectorButton;

    @FindBy(xpath = "//*[text()='Discount Coupon']")
    private WebElement discountCouponHeading;

    @FindBy(xpath = "//button[normalize-space()='View All']")
    private WebElement viewAllCouponsButton;

    @FindBy(xpath = "//button[normalize-space()='Add Coupon']")
    private WebElement addCouponButton;

    @FindBy(xpath = "//button[normalize-space()='Remove']")
    private WebElement removeCouponButton;

    @FindBy(xpath = "//button[normalize-space()='Proceed to Pay']")
    private WebElement proceedToPayButton;

    @FindBy(xpath = "//a[normalize-space()='Privacy Policy']")
    private WebElement privacyPolicyLink;

    @FindBy(xpath = "//a[contains(normalize-space(),'T&C')]")
    private WebElement termsLink;

    //write all methods here

    public PriceDetailsPanel priceDetails() {
        return priceDetails;
    }

    public WebElement dateSelectorVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, dateSelectorButton);
        return dateSelectorButton;
    }

    public String dateSelectorText() {
        return dateSelectorVisible().getText();
    }

    /**
     * Re-selects the stay dates using the dynamic calendar picker
     * (never hardcoded aria-label literals).
     */
    public void changeDates(LocalDate checkIn, LocalDate checkOut) {
        dateSelectorVisible().click();
        HomeSearchPage picker = new HomeSearchPage(driver);
        picker.selectDateRange(checkIn, checkOut);
        SeleniumHelper.waitFor2Second(driver);
    }

    public WebElement guestsSelectorVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, guestsSelectorButton);
        return guestsSelectorButton;
    }

    public String guestsSelectorText() {
        return guestsSelectorVisible().getText();
    }

    /**
     * Sets the adult count via the guests stepper ("Guests / 1 Adult").
     * The stepper is assumed to start at 1 adult; pass the TOTAL desired adults.
     */
    public void setAdults(int totalAdults) {
        guestsSelectorVisible().click();
        HomeSearchPage stepper = new HomeSearchPage(driver);
        stepper.increaseAdults(Math.max(0, totalAdults - 1));
    }

    public void addChildren(int children) {
        guestsSelectorVisible().click();
        HomeSearchPage stepper = new HomeSearchPage(driver);
        stepper.increaseChildren(children);
    }

    public WebElement mealsSelectorVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, mealsSelectorButton);
        return mealsSelectorButton;
    }

    public String mealsSelectorText() {
        return mealsSelectorVisible().getText();
    }

    /**
     * Opens the meals selector and picks a plan, e.g. "Property Only",
     * "Breakfast Included".
     */
    public void selectMealPlan(String plan) {
        mealsSelectorVisible().click();
        WebElement option = driver.findElement(By.xpath("//*[normalize-space()='" + plan + "']"));
        SeleniumHelper.waitForElementToBeClickable(driver, option);
        option.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    public WebElement discountCouponVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, discountCouponHeading);
        return discountCouponHeading;
    }

    /**
     * Opens the coupon list ("View All") and applies the first available coupon
     * via its "Add Coupon" button. No-op if no coupon button is visible.
     */
    public void applyFirstAvailableCoupon() {
        SeleniumHelper.waitForElementToBeClickable(driver, viewAllCouponsButton);
        viewAllCouponsButton.click();
        SeleniumHelper.waitFor2Second(driver);
        List<WebElement> addButtons = driver.findElements(By.xpath("//button[normalize-space()='Add Coupon']"));
        if (!addButtons.isEmpty()) {
            SeleniumHelper.waitForElementToBeClickable(driver, addButtons.get(0));
            addButtons.get(0).click();
            SeleniumHelper.waitFor2Second(driver);
        }
    }

    public void removeCoupon() {
        SeleniumHelper.waitForElementToBeClickable(driver, removeCouponButton);
        removeCouponButton.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    public WebElement proceedToPayVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, proceedToPayButton);
        return proceedToPayButton;
    }

    /**
     * Clicks "Proceed to Pay" — on the property detail page this navigates to
     * the booking review page (/booking/prop_&lt;id&gt;), not to payment yet.
     */
    public void clickProceedToPay() throws InterruptedException {
        SeleniumHelper.waitForElementToBeClickable(driver, proceedToPayButton);
        proceedToPayButton.click();
        Thread.sleep(3000);
    }

    public WebElement privacyPolicyVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, privacyPolicyLink);
        return privacyPolicyLink;
    }

    public WebElement termsVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, termsLink);
        return termsLink;
    }
}
