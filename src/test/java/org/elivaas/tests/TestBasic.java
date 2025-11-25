package org.elivaas.tests;

import org.apache.commons.io.FileUtils;
import org.elivaas.utils.BrowserManager;
import org.elivaas.utils.PropertiesLoader;
import org.elivaas.utils.TakeScreenShot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBasic {
    private static final ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();
    public static WebDriver getDriver() {
        return tdriver.get();
    }

    @BeforeMethod
    public void setup() throws IOException {

        String url = PropertiesLoader.loadProperty("url");
        WebDriver driver = BrowserManager.doBrowserSetup(false);
        tdriver.set(driver);
        getDriver().manage().window().maximize();
        getDriver().get(url);
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakeScreenShot.click(getDriver());
        }
        
        if (getDriver() != null) {
            getDriver().quit();
            tdriver.remove();
        }
    }

//    public static void captureScreenshot(String testName) {
//        try {
//            File screenshotFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
//            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String screenshotName = testName + "_" + timestamp + ".png";
//            File targetFile = new File(SCREENSHOT_PATH + screenshotName);
//            FileUtils.copyFile(screenshotFile, targetFile);
//
//            // Attach screenshot to TestNG report
//            System.out.println("Screenshot saved: " + targetFile.getAbsolutePath());
//        } catch (Exception e) {
//            System.out.println("Failed to capture screenshot: " + e.getMessage());
//        }
//    }
}
