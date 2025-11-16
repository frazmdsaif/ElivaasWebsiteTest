package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.example.utils.BrowserManager;
import org.example.utils.SeleniumHelper;
import org.example.utils.TakeScreenShot;

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
