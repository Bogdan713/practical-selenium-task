package org.example.pages;

import static org.example.helpers.Constants.YAHOO_SUCCESS_URL;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MailPage extends BasePage {

    @FindBy(xpath = "//a[contains(@aria-label, 'Compose')]")
    private WebElement composeButton;

    @FindBy(id = "message-to-field")
    private WebElement toField;

    @FindBy(xpath = "//div[@data-test-id='pill-text']")
    private List<WebElement> recipients;
    @FindBy(css = "input[data-test-id='compose-subject']")
    private WebElement subjectField;

    @FindBy(css = "div[data-test-id='rte']")
    private WebElement bodyField;

    @FindBy(xpath = "//button[@data-test-id='icon-btn-close']")
    private WebElement saveDraftButton;

    @FindBy(xpath = "//div[@data-test-folder-container='Draft']")
    private WebElement draftsFolder;

    @FindBy(xpath = "//div[@data-test-folder-container='Sent']")
    private WebElement sentFolder;

    @FindBy(xpath = "//a[@data-test-id='message-list-item']")
    private List<WebElement> draftEmails;

    @FindBy(xpath = "//button[@data-test-id='compose-send-button']")
    private WebElement sendButton;

    @FindBy(xpath = "//button[@id='ybarAccountMenu']")
    private WebElement accountMenu;

    @FindBy(xpath = "//span[text()='Sign out']")
    private WebElement signOutButton;

    private final WebDriverWait wait;

    public MailPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void createDraft(String to, String subject, String body) {
        composeButton.click();
        wait.until(ExpectedConditions.visibilityOf(toField)).sendKeys(to);
        subjectField.sendKeys(subject);
        bodyField.sendKeys(body);
        saveDraftButton.click();
    }

    public boolean isDraftPresent(String subject) {
        draftsFolder.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(draftEmails));
        wait.until(driver ->
            ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

        return draftEmails.stream().anyMatch(draft -> draft.getText().contains(subject));
    }

    public void openDraft(String subject) {
        draftsFolder.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(draftEmails));
        draftEmails.stream()
                   .filter(draft -> draft.getText().contains(subject))
                   .findFirst()
                   .orElseThrow(() -> new RuntimeException("Draft not found: " + subject))
                   .click();
    }

    public String getDraftTo(int recipientID) {
        return wait.until(ExpectedConditions.visibilityOf(recipients.get(recipientID))).getText();
    }

    public String getDraftSubject() {
        return subjectField.getAttribute("value");
    }

    public String getDraftBody() {
        return bodyField.getText();
    }

    public void sendDraft() {
        sendButton.click();
        wait.until(ExpectedConditions.invisibilityOf(sendButton));
    }

    public boolean isMailInSentFolder(String subject) {
        sentFolder.click();
        List<WebElement> sentEmails = wait.until(ExpectedConditions.visibilityOfAllElements(draftEmails));
        return sentEmails.stream().anyMatch(email -> email.getAttribute("aria-label").contains(subject));
    }

    public void logout() {
        accountMenu.click();
        wait.until(ExpectedConditions.visibilityOf(signOutButton)).click();
    }

    public boolean isLoginSuccessful() {
        return wait.until(ExpectedConditions.urlContains(YAHOO_SUCCESS_URL));
    }
}
