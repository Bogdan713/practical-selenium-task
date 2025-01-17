package org.example.test;

import static org.example.helpers.Constants.EMAIL;
import static org.example.helpers.Constants.LOGS_FOLDER;
import static org.example.helpers.Constants.PASSWORD;
import static org.example.helpers.Constants.SCREENSHOTS_FOLDER;
import static org.example.helpers.Constants.YAHOO_MAIL_SUFFIX;
import static org.example.helpers.Constants.YAHOO_URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.driver.DriverFactory;
import org.example.helpers.FileHelper;
import org.example.helpers.TestListener;
import org.example.pages.LoginPage;
import org.example.pages.MailPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected MailPage mailPage;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
        logger.info("Cleaning up screenshots and logs");
        FileHelper.deleteFolder(SCREENSHOTS_FOLDER);
        FileHelper.deleteFolder(LOGS_FOLDER);
        logger.info("Initializing WebDriver and setting up pages for Yahoo Mail.");
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(YAHOO_URL + YAHOO_MAIL_SUFFIX);
        loginPage = new LoginPage(driver);
        logger.info("Logging in");
        mailPage = loginPage.login(EMAIL, PASSWORD);
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Closing WebDriver.");
        DriverFactory.quitDriver();
    }
}
