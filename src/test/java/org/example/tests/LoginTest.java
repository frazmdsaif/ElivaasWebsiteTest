package org.example.tests;

import org.example.pages.SubmitUser;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class LoginTest extends TestBasic {

    @Test(description = "Maths test")
    public static void createUser() throws IOException  {
        int a=2;
        int b=2;
        Assert.assertEquals(a,b);
    }

}
