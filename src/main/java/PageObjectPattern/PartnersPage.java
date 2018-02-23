package PageObjectPattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PartnersPage extends Page {
    public PartnersPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[text()='Partner Details']/../../div[@id='New']")
    private WebElement addPartnerDetailsButton;

    public void clickToAddPartnerDetails() {
        waitUntilVisibilityOfElement(addPartnerDetailsButton, 30);
        addPartnerDetailsButton.click();
    }
}
