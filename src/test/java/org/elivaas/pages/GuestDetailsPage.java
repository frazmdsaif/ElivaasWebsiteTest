package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Booking review page (/booking/prop_&lt;id&gt;?checkin=...&amp;checkout=...&amp;adults=...&amp;children=...).
 *
 * Covers: the breadcrumb (aria-label "breadcrumb", ends in "Review Booking"),
 * the editable trip summary ("Check-In ..." button, "Guests / 1 Adult" text,
 * "Meals / Property Only" text), the "Guest Details" form (salutation combobox
 * defaulting to "Mr", First Name, Last Name, Mobile with +91 prefix, Email,
 * City), "Cancellation Policy" text, "Upgrade Your Stay" add-ons ("Snacks",
 * "Chef on Call", "Hookah", "Floating Breakfast", ...), "House Rules" tabs
 * (Check-In, Villa Rules, Security Deposit, Meals, FAQs), the bank-offer logo
 * strip, discount coupons, "Pay using Elicash", the "Price Details" expander
 * and the "Proceed to Pay ₹&lt;amount&gt;" CTA.
 *
 * IMPORTANT: flows on this page must STOP before payment — never click the
 * final "Proceed to Pay" CTA in automation (it creates a real payment order).
 * Instead assert that the CTA amount matches the price total.
 */
public class GuestDetailsPage {

    WebDriver driver;
    private final PriceDetailsPanel priceDetails;

    public GuestDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.priceDetails = new PriceDetailsPanel(driver);
    }

    //write all the locators here

    @FindBy(xpath = "//nav[@aria-label='breadcrumb']")
    private WebElement breadcrumb;

    @FindBy(xpath = "//nav[@aria-label='breadcrumb']//*[normalize-space()='Review Booking']")
    private WebElement reviewBookingCrumb;

    @FindBy(xpath = "//button[contains(normalize-space(),'Check-In')]")
    private WebElement tripSummaryCheckInButton;

    @FindBy(xpath = "//*[contains(normalize-space(),'Guests') and contains(normalize-space(),'Adult')]")
    private WebElement tripSummaryGuestsText;

    @FindBy(xpath = "//*[contains(normalize-space(),'Meals')]")
    private WebElement tripSummaryMealsText;

    @FindBy(xpath = "//*[normalize-space()='Guest Details']")
    private WebElement guestDetailsHeading;

    @FindBy(xpath = "//select[.//option[normalize-space()='Mr']]")
    private WebElement salutationCombobox;

    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@placeholder='Mobile']")
    private WebElement mobileInput;

    @FindBy(xpath = "//input[@placeholder='Email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@placeholder='City']")
    private WebElement cityInput;

    @FindBy(xpath = "//*[contains(normalize-space(),'Cancellation Policy')]")
    private WebElement cancellationPolicyText;

    @FindBy(xpath = "//*[contains(normalize-space(),'Upgrade Your Stay')]")
    private WebElement upgradeYourStayHeading;

    @FindBy(xpath = "//*[normalize-space()='Bank Offers']")
    private WebElement bankOffersHeading;

    @FindBy(xpath = "//button[contains(normalize-space(),'Proceed to Pay')]")
    private WebElement proceedToPayCta;

    //write all methods here

    public PriceDetailsPanel priceDetails() {
        return priceDetails;
    }

    public String breadcrumbText() {
        SeleniumHelper.waitForElementToBeVisible(driver, breadcrumb);
        return breadcrumb.getText();
    }

    public WebElement reviewBookingCrumbVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, reviewBookingCrumb);
        return reviewBookingCrumb;
    }

    public WebElement tripSummaryCheckInVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, tripSummaryCheckInButton);
        return tripSummaryCheckInButton;
    }

    public String tripSummaryGuestsText() {
        SeleniumHelper.waitForElementToBeVisible(driver, tripSummaryGuestsText);
        return tripSummaryGuestsText.getText();
    }

    public String tripSummaryMealsText() {
        SeleniumHelper.waitForElementToBeVisible(driver, tripSummaryMealsText);
        return tripSummaryMealsText.getText();
    }

    public WebElement guestDetailsHeadingVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, guestDetailsHeading);
        return guestDetailsHeading;
    }

    /**
     * Selects a salutation in the Guest Details combobox (default "Mr").
     */
    public void selectSalutation(String salutation) {
        SeleniumHelper.waitForElementToBeVisible(driver, salutationCombobox);
        new Select(salutationCombobox).selectByVisibleText(salutation);
    }

    /**
     * Fills the Guest Details form. Names are generated/passed by the caller;
     * phone and email should come from config (PropertiesLoader "phone"/"email").
     */
    public void fillGuestDetails(String firstName, String lastName,
                                 String phone, String email, String city) {
        guestDetailsHeadingVisible();
        selectSalutation("Mr");
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        mobileInput.clear();
        mobileInput.sendKeys(phone);
        emailInput.clear();
        emailInput.sendKeys(email);
        cityInput.clear();
        cityInput.sendKeys(city);
    }

    public WebElement cancellationPolicyVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, cancellationPolicyText);
        return cancellationPolicyText;
    }

    public WebElement upgradeYourStayVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, upgradeYourStayHeading);
        return upgradeYourStayHeading;
    }

    /**
     * Clicks an "Upgrade Your Stay" add-on button, e.g. "Snacks",
     * "Chef on Call", "Hookah", "Floating Breakfast".
     */
    public void addUpgrade(String upgradeName) {
        upgradeYourStayVisible();
        WebElement upgrade = driver.findElement(By.xpath("//button[normalize-space()='" + upgradeName + "']"));
        SeleniumHelper.waitForElementToBeClickable(driver, upgrade);
        upgrade.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    /**
     * Opens a "House Rules" tab: "Check-In", "Villa Rules",
     * "Security Deposit", "Meals", "FAQs".
     */
    public void openHouseRuleTab(String tabName) {
        WebElement tab = driver.findElement(By.xpath("//button[normalize-space()='" + tabName + "']"));
        SeleniumHelper.waitForElementToBeClickable(driver, tab);
        tab.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    public WebElement bankOffersVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, bankOffersHeading);
        return bankOffersHeading;
    }

    public WebElement proceedToPayCtaVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, proceedToPayCta);
        return proceedToPayCta;
    }

    /**
     * Parses the ₹ amount embedded in the "Proceed to Pay ₹&lt;amount&gt;" CTA text.
     * The CTA is NEVER clicked in automation — this is read-only verification.
     */
    public double ctaAmount() {
        return SeleniumHelper.parsePriceFromText(proceedToPayCtaVisible().getText());
    }

    /**
     * Visible "Add" buttons for upgrade add-ons.
     */
    public List<WebElement> upgradeAddButtons() {
        return driver.findElements(By.xpath("//button[normalize-space()='Add']"));
    }
}
