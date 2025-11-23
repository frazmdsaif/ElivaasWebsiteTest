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
        ChromeOptions opion=new ChromeOptions();
        opion.addArguments("--headless");


        if(headless){
            return new ChromeDriver(opion);
        }else {
            return new ChromeDriver();
        }

    }
}
