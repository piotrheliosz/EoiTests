package PageObjectPattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends Page {
    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "outerContainer")
    private WebElement outerContainer;

    public String getUserName() {
        return driver.findElement(By.xpath("//h2[contains(@class, 'user-name')]")).getText();
    }

    public void navigateToPartnerSection() {
        waitUntilVisibilityOfElement(outerContainer, 30);
        driver.navigate().to(getCredentials("baseUrl") + "/portal/kicinnoenergy-acc/page/all-partners/");
    }

    public void navigateToEngagementsSection() {
        waitUntilVisibilityOfElement(outerContainer, 30);
        driver.navigate().to(getCredentials("baseUrl") + "/portal/kicinnoenergy-acc/page/engagements-overview/");
    }
}
