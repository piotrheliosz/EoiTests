package PageObjectPattern;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class EngagementsPerformancePage extends Page {
    public EngagementsPerformancePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@event='Next']")
    private WebElement eventNext;
    @FindBy(xpath = "//div[@event='Save']")
    private WebElement eventSave;
    @FindBy(xpath = "//div[@event='Yes']")
    private WebElement eventYes;
    @FindBy(xpath = "//div[@id='Delete' and not(@style='display: none')]")
    private WebElement deleteButton;

    public void clickPartnersTab() {
        new WebDriverWait(driver, 5).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//div[contains(@id, 'partdiv')]//li[text()='Partners']")))
                .click();
    }

    public void clickAddNewPartnerButton() {
        new WebDriverWait(driver, 5).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//div[contains(@id, 'partdiv')]//div[@id='New']")))
                .click();
    }

    private WebElement getPopUpInput(String elementName) {
        return new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-field='Partner." + elementName + "']//input")));
    }

    private WebElement getPopUpDiv(String elementName) {
        return new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-field='Partner." + elementName + "']")));
    }

    private WebElement getFoundItem(String itemName) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='item-table']//div[text()='" + itemName + "']")));
    }

    public void selectNewPartnerFromDropdown(String partnerName) {
        getPopUpDiv("PartnerRef").click();
        getPopUpInput("PartnerRef").sendKeys(partnerName);
        getFoundItem(partnerName).click();
        eventNext.click();
    }

    public void selectRoleType(String roleType) {
        getPopUpDiv("RoleType").click();
        getPopUpInput("RoleType").sendKeys(roleType);
        try {
            getFoundItem(roleType).click();
        } catch (StaleElementReferenceException e) {
            clickStaleWebElement(getFoundItem(roleType));
        }
    }

    public void saveAddPartner() {
        try {
            eventSave.click();
            loadingElement();
        } catch (UnhandledAlertException e) {
            System.out.println("Alert text: " + driver.switchTo().alert().getText());
            driver.switchTo().alert().accept();
        }
    }

    public boolean partnerIsDisplayedOnGrid(String partnerName) {
        try {
            return new WebDriverWait(driver, 10).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//div[contains(@id, 'rgridbody')]//td[contains(text(),'" + partnerName + "')][1]")))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<WebElement> getPartnersList() {
        try {
            return new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//tbody[@class='rgrid rgridlist']/tr"), 0));
        } catch (TimeoutException e) {
            return new ArrayList<WebElement>();
        }
    }

    public List<String> getPartnersNameList() {
        List<WebElement> partnerList = new WebDriverWait(driver, 3)
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//tbody[@class='rgrid rgridlist']/tr/td[1]"), 0));
        List<String> partnersNames = new ArrayList<String>();
        for (WebElement partner : partnerList) {
            partnersNames.add(partner.getAttribute("textContent"));
        }
        return partnersNames;
    }

    public WebElement findPartnerOnGrid(String partnerName) {
        return new WebDriverWait(driver, 5)
                .until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//tbody[@class='rgrid rgridlist']/tr/td[contains(text(),'" + partnerName + "')]")));
    }

    public void removePartnerFromEngagement() {
        waitUntilVisibility(deleteButton, 10).click();
        waitUntilVisibility(eventYes, 10).click();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@event='Yes']"), 0));
    }
}
