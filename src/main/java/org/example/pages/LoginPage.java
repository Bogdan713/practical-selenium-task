package org.example.pages;

import static org.example.helpers.Constants.YAHOO_LOGOUT_URL;
import static org.example.helpers.Constants.YAHOO_SUCCESS_URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(id = "login-username")
    private WebElement emailField;

    @FindBy(id = "login-signin")
    private WebElement nextButton;

    @FindBy(id = "login-passwd")
    private WebElement passwordField;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public MailPage login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(email);
        nextButton.click();
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
        nextButton.click();
        return new MailPage(driver);
    }

    public boolean isLogoutSuccessful() {
        return wait.until(ExpectedConditions.urlToBe(YAHOO_LOGOUT_URL));
    }

    public boolean isLoginSuccessful() {
        return wait.until(ExpectedConditions.urlContains(YAHOO_SUCCESS_URL));
    }
}
