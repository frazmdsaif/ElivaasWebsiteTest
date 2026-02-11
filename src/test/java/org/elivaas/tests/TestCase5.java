package org.elivaas.tests;

import org.elivaas.pages.PropertyDetailPage;
import org.testng.annotations.Test;

public class TestCase5 extends TestBasic{

    @Test(description = "Verify that property details page is visible successfully")
    public void verifyThatPropertyDetailsPageIsVisible() throws InterruptedException {
        PropertyDetailPage propertyDetailPage=new PropertyDetailPage(getDriver());
        String propertyName=propertyDetailPage.CurrentPropertyName();
        System.out.println(propertyName);

    }
    @Test(description = "verify property price")
    public void verifyPropertyPrice() throws InterruptedException {
        PropertyDetailPage propertyDetailPage=new PropertyDetailPage(getDriver());
        String propertyPrice=propertyDetailPage.CurrentPropertyPrice();
        int price = Integer.parseInt(propertyPrice);
        System.out.println("Price as integer: " + price);
        assert price > 1 : "Price should be greater than 1";
    }
}
