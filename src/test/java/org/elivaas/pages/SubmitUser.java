package org.elivaas.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class SubmitUser {

    private WebDriver driver;


    public SubmitUser(WebDriver driver){
        this.driver=driver;
        driver.manage().window().maximize();
        PageFactory.initElements(driver,this);
    }


    @FindBy(xpath = "//a[normalize-space()='Register here']")
    WebElement registerButton;

    public WebElement clickOnRegisterButton(){
        return registerButton;
    }

    @FindBy(xpath = "//input[@id='firstName']")
    WebElement firstName;

    @FindBy(xpath = "//input[@id='lastName']")
    WebElement lastName;

    @FindBy(xpath = "//input[@id='userEmail']")
    WebElement userEmail;

    @FindBy(xpath = "//input[@id='userMobile']")
    WebElement userMobile;

    @FindBy(xpath = "//select[@class='custom-select ng-untouched ng-pristine ng-valid']")
    private WebElement selectDropdownElement;

    public void setSelectDropdownElement(int index){
        Select dropdown=new Select(selectDropdownElement);
        dropdown.selectByIndex(index);
    }

    @FindBy(xpath = "//input[@value='Male']")
    WebElement genderSelection;

    @FindBy(xpath = "//input[@id='userPassword']")
    WebElement setPassword;

    @FindBy(xpath = "//input[@id='confirmPassword']")
    WebElement confirmPassword;

    @FindBy(xpath = "//input[@type='checkbox']")
    WebElement checkBox;

    @FindBy(xpath = "//input[@id='login']")
    WebElement submit;

    public void createFinalUser(String Lname,String Fname,String email,String phone,String password) {
        registerButton.click();
        firstName.sendKeys(Fname);
        lastName.sendKeys(Lname);
        userEmail.sendKeys(email);
        userMobile.sendKeys(phone);
        setSelectDropdownElement(2);
        genderSelection.click();
        setPassword.sendKeys(password);
        confirmPassword.sendKeys(password);
        checkBox.click();
        submit.click();
    }

}
