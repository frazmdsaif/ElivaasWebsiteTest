package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Shared "Price Details" panel component. It appears on both the property
 * detail booking panel and the booking review page with the same rows:
 * Base Price, Coupon Discounts, GST and Total Amount ("includes taxes and
 * platform fee"). Kept as a component so both pages reuse it.
 */
public class PriceDetailsPanel {

    WebDriver driver;

    public PriceDetailsPanel(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[normalize-space()='Price Details']")
    private WebElement priceDetailsExpander;

    @FindBy(xpath = "//*[contains(normalize-space(),'Elicash')]")
    private WebElement elicashOption;

    /**
     * Opens the Price Details expander if it is not already expanded.
     */
    public void expand() {
        SeleniumHelper.waitForElementToBeClickable(driver, priceDetailsExpander);
        priceDetailsExpander.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    /**
     * Reads the amount shown next to a price row label, e.g. "Base Price".
     * The value is expected in the first following element containing ₹.
     */
    public double amountForRow(String rowLabel) {
        WebElement value = driver.findElement(By.xpath(
                "//*[normalize-space()='" + rowLabel + "']/following::*[contains(normalize-space(),'₹')][1]"));
        SeleniumHelper.waitForElementToBeVisible(driver, value);
        return SeleniumHelper.parsePrice(value.getText());
    }

    public double basePrice() {
        return amountForRow("Base Price");
    }

    public double couponDiscounts() {
        List<WebElement> row = driver.findElements(By.xpath("//*[normalize-space()='Coupon Discounts']"));
        if (row.isEmpty()) {
            return 0.0;
        }
        return amountForRow("Coupon Discounts");
    }

    public double gst() {
        return amountForRow("GST");
    }

    public double totalAmount() {
        return amountForRow("Total Amount");
    }

    /**
     * Toggles the "Pay using Elicash" checkbox option.
     */
    public void togglePayUsingElicash() {
        SeleniumHelper.waitForElementToBeClickable(driver, elicashOption);
        elicashOption.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    public WebElement elicashOptionVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, elicashOption);
        return elicashOption;
    }
}
