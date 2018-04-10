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

    @FindBy(id = "container")
    private WebElement container;

    @FindBy(xpath = "//div[@event='Next']")
    private WebElement eventNext;

    @FindBy(xpath = "//div[@event='Save']")
    private WebElement eventSave;

    public void clickPartnersTab() {
        new WebDriverWait(driver, 5).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//div[@id='partdiv-" + getPartId(1) + "']//li[text()='Partners']")))
                .click();
    }

    public void clickAddNewPartnerButton() {
        new WebDriverWait(driver, 5).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//div[@id='partdiv-" + getPartId(1) + "']//div[@id='New']")))
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

    public void addNewPartnerFromDropdown(String partnerName) {
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
                    .elementToBeClickable(By.xpath("//tbody[@id='rgridbody-" + getPartId(1) + "']//td[contains(text(),'" + partnerName + "')][1]")))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<String> partnersList() {
        List<String> listOfPartnersNames = new ArrayList<String>();
        waitUntilVisibility(container, 10);
        for (WebElement element : driver.findElements(By.xpath("//tbody[@id='rgridbody-" + getPartId(1) + "']//td[1]"))) {
            listOfPartnersNames.add(element.getAttribute("textContent"));
        }
        return listOfPartnersNames;
    }
}
