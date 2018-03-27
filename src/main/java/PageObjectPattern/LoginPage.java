package PageObjectPattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page {

    @FindBy(id = "loginButton")
    public WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void sendLogin(String login) {
        driver.findElement(By.id("usernameInput")).sendKeys(login);
    }

    public void sendPassword(String password) {
        driver.findElement(By.id("passwordInput")).sendKeys(password);
    }
}
