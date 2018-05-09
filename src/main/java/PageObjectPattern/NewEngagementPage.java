package PageObjectPattern;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewEngagementPage extends Page {
    public NewEngagementPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@event='Save']")
    public WebElement saveButton;

    private WebElement getPopUpInput(String elementName) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-field='Engagement." + elementName + "']//input")));
    }

    private WebElement getPopUpDiv(String elementName) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-field='Engagement." + elementName + "']")));
    }

    private WebElement getFoundItem(String itemName) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='item']//div[text()='" + itemName + "']")));
    }

    public void sendNewEngagementName(String engagementName) {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-field='Engagement.Name']//input")))
                .sendKeys(engagementName);
    }

    public void setTypeOfNewEngagement(String engagementType) {
        getPopUpDiv("Type").click();
        getPopUpInput("Type").sendKeys(engagementType);
        try {
            getFoundItem(engagementType).click();
        } catch (StaleElementReferenceException e) {
            clickStaleElementBy(By.xpath("//div[@class='item']//div[text()='" + engagementType + "']"));
        }
    }


    public void setParentPartnerToNewEngagement(String parentEngagement) {
        getPopUpDiv("TreeParent").click();
        getPopUpInput("TreeParent").sendKeys(parentEngagement);
        try {
            getFoundItem(parentEngagement).click();
        } catch (StaleElementReferenceException e) {
            clickStaleElementBy(By.xpath("//div[@class='item']//div[text()='" + parentEngagement + "']"));
        }
    }

    public void setBlaToNewEngagement(String bla) {
        getPopUpDiv("BusinessLineArea").click();
        getPopUpInput("BusinessLineArea").sendKeys(bla);
        try {
            getFoundItem(bla).click();
        } catch (StaleElementReferenceException e) {
            clickStaleElementBy(By.xpath("//div[@class='item']//div[text()='" + bla + "']"));
        }
    }

    public void setManagementsUnitToNewEngagement(String managementUnit) {
        getPopUpDiv("CoLocation").click();
        getPopUpInput("CoLocation").sendKeys(managementUnit);
        try {
            getFoundItem(managementUnit).click();
        } catch (StaleElementReferenceException e) {
            clickStaleElementBy(By.xpath("//div[@class='item']//div[text()='" + managementUnit + "']"));
        }
    }

    public void setManagerToNewEngagement(String manager) {
        getPopUpDiv("Managers").click();
        getPopUpInput("Managers").sendKeys(manager);
        try {
            getFoundItem(manager).click();
        } catch (StaleElementReferenceException e) {
            clickStaleElementBy(By.xpath("//div[@class='item']//div[text()='" + manager + "']"));
        }
        driver.findElement(By.xpath("//div[@class='apply']/div")).click();
    }
}
