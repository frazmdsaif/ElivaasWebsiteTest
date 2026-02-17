package org.elivaas.pages;

import org.elivaas.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static org.elivaas.tests.TestBasic.getDriver;

public class UserLogin {
    WebDriver driver;

    public UserLogin(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@id='phone']")
    private WebElement phone;

    @FindBy(xpath = "//button[normalize-space()='Continue']")
    private WebElement continueButton;


    public void enterPhoneNumber(String phone){
        SeleniumHelper.waitForElementToBeVisible(this.driver, this.phone);
        this.phone.sendKeys(phone);
    }

    public void clickContinueButton(){
        SeleniumHelper.waitForElementToBeClickable(driver, continueButton);
        try {
            continueButton.click();
        } catch (Exception e) {
            // Fallback to JavaScript click if regular click fails
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", continueButton);
        }
    }

    private String getOtpInputXPath(int index) {
        return String.format("//div[@class='w-full flex items-center justify-center gap-2 md:gap-4 ']/input[%d]", index);
    }

    public void enterOtp(String otp) {
        for(int i = 1; i <= otp.length(); i++) {
            String xpath = getOtpInputXPath(i);
            WebElement otpInput = driver.findElement(By.xpath(xpath));
            SeleniumHelper.waitForElementToBeVisible(driver, otpInput);
            SeleniumHelper.waitForElementToBeClickable(driver, otpInput);
            otpInput.sendKeys(String.valueOf(otp.charAt(i-1)));
        }
    }
}
