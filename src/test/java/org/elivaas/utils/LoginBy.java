package org.elivaas.utils;


import org.elivaas.pages.HomePage;
import org.elivaas.tests.TestBasic;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginBy extends TestBasic {
    public static String getOTP() throws IOException {
        String FindOTP = "";
        try {
            // Step 1: Execute ADB command to fetch SMS inbox
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "adb", "shell", "content", "query", "--uri", "content://sms/inbox"
            );
            Process process = processBuilder.start();

            // Step 2: Read the command output
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Step 3: Extract OTP using regex
            String smsContent = output.toString();
            Pattern otpPattern = Pattern.compile("\\b\\d{6}\\b"); // Matches 6-digit numbers
            Matcher matcher = otpPattern.matcher(smsContent);

            if (matcher.find()) {
                String otp = matcher.group(0);
                //System.out.println("OTP FOUND: " + otp);
                FindOTP = otp;


            } else {
                System.out.println("OTP not found in SMS inbox./make sure devloper option ON and USB debug");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return FindOTP;
    }

    public static void gmail(WebDriver wb) throws InterruptedException {
        WebDriver driver = getDriver();
        driver=wb;

        HomePage homePage = new HomePage(driver);
        homePage.loginSignUpVisible().click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[normalize-space()='Continue with Google']")).click();
        Thread.sleep(2000);
        String original_window = driver.getWindowHandle();
        // switch to  window


        for (String window_handle : driver.getWindowHandles()) {
            if (!window_handle.equals(original_window)) {
                driver.switchTo().window(window_handle);
                break;
            }
        }
        //test.addScreenCaptureFromPath("image1.png").pass(MediaEntityBuilder.createScreenCaptureFromPath("image1.png").build());

        try {
            driver.findElement(By.id("identifierId")).sendKeys("elivaas061@gmail.com");
        } catch (ElementNotInteractableException e) {
            System.out.println("Google login flow is missing" + e.getMessage());
        }
        Thread.sleep(1000);

        driver.findElement(By.xpath("//span[normalize-space()='Next']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='Passwd']")).sendKeys("Saif@123");
        Thread.sleep(500);

        driver.findElement(By.xpath("//span[normalize-space()='Next']")).click();
        Thread.sleep(5000);
        // switch to main window
        driver.switchTo().window(original_window);

    }

}
