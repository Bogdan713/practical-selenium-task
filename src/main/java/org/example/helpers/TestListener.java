package org.example.helpers;

import com.epam.reportportal.message.ReportPortalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.driver.DriverFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {}", result.getName());
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String screenshotPath = "screenshots/" + result.getName() + "_" + timestamp + ".png";
                Files.createDirectories(new File("screenshots").toPath());
                Files.copy(screenshot.toPath(), new File(screenshotPath).toPath());
                logger.error("Screenshot saved at {}", screenshotPath);
                String rp_message = "screenshot msg for rp";
                ReportPortalMessage message = new ReportPortalMessage(screenshot, rp_message);
                logger.info(message);
            } catch (IOException e) {
                logger.error("Error while saving screenshot: {}", e.getMessage(), e);
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test started: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: {}", result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: {}", result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test suite started: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test suite finished: {}", context.getName());
    }
}
