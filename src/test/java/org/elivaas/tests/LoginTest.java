package org.elivaas.tests;

import org.elivaas.utils.LoginBy;
import org.testng.annotations.Test;

import java.io.IOException;


public class LoginTest extends TestBasic {

    @Test(description = "Verify that login with gmail is working")
    public void verifyLoginWithGmailIsWorking() throws IOException, InterruptedException {
        LoginBy login = new LoginBy();
        login.gmail(getDriver());
    }


}
