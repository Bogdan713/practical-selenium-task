import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

    @FindBy(id = "login-username")
    private WebElement emailField;

    @FindBy(id = "login-signin")
    private WebElement nextButton;

    @FindBy(id = "login-passwd")
    private WebElement passwordField;

    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(email);
        nextButton.click();
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
        nextButton.click();
    }

    public boolean isLogoutSuccessful() {
        return wait.until(ExpectedConditions.urlToBe("https://www.yahoo.com/"));
    }
}
