package org.elivaas.tests;

import org.elivaas.pages.BookingPanelPage;
import org.elivaas.pages.GuestDetailsPage;
import org.elivaas.pages.HomeSearchPage;
import org.elivaas.pages.ListingFiltersPage;
import org.elivaas.utils.PropertiesLoader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * End-to-end booking journey: home search -> listing -> property detail
 * (booking panel) -> booking review.
 *
 * The flow STOPS BEFORE payment: it fills the Guest Details form and asserts
 * that the "Proceed to Pay ₹&lt;amount&gt;" CTA amount matches the price total.
 * The final "Proceed to Pay" CTA is NEVER clicked (it would create a real
 * payment order).
 */
public class EndToEndBookingFlowTest extends TestBasic {

    @Test(description = "end to end booking flow from home search to booking review without submitting payment")
    public void endToEndBookingFlowStopsBeforePayment() throws IOException, InterruptedException {
        String city = PropertiesLoader.loadProperty("city");

        // 1. HOME: search with dynamic dates (today+7 / today+9)
        HomeSearchPage home = new HomeSearchPage(getDriver());
        home.searchCityWithDynamicDates(city);
        String listingUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(listingUrl.contains("/villas/villas-in-"),
                "Search should land on a destination listing page, got: " + listingUrl);

        // 2. LISTING: open the first property card (opens a new window/tab)
        ListingFiltersPage listing = new ListingFiltersPage(getDriver());
        Assert.assertTrue(listing.propertyCardCount() > 0, "Listing should show at least one property card");
        String propertyTitle = listing.openPropertyCard(0);
        System.out.println("Opened property card: " + propertyTitle);
        String detailUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(detailUrl.contains("/villa-in-"),
                "Card click should open a property detail page, got: " + detailUrl);

        // 3. PROPERTY DETAIL: edit guests + meal plan, verify price panel parses
        BookingPanelPage panel = new BookingPanelPage(getDriver());
        panel.setAdults(2);
        panel.selectMealPlan("Property Only");
        panel.priceDetails().expand();
        double basePrice = panel.priceDetails().basePrice();
        double total = panel.priceDetails().totalAmount();
        System.out.println("Property detail price — base: " + basePrice + ", total: " + total);
        Assert.assertTrue(basePrice > 0, "Base price should parse to a positive amount");
        Assert.assertTrue(total > 0, "Total amount should parse to a positive amount");

        // proceed to the booking review page
        panel.clickProceedToPay();
        String reviewUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(reviewUrl.contains("/booking/"),
                "Proceed to Pay should land on the booking review page, got: " + reviewUrl);

        // 4. BOOKING REVIEW: fill guest details, verify totals, stop before payment
        GuestDetailsPage review = new GuestDetailsPage(getDriver());
        Assert.assertTrue(review.breadcrumbText().contains("Review Booking"),
                "Breadcrumb should end in 'Review Booking'");
        review.fillGuestDetails(
                "Elivaas",
                "Tester",
                PropertiesLoader.loadProperty("phone"),
                PropertiesLoader.loadProperty("email"),
                city);
        review.priceDetails().expand();
        double reviewTotal = review.priceDetails().totalAmount();
        double ctaAmount = review.ctaAmount();
        System.out.println("Review page — price total: " + reviewTotal + ", CTA amount: " + ctaAmount);
        Assert.assertTrue(reviewTotal > 0, "Review page total should parse to a positive amount");
        Assert.assertEquals(ctaAmount, reviewTotal, 1.0,
                "Proceed-to-Pay CTA amount must match the price total");

        // STOP: the final "Proceed to Pay" CTA is intentionally NOT clicked —
        // clicking it would create a real payment order.
    }
}
