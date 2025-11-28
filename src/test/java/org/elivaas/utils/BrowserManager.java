package org.elivaas.utils;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class BrowserManager {


    public static WebDriver doBrowserSetup(boolean headless) throws IOException {
        String name=PropertiesLoader.loadProperty("browser.name");

        if(name.equalsIgnoreCase("Chrome")){
            throw new UnsupportedOperationException("only chrome supported");

        }
        ChromeOptions option=new ChromeOptions();
        option.addArguments("--headless");
        //option.addArguments("--disable-blink-features=AutomationControlled");
        //option.addArguments("profile-directory=Default");
        //option.addArguments("\"excludeSwitches\", [\"enable-automation\"]");
        //option.addArguments("\"useAutomationExtension\", False");
        option.addArguments("profile-directory=Profile 1");



        if(headless){
            return new ChromeDriver(option);
        }else {
            return new ChromeDriver();
        }

    }
}
