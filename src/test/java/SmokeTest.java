import java.util.Random;
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

    private final String email = "test_account713713@yahoo.com";
    private final String password = "test_password";
    private final String to = "bogdanprostov@gmail.com";
    private final String subject = "Test Subject " + new Random().nextInt(100);
    private final String body = "This is a test email body.";

    @BeforeClass
    public void setUp() {
        logger.info("Initializing WebDriver and setting up pages for Yahoo Mail.");
        driver = DriverFactory.getDriver("chrome");
        driver.manage().window().maximize();
        driver.get("https://login.yahoo.com/?.src=ym&pspid=1197806870&activity=header-signin&.lang=en-US&.intl=us&.done=https%3A%2F%2Fmail.yahoo.com%2Fd");
        loginPage = new LoginPage(driver);
        mailPage = new MailPage(driver);
    }

    @Test(priority = 1)
    public void testLogin() {
        logger.info("Executing login test for Yahoo Mail.");
        loginPage.login(email, password);
        Assert.assertTrue(mailPage.isLoginSuccessful(), "Login was unsuccessful.");
        logger.info("Login test passed.");
    }

    @Test(priority = 2)
    public void testCreateDraft() {
        logger.info("Executing draft creation test.");
        mailPage.createDraft(to, subject, body);
        Assert.assertTrue(mailPage.isDraftPresent(subject), "Draft not found in the 'Drafts' folder.");
        logger.info("Draft creation test passed.");
    }

    @Test(priority = 3)
    public void testVerifyDraftContent() {
        logger.info("Verifying draft content.");
        Assert.assertTrue(mailPage.isDraftPresent(subject), "Draft with the specified subject does not exist.");

        mailPage.openDraft(subject);
        Assert.assertEquals(mailPage.getDraftTo(1), to, "Recipient in draft does not match.");
        Assert.assertEquals(mailPage.getDraftSubject(), subject, "Subject in draft does not match.");
        Assert.assertEquals(mailPage.getDraftBody(), body, "Body in draft does not match.");
        logger.info("Draft content verification test passed.");
    }

    @Test(priority = 4)
    public void testSendMail() {
        logger.info("Executing mail sending test.");
        mailPage.sendDraft();
        Assert.assertFalse(mailPage.isDraftPresent(subject), "Draft still present in the 'Drafts' folder.");
        Assert.assertTrue(mailPage.isMailInSentFolder(subject), "Mail not found in the 'Sent' folder.");
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