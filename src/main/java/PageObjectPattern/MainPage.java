package PageObjectPattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends Page {
    @FindBy(id = "outerContainer")
    private WebElement outerContainer;
    @FindBy(xpath = "//div[@id='menu-items']//a[text()='Engagements']")
    private WebElement engagementSectionLink;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnEngagementItemFromMenu() {
        new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(engagementSectionLink)).click();
        loadingElement();
    }

    public void navigateToEngagementsSection() {
        waitUntilVisibility(outerContainer, 30);
        if (!driver.getCurrentUrl().contains("engagements-overview")) {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(engagementSectionLink))
                    .click();
        }
    }
}
