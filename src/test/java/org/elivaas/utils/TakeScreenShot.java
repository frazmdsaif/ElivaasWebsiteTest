package org.elivaas.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;

public class TakeScreenShot {
    public static void click(WebDriver driver) throws IOException {
        TakesScreenshot screenshot=(TakesScreenshot) driver;

        File file =screenshot.getScreenshotAs(OutputType.FILE);
        String filename="src/test/resources/Screenshots/"+Utils.generateCurrentDateAndTime()+".png";
        FileUtils.copyFile(file,new File(filename));
    }
}
