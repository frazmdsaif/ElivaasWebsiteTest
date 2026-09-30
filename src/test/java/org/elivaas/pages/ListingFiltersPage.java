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
 * Search results / listing page (/villas/villas-in-&lt;destination&gt;?checkin=...&checkout=...).
 *
 * Covers the filters panel (Filters heading, Clear All, Price Range sliders,
 * Property Types radios, Bedrooms buttons, Pet Friendly checkbox, By Brand
 * checkboxes, Sort By combobox) and the property cards. Each card is a
 * whole-card link whose accessible name is the property title; clicking a
 * card opens the property detail page in a NEW window/tab.
 */
public class ListingFiltersPage {

    WebDriver driver;

    public ListingFiltersPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //write all the locators here

    @FindBy(xpath = "//*[normalize-space()='Filters']")
    private WebElement filtersHeading;

    @FindBy(xpath = "//button[normalize-space()='Clear All']")
    private WebElement clearAllButton;

    @FindBy(xpath = "//*[normalize-space()='Price Range']")
    private WebElement priceRangeHeading;

    @FindBy(xpath = "//*[normalize-space()='Minimum']")
    private WebElement minimumLabel;

    @FindBy(xpath = "//*[normalize-space()='Maximum']")
    private WebElement maximumLabel;

    @FindBy(xpath = "//*[normalize-space()='Property Types']")
    private WebElement propertyTypesHeading;

    @FindBy(xpath = "//*[normalize-space()='Bedrooms']")
    private WebElement bedroomsHeading;

    @FindBy(xpath = "//label[normalize-space()='Pet Friendly']")
    private WebElement petFriendlyCheckbox;

    @FindBy(xpath = "//*[normalize-space()='By Brand']")
    private WebElement byBrandHeading;

    @FindBy(xpath = "//*[contains(normalize-space(),'Sort By')]")
    private WebElement sortByCombobox;

    @FindBy(xpath = "//a[contains(@href,'/villa-in-')]")
    private List<WebElement> propertyCards;

    //write all methods here

    public WebElement filtersHeadingVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, filtersHeading);
        return filtersHeading;
    }

    public void clearAllFilters() {
        SeleniumHelper.waitForElementToBeClickable(driver, clearAllButton);
        clearAllButton.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    public WebElement priceRangeVisible() {
        SeleniumHelper.waitForElementToBeVisible(driver, priceRangeHeading);
        SeleniumHelper.waitForElementToBeVisible(driver, minimumLabel);
        SeleniumHelper.waitForElementToBeVisible(driver, maximumLabel);
        return priceRangeHeading;
    }

    /**
     * Selects a property type radio ("Apartment" / "Villa").
     */
    public void selectPropertyType(String type) {
        WebElement option = driver.findElement(By.xpath("//label[normalize-space()='" + type + "']"));
        SeleniumHelper.waitForElementToBeClickable(driver, option);
        option.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    /**
     * Selects a bedroom-count button (0-10) in the Bedrooms filter group.
     */
    public void selectBedrooms(int count) {
        WebElement button = driver.findElement(By.xpath(
                "//div[.//*[normalize-space()='Bedrooms']]//button[normalize-space()='" + count + "']"));
        SeleniumHelper.waitForElementToBeClickable(driver, button);
        button.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    public void togglePetFriendly() {
        SeleniumHelper.waitForElementToBeClickable(driver, petFriendlyCheckbox);
        petFriendlyCheckbox.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    /**
     * Selects a brand checkbox under "By Brand" ("Like a 5" / "Like a 4").
     */
    public void selectBrand(String brand) {
        WebElement checkbox = driver.findElement(By.xpath("//label[normalize-space()='" + brand + "']"));
        SeleniumHelper.waitForElementToBeClickable(driver, checkbox);
        checkbox.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    /**
     * Opens the "Sort By" combobox and picks an option, e.g. "Popularity",
     * "Price: Low to High", "Price: High to Low".
     */
    public void sortBy(String option) {
        SeleniumHelper.waitForElementToBeClickable(driver, sortByCombobox);
        sortByCombobox.click();
        WebElement item = driver.findElement(By.xpath("//*[normalize-space()='" + option + "']"));
        SeleniumHelper.waitForElementToBeClickable(driver, item);
        item.click();
        SeleniumHelper.waitFor2Second(driver);
    }

    /**
     * Titles (accessible names) of all visible property cards.
     */
    public List<String> propertyCardTitles() {
        List<String> titles = new ArrayList<>();
        for (WebElement card : propertyCards) {
            String name = card.getAttribute("aria-label");
            if (name == null || name.isEmpty()) {
                name = card.getText();
            }
            titles.add(name);
        }
        return titles;
    }

    public int propertyCardCount() {
        return propertyCards.size();
    }

    /**
     * Clicks the property card at the given index and switches the driver
     * to the newly opened window/tab (property detail page).
     * Returns the card's accessible name (property title).
     */
    public String openPropertyCard(int index) {
        WebElement card = propertyCards.get(index);
        SeleniumHelper.waitForElementToBeClickable(driver, card);
        String title = card.getAttribute("aria-label");
        if (title == null || title.isEmpty()) {
            title = card.getText();
        }
        card.click();
        SeleniumHelper.switchToNewWindow(driver);
        SeleniumHelper.waitFor2Second(driver);
        return title;
    }

    /**
     * Clicks the card whose accessible name contains the given title fragment.
     */
    public void openPropertyCardByTitle(String titleFragment) {
        List<WebElement> cards = driver.findElements(By.xpath("//a[contains(@href,'/villa-in-')]"));
        for (WebElement card : cards) {
            String name = card.getAttribute("aria-label");
            if (name != null && name.contains(titleFragment)) {
                SeleniumHelper.waitForElementToBeClickable(driver, card);
                card.click();
                SeleniumHelper.switchToNewWindow(driver);
                SeleniumHelper.waitFor2Second(driver);
                return;
            }
        }
        throw new RuntimeException("No property card found containing title: " + titleFragment);
    }
}
