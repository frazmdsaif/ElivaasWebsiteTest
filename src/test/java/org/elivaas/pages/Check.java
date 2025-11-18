package org.elivaas.pages;

import org.openqa.selenium.WebDriver;
import org.elivaas.utils.BrowserManager;
import org.elivaas.utils.TakeScreenShot;

import java.io.IOException;

public class Check {
    public static void main(String[] args) throws IOException,InterruptedException {
        WebDriver d=BrowserManager.doBrowserSetup(false);

        d.get("https://www.elivaas.com");
        Thread.sleep(2000);
        TakeScreenShot.click(d);
        d.quit();

        //SeleniumHelper.waitForElementToBeClickable(d, SubmitUser.);



    }
}
