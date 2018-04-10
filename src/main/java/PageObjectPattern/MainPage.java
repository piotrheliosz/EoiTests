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

    public String getUserName() {
        return driver.findElement(By.xpath("//h2[contains(@class, 'user-name')]")).getText();
    }

    public void navigateToPartnerSection() {
        waitUntilVisibility(outerContainer, 30);
        driver.navigate().to(getCredentials("baseUrl") + "/portal/kicinnoenergy-acc/page/all-partners/");
    }

    public void navigateToEngagementsSection() {
        waitUntilVisibility(outerContainer, 30);
        if (!driver.getCurrentUrl().contains("engagements-overview")) {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(engagementSectionLink))
                    .click();
        }
    }
}
