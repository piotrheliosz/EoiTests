package PageObjectPattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends Page {
    @FindBy(id = "outerContainer")
    private WebElement outerContainer;
    @FindBy(xpath = "//div[@id='menu-items']//a[text()='Engagements']")
    private WebElement engagementSectionLink;
    @FindBy(xpath = "(//div[@id='mainToolbar']//span[text()='Engagements'])[1]")
    private WebElement engagementTopMenu;
    @FindBy(xpath = "//div[@id='mainToolbar']//span[text()='Engagement Performance']")
    private WebElement engagementPerformanceSubmenu;
    @FindBy(xpath = "(//div[@id='mainToolbar']//span[text()='Engagement Performance']/..//a)[1]")
    private WebElement firstEngagementFromSubmenu;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnEngagementItemFromMenu() {
        new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(engagementSectionLink)).click();
        loadingElement();
    }

    public void navigateToEngagementsSection() {
        waitUntilVisibility(outerContainer, 30);
        if (!driver.getCurrentUrl().contains("engagements-overview")) {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(engagementSectionLink))
                    .click();
        }
    }

    public void goToEngagementPerformancePageByJs(String engagementName) {
        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='mainToolbar']//span[text()='Engagements'])[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                driver.findElement(By.xpath("//*[text()='Engagement Performance']//..//*[contains(text(),'" + engagementName + "')]")));
    }

    public void goToEngagementsPerformanceSection() {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitUntilVisibility(engagementTopMenu, 5)).perform();
        actions.moveToElement(waitUntilVisibility(engagementPerformanceSubmenu, 3)).perform();
        actions.moveToElement(waitUntilVisibility(firstEngagementFromSubmenu, 3)).click().perform();
    }
}
