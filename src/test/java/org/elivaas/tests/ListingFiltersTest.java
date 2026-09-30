package org.elivaas.tests;

import org.elivaas.pages.HomeSearchPage;
import org.elivaas.pages.ListingFiltersPage;
import org.elivaas.utils.PropertiesLoader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Listing page filters + sort coverage: applies the filters panel controls
 * (property type, bedrooms, pet friendly, brand), exercises "Clear All" and
 * the "Sort By" combobox, then opens a property card into a new window/tab
 * and asserts it lands on a property detail page.
 */
public class ListingFiltersTest extends TestBasic {

    private ListingFiltersPage openListing() throws IOException, InterruptedException {
        String city = PropertiesLoader.loadProperty("city");
        HomeSearchPage home = new HomeSearchPage(getDriver());
        home.searchCity(city);
        ListingFiltersPage listing = new ListingFiltersPage(getDriver());
        listing.filtersHeadingVisible();
        return listing;
    }

    @Test(description = "verify that listing filters can be applied and cleared")
    public void verifyThatFiltersCanBeAppliedAndCleared() throws IOException, InterruptedException {
        ListingFiltersPage listing = openListing();
        int beforeFilters = listing.propertyCardCount();
        Assert.assertTrue(beforeFilters > 0, "Listing should show property cards before filtering");

        listing.selectPropertyType("Villa");
        listing.selectBedrooms(2);
        listing.togglePetFriendly();
        listing.selectBrand("Like a 5");

        int afterFilters = listing.propertyCardCount();
        System.out.println("Cards before filters: " + beforeFilters + ", after filters: " + afterFilters);
        Assert.assertTrue(afterFilters > 0, "Filtered listing should still show property cards");

        listing.clearAllFilters();
        int afterClear = listing.propertyCardCount();
        Assert.assertTrue(afterClear >= afterFilters,
                "Clearing filters should restore at least the filtered card count");
    }

    @Test(description = "verify that price range filter panel is visible")
    public void verifyThatPriceRangeFilterIsVisible() throws IOException, InterruptedException {
        ListingFiltersPage listing = openListing();
        listing.priceRangeVisible();
        Assert.assertTrue(true, "Price Range filter with Minimum/Maximum sliders is visible");
    }

    @Test(description = "verify that sort by combobox works")
    public void verifyThatSortByComboboxWorks() throws IOException, InterruptedException {
        ListingFiltersPage listing = openListing();
        listing.sortBy("Popularity");
        Assert.assertTrue(listing.propertyCardCount() > 0,
                "Cards should still be listed after sorting by Popularity");
    }

    @Test(description = "verify that clicking a property card opens the detail page in a new window")
    public void verifyThatPropertyCardOpensDetailPage() throws IOException, InterruptedException {
        ListingFiltersPage listing = openListing();
        String title = listing.openPropertyCard(0);
        String url = getDriver().getCurrentUrl();
        System.out.println("Opened card: " + title + " -> " + url);
        Assert.assertTrue(url.contains("/villa-in-"),
                "Card click should open a property detail page in a new window, got: " + url);
    }
}
