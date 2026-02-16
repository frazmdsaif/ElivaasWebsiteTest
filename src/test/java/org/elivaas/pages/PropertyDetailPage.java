package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    @FindBy(xpath = "//button[text()='Proceed to Pay']")
    WebElement proceed_to_pay;

    @FindBy(xpath = "//*[text()='Discount Coupon']")
    WebElement discount_text;

    public String get_text_of_discount(){
        SeleniumHelper.waitForElementToBeVisible(driver,discount_text);
        return discount_text.getText();
    }

    public WebElement click_on_proceed_to_pay(){
        SeleniumHelper.waitForElementToBeVisible(driver,proceed_to_pay);
        return proceed_to_pay;
    }

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
        dragPage(300);
        SeleniumHelper.waitForElementToBeVisible(driver,checkin);
        return checkin;
    }

    public WebElement clickOnCheckout(){
        InternalCurrentPropertyPrice();
        SeleniumHelper.waitForElementToBeVisible(driver,checkout);
        return checkout;
    }

    public void dragPage(int pixel){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, " + pixel + ");");
    }


    public void dateSelection(){
        WebElement date=clickOnCheckin();
        date.click();
        WebElement check_in=driver.findElement(By.xpath("//button[@aria-label='Tuesday, March 10th, 2026']"));
        SeleniumHelper.waitForElementToBeVisible(driver,check_in);
        check_in.click();
        WebElement check_out=driver.findElement(By.xpath("//button[@aria-label='Wednesday, March 11th, 2026']"));
        SeleniumHelper.waitForElementToBeVisible(driver,check_out);
        check_out.click();

    }


}
