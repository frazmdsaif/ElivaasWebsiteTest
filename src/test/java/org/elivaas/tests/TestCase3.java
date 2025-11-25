package org.elivaas.tests;

import org.elivaas.pages.ListingPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestCase3 extends TestBasic{


    @Test(description = "Verify that listing page is visible")
    public void verifyThatListingPageIsVisible() throws InterruptedException, IOException {
        ListingPage listingPage=new ListingPage(getDriver());
        listingPage.openListingPageViaTest();

    }

    @Test(description = "Verify that villa count is visible")
    public void verifyThatVillaCountIsVisible() throws InterruptedException, IOException {
        ListingPage listingPage=new ListingPage(getDriver());
        listingPage.openListingPageViaTest();
        String villaCount=listingPage.villaCountInCityVisible();
        Assert.assertTrue(Integer.parseInt(villaCount)>0,"Villa count should be greater than 0");
    }
}
