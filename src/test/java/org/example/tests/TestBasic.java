package org.example.tests;

import org.example.utils.BrowserManager;
import org.example.utils.PropertiesLoader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.Properties;

public class TestBasic {

    protected static ThreadLocal<WebDriver> tdriver=new ThreadLocal<>();

    public static synchronized WebDriver getDriver(){
        return tdriver.get();
    }

    @BeforeMethod
    public void setup() throws IOException {
        String url= PropertiesLoader.loadProperty("url");
        WebDriver driver= BrowserManager.doBrowserSetup(false);
        tdriver.set(driver);
        getDriver().get(url);

    }

    @AfterMethod
    public void tearDown(){
        getDriver().quit();
        tdriver.remove();
    }
}
