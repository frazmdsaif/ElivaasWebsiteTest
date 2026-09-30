package org.elivaas.pages;

import org.elivaas.utils.CalendarHelper;
import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Home page search widget (elivaas.com) — extends {@link SearchFunction} so the
 * existing destination-type-ahead behaviour is reused instead of duplicated.
 *
 * Widget fields (identified by visible text, not ids):
 *  - "City / Villa / Location"  -> "Select destination" (type-ahead)
 *  - "Check-In" / "Check-Out"    -> "Add Date" (opens the date picker)
 *  - "Guests"                    -> "1 Guest" (opens the guest stepper)
 *  - submit                      -> red circular arrow button
 *
 * Dates are selected dynamically (check-in = today+7, check-out = today+9)
 * via the calendar day buttons' aria-labels, e.g. "Tuesday, March 10th, 2026".
 */
public class HomeSearchPage extends SearchFunction {

    public HomeSearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[normalize-space()='Check-In']/following::*[normalize-space()='Add Date'][1]")
    private WebElement checkInAddDate;

    @FindBy(xpath = "//*[normalize-space()='Check-Out']/following::*[normalize-space()='Add Date'][1]")
    private WebElement checkOutAddDate;

    @FindBy(xpath = "//*[normalize-space()='Guests']")
    private WebElement guestsLabel;

    @FindBy(xpath = "//a[contains(@href,'/villas/villas-in-')]")
    private List<WebElement> destinationCards;

    //write all methods here

    /**
     * Selects a destination category tab shown above the destinations grid,
     * e.g. "All", "Beach Retreats", "Urban Getaways".
     */
    public void selectCategoryTab(String tabName) {
        WebElement tab = driver.findElement(By.xpath("//button[normalize-space()='" + tabName + "']"));
        SeleniumHelper.waitForElementToBeClickable(driver, tab);
        tab.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    /**
     * Returns the destination slugs shown as cards, e.g. "goa" for
     * /villas/villas-in-goa. Cards are whole links to /villas/villas-in-<destination>.
     */
    public List<String> visibleDestinationSlugs() {
        List<String> slugs = new ArrayList<>();
        for (WebElement card : destinationCards) {
            String href = card.getAttribute("href");
            if (href != null && href.contains("/villas/villas-in-")) {
                slugs.add(href.substring(href.lastIndexOf("/villas/villas-in-") + "/villas/villas-in-".length()));
            }
        }
        return slugs;
    }

    public WebElement checkInAddDateVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, checkInAddDate);
        return checkInAddDate;
    }

    public WebElement checkOutAddDateVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, checkOutAddDate);
        return checkOutAddDate;
    }

    /**
     * Clicks the calendar day button whose aria-label matches the given date.
     * If the date is not in the currently visible month, steps the calendar
     * forward (bounded loop) before giving up.
     */
    public void selectDateInPicker(LocalDate date) {
        String ariaLabel = CalendarHelper.ariaLabelFor(date);
        String dayButtonXpath = "//button[@aria-label='" + ariaLabel + "']";
        for (int attempt = 0; attempt < 4; attempt++) {
            List<WebElement> matches = driver.findElements(By.xpath(dayButtonXpath));
            if (!matches.isEmpty()) {
                SeleniumHelper.waitForElementToBeClickable(driver, matches.get(0));
                matches.get(0).click();
                return;
            }
            // date is in a later month — advance the calendar one month
            WebElement nextMonth = driver.findElement(
                    By.xpath("//button[contains(translate(@aria-label,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'next')]"));
            nextMonth.click();
            SeleniumHelper.waitFor2Second(driver);
        }
        throw new RuntimeException("Could not find calendar day with aria-label: " + ariaLabel);
    }

    /**
     * Selects a date range in the open date picker, check-in first then check-out.
     */
    public void selectDateRange(LocalDate checkIn, LocalDate checkOut) {
        selectDateInPicker(checkIn);
        selectDateInPicker(checkOut);
    }

    /**
     * Opens the Check-In field and picks a dynamic range: today+7 to today+9.
     */
    public void pickDynamicDates() {
        checkInAddDateVisible().click();
        selectDateRange(CalendarHelper.defaultCheckIn(), CalendarHelper.defaultCheckOut());
    }

    /**
     * Opens the guest stepper ("Guests" field showing "1 Guest").
     */
    public void openGuestStepper() {
        SeleniumHelper.waitForElementToBeClickable(driver, guestsLabel);
        guestsLabel.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    /**
     * Increases the adult count in the open guest stepper the given number of times.
     */
    public void increaseAdults(int times) {
        for (int i = 0; i < times; i++) {
            WebElement increase = driver.findElement(
                    By.xpath("//button[contains(@aria-label,'Increase') and contains(@aria-label,'adult')]"));
            SeleniumHelper.waitForElementToBeClickable(driver, increase);
            increase.click();
        }
    }

    /**
     * Increases the children count in the open guest stepper the given number of times.
     */
    public void increaseChildren(int times) {
        for (int i = 0; i < times; i++) {
            WebElement increase = driver.findElement(
                    By.xpath("//button[contains(@aria-label,'Increase') and contains(@aria-label,'child')]"));
            SeleniumHelper.waitForElementToBeClickable(driver, increase);
            increase.click();
        }
    }

    /**
     * Full home-page search: type city, pick the first suggestion, select
     * dynamic dates (today+7 / today+9) and submit.
     */
    public void searchCityWithDynamicDates(String cityName) throws InterruptedException {
        List<WebElement> result = listOfCityVisible(cityName);
        result.get(0).click();
        pickDynamicDates();
        searchSubmitButton().click();
        Thread.sleep(3000);
    }

    /**
     * Full home-page search without dates (dates stay unset).
     */
    public void searchCity(String cityName) throws InterruptedException {
        List<WebElement> result = listOfCityVisible(cityName);
        result.get(0).click();
        searchSubmitButton().click();
        Thread.sleep(3000);
    }
}
