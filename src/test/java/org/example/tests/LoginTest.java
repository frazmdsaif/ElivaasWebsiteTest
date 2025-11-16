package org.example.tests;

import org.example.pages.SubmitUser;
import org.testng.annotations.Test;

import java.io.IOException;


public class LoginTest extends TestBasic {

    @Test(description = "Verify that Homepage is Visible successfully")
    public static void createUser() throws IOException  {
//        WebDriver driver = new ChromeDriver();
//        driver.get("http://www.rahulshettyacademy.com/client");
//
        SubmitUser user = new SubmitUser(getDriver());
        user.createFinalUser("John", "Doe", "john.doe@example.com", "9876543210","Saif@123");
//
//        driver.quit();





    }

}
