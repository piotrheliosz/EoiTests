package PageObjectPattern;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewEngagementPage extends Page {
    public NewEngagementPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@event='Save']")
    private WebElement saveButton;

    public void sendNewEngagementName(String engagementName) {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-field='Engagement.Name']//input")))
                .sendKeys(engagementName);
    }

    @FindBy(xpath = "//div[@name='Engagement.Type']")
    private WebElement engagementTypeDiv;
    @FindBy(xpath = "//div[@data-field='Engagement.Type']//input")
    private WebElement engagementTypeInput;
    @FindBy(xpath = "//div[@data-field='Engagement.Type']//*[@class='item']/div[2]")
    private WebElement engagementTypeItem;
    @FindBy(xpath = "(//div[@data-field='Engagement.Type']//*[@class='item']/div[2])[1]")
    private WebElement engagementTypeFirstItem;
    public void setTypeOfNewEngagement(String engagementType) {
        if (!listContainerIsDisplayed())
            waitUntilVisibility(engagementTypeDiv, 30).click();
        clickStaleWebElement(waitUntilVisibility(engagementTypeFirstItem, 15));
    }

    @FindBy(xpath = "//div[@name='Engagement.TreeParent']")
    private WebElement engagementTreeParentDiv;

    @FindBy(xpath = "//div[@data-field='Engagement.TreeParent']//input")
    private WebElement engagementTreeParentInput;
    @FindBy(xpath = "//div[@data-field='Engagement.TreeParent']//*[@class='item']/div[2]")
    private WebElement engagementTreeParentItem;
    @FindBy(xpath = "(//div[@data-field='Engagement.TreeParent']//*[@class='item']/div[2])[1]")
    private WebElement engagementTreeParentFirstItem;
    public void setParentPartnerToNewEngagement(String parentEngagement) {
        waitUntilVisibility(engagementTreeParentDiv, 30).click();
        if (!listContainerIsDisplayed())
            clickStaleWebElement(waitUntilVisibility(engagementTreeParentFirstItem, 15));
    }

    @FindBy(xpath = "//div[@name='Engagement.BusinessLineArea']")
    private WebElement engagementBusinessLineAreaDiv;

    @FindBy(xpath = "//div[@data-field='Engagement.BusinessLineArea']//input")
    private WebElement engagementBusinessLineAreaInput;
    @FindBy(xpath = "//div[@data-field='Engagement.BusinessLineArea']//*[@class='item']/div[2]")
    private WebElement engagementBusinessLineAreaItem;
    @FindBy(xpath = "(//div[@data-field='Engagement.BusinessLineArea']//*[@class='item']/div[2])[1]")
    private WebElement engagementBusinessLineAreaFirstItem;
    public void setBlaToNewEngagement(String bla) {
        if (!listContainerIsDisplayed())
            waitUntilVisibility(engagementBusinessLineAreaDiv, 30).click();
        clickStaleWebElement(waitUntilVisibility(engagementBusinessLineAreaFirstItem, 15));
    }

    @FindBy(xpath = "//div[@name='Engagement.CoLocation']")
    private WebElement engagementCoLocationDiv;

    @FindBy(xpath = "//div[@data-field='Engagement.CoLocation']//input")
    private WebElement engagementCoLocationInput;
    @FindBy(xpath = "//div[@data-field='Engagement.CoLocation']//*[@class='item']/div[2]")
    private WebElement engagementCoLocationItem;
    @FindBy(xpath = "(//div[@data-field='Engagement.CoLocation']//*[@class='item']/div[2])[1]")
    private WebElement engagementCoLocationFirstItem;
    public void setManagementsUnitToNewEngagement(String managementUnit) {
        if (!listContainerIsDisplayed())
            waitUntilVisibility(engagementCoLocationDiv, 30).click();
        clickStaleWebElement(waitUntilVisibility(engagementCoLocationFirstItem, 15));
    }

    @FindBy(xpath = "//div[@name='Engagement.Managers']")
    private WebElement engagementManagesDiv;

    @FindBy(xpath = "//div[@data-field='Engagement.Managers']//input")
    private WebElement engagementManagesInput;
    @FindBy(xpath = "//div[@data-field='Engagement.Managers']//*[@class='item']/div[2]")
    private WebElement engagementManagesItem;
    @FindBy(xpath = "(//div[@data-field='Engagement.Managers']//*[@class='item']/div[2])[1]")
    private WebElement engagementManagesFirstItem;
    @FindBy(xpath = "//div[@data-field='Engagement.Managers']//*[@class='item checked']")
    private WebElement engagementManagesItemChecked;
    @FindBy(xpath = "//div[@class='apply']/div")
    private WebElement managersApplyButton;

    public void setManagerToNewEngagement(String manager) {
        if (!listContainerIsDisplayed())
            waitUntilVisibility(engagementManagesDiv, 30).click();
        waitUntilVisibility(engagementManagesFirstItem, 15).click();
        try {
            waitUntilVisibility(engagementManagesItemChecked, 3);
        } catch (TimeoutException e) {
            engagementManagesFirstItem.click();
        }
        managersApplyButton.click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.invisibilityOf(engagementManagesItemChecked));
    }

    public void clickSaveButton() {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        if (driver.toString().contains("EdgeDriver")) {
            try {
                wait.until(ExpectedConditions.invisibilityOf(saveButton));
            } catch (TimeoutException e) {
                saveButton.click();
            }
        }
        loadingElement();
    }

    private boolean listContainerIsDisplayed() {
        try {
            return driver.findElement(By.xpath("//*[@class='listContainer' and @style!='display: none;']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}

