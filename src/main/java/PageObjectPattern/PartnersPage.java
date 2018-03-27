package PageObjectPattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PartnersPage extends Page {
    @FindBy(xpath = "//*[text()='Partner Details']/../../div[@id='New']")
    private WebElement addPartnerDetailsButton;

    public PartnersPage(WebDriver driver) {
        super(driver);
    }

    public void clickToAddPartnerDetails() {
        waitUntilVisibility(addPartnerDetailsButton, 30);
        addPartnerDetailsButton.click();
    }
}
