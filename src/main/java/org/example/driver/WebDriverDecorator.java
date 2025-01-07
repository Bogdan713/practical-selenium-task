package org.example.driver;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverDecorator implements WebDriver, JavascriptExecutor, TakesScreenshot {
    private final WebDriver driver;
    private static final Logger logger = LogManager.getLogger(WebDriverDecorator.class);

    public WebDriverDecorator(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void get(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        String currentUrl = driver.getCurrentUrl();
        logger.info("Current URL: {}", currentUrl);
        return currentUrl;
    }

    @Override
    public String getTitle() {
        String title = driver.getTitle();
        logger.info("Page title: {}", title);
        return title;
    }

    @Override
    public List<WebElement> findElements(By by) {
        logger.info("Finding elements with locator: {}", by);
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        logger.info("Finding element with locator: {}", by);
        return driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        logger.info("Retrieving page source.");
        return driver.getPageSource();
    }

    @Override
    public void close() {
        logger.info("Closing current browser window.");
        driver.close();
    }

    @Override
    public void quit() {
        logger.info("Quitting the WebDriver session.");
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        Set<String> handles = driver.getWindowHandles();
        logger.info("Window handles: {}", handles);
        return handles;
    }

    @Override
    public String getWindowHandle() {
        String handle = driver.getWindowHandle();
        logger.info("Current window handle: {}", handle);
        return handle;
    }

    @Override
    public TargetLocator switchTo() {
        logger.info("Switching to a different context.");
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        logger.info("Accessing navigation controls.");
        return driver.navigate();
    }

    @Override
    public Options manage() {
        logger.info("Accessing WebDriver options.");
        return driver.manage();
    }

    @Override
    public Object executeScript(String script, Object... args) {
        logger.info("Executing JavaScript: {}", script);
        if (driver instanceof JavascriptExecutor) {
            return ((JavascriptExecutor) driver).executeScript(script, args);
        }
        throw new UnsupportedOperationException("Driver does not support JavaScript execution.");
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        logger.info("Executing asynchronous JavaScript: {}", script);
        if (driver instanceof JavascriptExecutor) {
            return ((JavascriptExecutor) driver).executeAsyncScript(script, args);
        }
        throw new UnsupportedOperationException("Driver does not support asynchronous JavaScript execution.");
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        logger.info("Capturing screenshot.");
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(target);
        }
        throw new UnsupportedOperationException("Driver does not support screenshots.");
    }

    public void logBrowserConsoleLogs() {
        if (driver.manage().logs().getAvailableLogTypes().contains(LogType.BROWSER)) {
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logEntries) {
                logger.info("Console log: [{}] [{}] {}", entry.getLevel(), entry.getTimestamp(), entry.getMessage());
            }
        } else {
            logger.warn("Browser does not support retrieving console logs.");
        }
    }

    public WebDriverWait createWait(long timeoutInSeconds) {
        logger.info("Creating WebDriverWait with timeout of {} seconds.", timeoutInSeconds);
        return new WebDriverWait(this.driver, Duration.ofSeconds(timeoutInSeconds));
    }

    public Actions createActions() {
        logger.info("Creating Actions object.");
        return new Actions(this.driver);
    }
}
