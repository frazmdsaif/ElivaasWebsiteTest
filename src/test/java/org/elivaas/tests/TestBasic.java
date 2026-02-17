package org.elivaas.tests;

import org.apache.commons.io.FileUtils;
import org.elivaas.utils.BrowserManager;
import org.elivaas.utils.PropertiesLoader;
import org.elivaas.utils.SlackNotifier;
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
import java.util.regex.Pattern;

public class TestBasic {
    private static final ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return tdriver.get();
    }

    private String cleanFailureMessage(String failureMessage) {
        if (failureMessage == null) return "Unknown error";
        
        // Remove session info section
        String cleaned = failureMessage.replaceAll("\\(Session info:.*?\\)", "");
        
        // Remove build info section  
        cleaned = cleaned.replaceAll("Build info:.*?System info:.*?Driver info:.*?Command:.*?\\)", "");
        
        // Remove capabilities section completely
        cleaned = cleaned.replaceAll("Capabilities:.*?Session ID:.*?\\)", "");
        
        // Remove any remaining Selenium technical details with more specific patterns
        cleaned = cleaned.replaceAll("Command:\\s*\\[.*?\\]", "");
        cleaned = cleaned.replaceAll("fedcm:accounts:.*?\\}", "");
        cleaned = cleaned.replaceAll("goog:chromeOptions:.*?\\}", "");
        cleaned = cleaned.replaceAll("goog:processID:.*?\\}", "");
        cleaned = cleaned.replaceAll("se:cdp:.*?\\}", "");
        cleaned = cleaned.replaceAll("webauthn:extension:.*?\\}", "");
        cleaned = cleaned.replaceAll("setWindowRect:.*?\\}", "");
        cleaned = cleaned.replaceAll("strictFileInteractability:.*?\\}", "");
        cleaned = cleaned.replaceAll("timeouts:.*?\\}", "");
        cleaned = cleaned.replaceAll("unhandledPromptBehavior:.*?\\}", "");
        cleaned = cleaned.replaceAll("Session ID:.*?\\}", "");
        cleaned=cleaned.replaceAll("unhandledPromptBehavior:.*?\\}","");
        
        // Remove any remaining technical patterns
        cleaned = cleaned.replaceAll("\\{[^}]*chrome[^}]*\\}", "");
        cleaned = cleaned.replaceAll("\\{[^}]*localhost[^}]*\\}", "");
        cleaned = cleaned.replaceAll("\\{[^}]*debuggerAddress[^}]*\\}", "");
        
        // Clean up extra whitespace and quotes
        cleaned = cleaned.replace("\n", " ").replace("\"", "'").trim();
        
        // Remove multiple spaces and trailing commas
        cleaned = cleaned.replaceAll("\\s+", " ").replaceAll(",\\s*$", "");
        
        return cleaned;
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
            
            // Send Slack notification for failed test
            String testName = result.getMethod().getMethodName();
            String fullClassName = result.getTestClass().getName();
            String testClass = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            String failureMessage = result.getThrowable().getMessage();
            
            String slackMessage = String.format(
                "🚨 TEST FAILED 🚨\n" +
                "Test Class: %s\n" +
                "Test Method: %s\n" +
                "Failure: %s",
                testClass, testName, failureMessage != null ? cleanFailureMessage(failureMessage) : "Unknown error"
            );
            
            System.out.println("Attempting to send Slack notification...");
            System.out.println("Message: " + slackMessage);
            SlackNotifier.send(slackMessage);
            System.out.println("Slack notification sent successfully");
        } else if (ITestResult.SUCCESS == result.getStatus()) {
            // Optional: Send success notification (uncomment if needed)

            String testName = result.getMethod().getMethodName();
            String fullClassName = result.getTestClass().getName();
            String testClass = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            
            String slackMessage = String.format(
                "✅ *TEST PASSED* ✅\n" +
                "📋 *Test Class:* `%s`\n" +
                "🧪 *Test Method:* `%s`",
                testClass, testName
            );
            
            SlackNotifier.send(slackMessage);

        }

        if (getDriver() != null) {
            getDriver().quit();
            tdriver.remove();
        }
    }
}
