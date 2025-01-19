package org.example.cucumber.steps;

import static org.example.helpers.Constants.EMAIL;
import static org.example.helpers.Constants.PASSWORD;
import static org.example.helpers.Constants.YAHOO_MAIL_SUFFIX;
import static org.example.helpers.Constants.YAHOO_URL;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.driver.DriverFactory;
import org.example.pages.LoginPage;
import org.example.pages.MailPage;
import org.openqa.selenium.WebDriver;

public class EmailSteps {
    WebDriver driver;
    LoginPage loginPage;
    MailPage mailPage;

    @Given("the browser is initialized")
    public void theBrowserIsInitialized() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(YAHOO_URL + YAHOO_MAIL_SUFFIX);
        loginPage = new LoginPage(driver);
    }

    @Given("the user is logged into Yahoo Mail")
    public void theUserIsLoggedIntoYahooMail() {
        mailPage = loginPage.login(EMAIL, PASSWORD);
        assertTrue("Login was unsuccessful", loginPage.isLoginSuccessful());
    }

    @When("the user creates a draft email to {string} with subject {string} and body {string}")
    public void theUserCreatesADraftEmail(String receiver, String subject, String body) {
        mailPage.createDraft(receiver, subject, body);
    }

    @Then("the draft email with subject {string} should be present in the Drafts folder")
    public void theDraftEmailShouldBePresent(String subject) {
        assertTrue("Draft not found", mailPage.isDraftPresent(subject));
    }

    @When("the user sends the draft email with subject {string}")
    public void theUserSendsTheDraftEmail(String subject) {
        mailPage.openDraft(subject)
                .sendDraft();
    }

    @Then("the draft email with subject {string} should no longer be present in the Drafts folder")
    public void theDraftEmailShouldNoLongerBePresent(String subject) {
        assertFalse("Draft still present", mailPage.isDraftPresent(subject));
    }

    @Then("the email with subject {string} should be present in the Sent folder")
    public void theEmailShouldBePresentInSentFolder(String subject) {
        assertTrue("Email not found in Sent folder", mailPage.isMailInSentFolder(subject));
    }
}