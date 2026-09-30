package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

/**
 * STUB for the Juspay payment page
 * (payments.juspay.in/payment-page/order/ordv2_&lt;order-id&gt;).
 *
 * READ-ONLY assertions only. This page is intentionally NOT wired into the
 * default test flow: submitting a payment must NEVER happen automatically.
 *
 * Expected page state: "Payable Amount" label, a bank-offer banner, restricted
 * payment options, the "secured by JUSPAY" badge and a disabled
 * "Proceed to Pay" button.
 */
public class PaymentPage {

    WebDriver driver;

    public PaymentPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //write all the locators here

    @FindBy(xpath = "//*[normalize-space()='Payable Amount']")
    private WebElement payableAmountLabel;

    @FindBy(xpath = "//*[contains(normalize-space(),'offer') or contains(normalize-space(),'Offer')]")
    private WebElement bankOfferBanner;

    @FindBy(xpath = "//*[contains(normalize-space(),'JUSPAY')]")
    private WebElement juspayBadge;

    @FindBy(xpath = "//button[contains(normalize-space(),'Proceed to Pay') and @disabled]")
    private WebElement disabledProceedToPayButton;

    //write all methods here

    /**
     * Reads the payable amount shown next to the "Payable Amount" label.
     * Read-only — never enters card details, never submits.
     */
    public double payableAmount() {
        SeleniumHelper.waitForElementToBeVisible(driver, payableAmountLabel);
        WebElement row = driver.findElement(
                By.xpath("//*[normalize-space()='Payable Amount']/parent::*"));
        return SeleniumHelper.parsePriceFromText(row.getText());
    }

    /**
     * Safe, read-only verification of the payment page.
     * Asserts the payable amount is present and positive, the bank-offer
     * banner and "secured by JUSPAY" badge are visible, and the
     * "Proceed to Pay" button is DISABLED. Nothing is entered, nothing
     * is submitted.
     */
    public void assertSafeReadOnlyState() {
        SeleniumHelper.waitForElementToBeVisible(driver, payableAmountLabel);
        Assert.assertTrue(payableAmountLabel.isDisplayed(), "Payable Amount label should be visible");
        Assert.assertTrue(bankOfferBanner.isDisplayed(), "Bank-offer banner should be visible");
        Assert.assertTrue(juspayBadge.isDisplayed(), "secured by JUSPAY badge should be visible");
        Assert.assertTrue(disabledProceedToPayButton.isDisplayed(),
                "Proceed to Pay button must stay disabled — real payment must never be submitted automatically");
        Assert.assertFalse(disabledProceedToPayButton.isEnabled(),
                "Proceed to Pay button must be disabled");
    }
}
