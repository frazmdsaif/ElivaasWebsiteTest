package org.elivaas.tests;

import org.elivaas.pages.ListingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class TestCase4 extends TestBasic{

    @Test(description = "Verify that property DETAILS page is visible successfully")
    public void verifyThatPropertyPageIsVisible() throws InterruptedException, IOException {
        ListingPage listingPage=new ListingPage(getDriver());
        listingPage.openListingPageViaTest();
        List<WebElement> cards=getDriver().findElements(By.xpath("//*[@data-slot='card']"));
        String currentWindow=getDriver().getWindowHandle();


        if(!cards.isEmpty()){
            WebElement card=cards.get(0);
            if(card.isDisplayed()){
                card.click();
                Thread.sleep(2000);
            }
        }
        Set<String> totalWindow=getDriver().getWindowHandles();
        for(String window:totalWindow){
            if(!window.equals(currentWindow)){
                getDriver().switchTo().window(window);
                System.out.println(getDriver().getCurrentUrl());
                Thread.sleep(2000);
                getDriver().close();
            }
        }



    }


}
