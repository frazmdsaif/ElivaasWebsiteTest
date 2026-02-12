package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Set;

public class PropertyDetailPage {
    WebDriver driver;
    public PropertyDetailPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }
    @FindBy(xpath = "//*[@class='ml-auto pr-1']")
    WebElement sumbitonHome;

    @FindBy(xpath = "//div[contains(@class,'grid gap-6 px-3 sm:px-2 md:px-8 lg:grid-cols-2 xl:grid-cols-3 sm:pb-10 lg:px-0 xl:px-0 grid-cols-1 sm:grid-cols-2')]/div[1]")
    WebElement firstCard;

    @FindBy(xpath = "//span[normalize-space()='Check-In']")
    WebElement checkin;

    @FindBy(xpath = "//span[normalize-space()='Check-Out']")
    WebElement checkout;

    public WebElement clickOnsumbitOnHome(){
        SeleniumHelper.waitForElementToBeVisible(driver,sumbitonHome);
        return sumbitonHome;


    }
    public String CurrentPropertyName(){
        clickOnsumbitOnHome().click();
        SeleniumHelper.waitForElementToBeVisible(driver,firstCard);
        String fullText = firstCard.getText();
        String[] lines = fullText.split("\n");
        return lines[0].trim();
    }

    public void InternalCurrentPropertyPrice(){
        clickOnsumbitOnHome().click();
        SeleniumHelper.waitForElementToBeVisible(driver,firstCard);
        firstCard.click();
        String currentwindow=driver.getWindowHandle();
        Set<String> totalWindow=driver.getWindowHandles();
        if(totalWindow.size() > 1){
            for(String window : totalWindow){
                if(!window.equals(currentwindow)){
                    driver.switchTo().window(window);
                    break;
                }
            }
        }

    }

    public String CurrentPropertyPrice(){
        InternalCurrentPropertyPrice();
        WebElement priceText=driver.findElement(By.xpath("//div[@class='px-6 space-y-4']//span[@class='text-xl sm:text-2xl font-bold text-primary-950 dark:text-white']"));
        String clean    =priceText.getText().replace("₹", "").replace(",", "").trim();
        return clean;

    }

    public WebElement clickOnCheckin(){
        InternalCurrentPropertyPrice();
        SeleniumHelper.waitForElementToBeVisible(driver,checkin);
        return checkin;
    }

    public WebElement clickOnCheckout(){
        InternalCurrentPropertyPrice();
        SeleniumHelper.waitForElementToBeVisible(driver,checkout);
        return checkout;
    }

    public void dateSelection(){
        //SeleniumHelper.waitForElementToBeVisible(driver,clickOnCheckin());
        WebElement date=clickOnCheckin();
        date.click();
        driver.findElement(By.xpath("//button[@aria-label='Tuesday, March 10th, 2026']")).click();
        driver.findElement(By.xpath("//button[@aria-label='Wednesday, March 11th, 2026']")).click();


        //clickOnCheckout().sendKeys("2026-03-05");

    }


}
