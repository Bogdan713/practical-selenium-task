package org.example;

import static org.example.helpers.Constants.BODY;
import static org.example.helpers.Constants.EMAIL;
import static org.example.helpers.Constants.PASSWORD;
import static org.example.helpers.Constants.SUBJECT;
import static org.example.helpers.Constants.TO;
import static org.example.helpers.Constants.YAHOO_URL;

import org.example.driver.DriverFactory;
import org.example.pages.LoginPage;
import org.example.pages.MailPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmokeTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;
    private static final Logger logger = LogManager.getLogger(SmokeTest.class);

    @BeforeClass
    public void setUp() {
        logger.info("Initializing WebDriver and setting up pages for Yahoo Mail.");
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(YAHOO_URL);
        loginPage = new LoginPage(driver);
        mailPage = new MailPage(driver);
    }

    @Test(priority = 1)
    public void testLogin() {
        logger.info("Executing login test for Yahoo Mail.");
        loginPage.login(EMAIL, PASSWORD);
        Assert.assertTrue(mailPage.isLoginSuccessful(), "Login was unsuccessful.");
        logger.info("Login test passed.");
    }

    @Test(priority = 2)
    public void testCreateDraft() {
        logger.info("Executing draft creation test.");
        mailPage.createDraft(TO, SUBJECT, BODY);
        Assert.assertTrue(mailPage.isDraftPresent(SUBJECT), "Draft not found in the 'Drafts' folder.");
        logger.info("Draft creation test passed.");
    }

    @Test(priority = 3)
    public void testVerifyDraftContent() {
        logger.info("Verifying draft content.");
        Assert.assertTrue(mailPage.isDraftPresent(SUBJECT), "Draft with the specified subject does not exist.");

        mailPage.openDraft(SUBJECT);
        Assert.assertEquals(mailPage.getDraftTo(1), TO, "Recipient in draft does not match.");
        Assert.assertEquals(mailPage.getDraftSubject(), SUBJECT, "Subject in draft does not match.");
        Assert.assertEquals(mailPage.getDraftBody(), BODY, "Body in draft does not match.");
        logger.info("Draft content verification test passed.");
    }

    @Test(priority = 4)
    public void testSendMail() {
        logger.info("Executing mail sending test.");
        mailPage.sendDraft();
        Assert.assertFalse(mailPage.isDraftPresent(SUBJECT), "Draft still present in the 'Drafts' folder.");
        Assert.assertTrue(mailPage.isMailInSentFolder(SUBJECT), "Mail not found in the 'Sent' folder.");
        logger.info("Mail sending test passed.");
    }

    @Test(priority = 5)
    public void testLogout() {
        logger.info("Executing logout test for Yahoo Mail.");
        mailPage.logout();
        Assert.assertTrue(loginPage.isLogoutSuccessful(), "Logout was unsuccessful.");
        logger.info("Logout test passed.");
    }

    @AfterClass
    public void tearDown() {
        logger.info("Closing WebDriver.");
        DriverFactory.quitDriver();
    }
}