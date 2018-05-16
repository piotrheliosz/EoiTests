package PageObjectPattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
    private WebElement saveButton;

    public void clickSaveButton() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        double time1 = System.nanoTime();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[text()='Add Engagement']")));
        loadingElement();
        double time2 = System.nanoTime();
        System.out.println("Engagement created in: " + (time2 - time1) / 1000000000 + " sec.");
    }

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

    public void setTypeOfNewEngagement(String engagementType) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if (!itemListIsActive())
            wait.until(ExpectedConditions.elementToBeClickable(engagementTypeDiv)).click();
        wait.until(ExpectedConditions.elementToBeClickable(engagementTypeInput)).sendKeys(engagementType);
        wait.until(ExpectedConditions
                .numberOfElementsToBe(By.xpath("//div[@data-field='Engagement.Type']//*[@class='item']/div[2]"), 1));
        clickStaleWebElement(wait.until(ExpectedConditions.elementToBeClickable(engagementTypeItem)));
        wait.until(ExpectedConditions
                .attributeContains(By.xpath("(//div[@data-field='Engagement.Type']//div[@class='column'])[2]"),
                        "textContent", engagementType));
    }

    @FindBy(xpath = "//div[@name='Engagement.TreeParent']")
    private WebElement engagementTreeParentDiv;
    @FindBy(xpath = "//div[@data-field='Engagement.TreeParent']//input")
    private WebElement engagementTreeParentInput;
    @FindBy(xpath = "//div[@data-field='Engagement.TreeParent']//*[@class='item']/div[2]")
    private WebElement engagementTreeParentItem;

    public void setParentPartnerToNewEngagement(String parentEngagement) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if (!itemListIsActive())
            wait.until(ExpectedConditions.elementToBeClickable(engagementTreeParentDiv)).click();
        wait.until(ExpectedConditions.elementToBeClickable(engagementTreeParentInput)).sendKeys(parentEngagement);
        wait.until(ExpectedConditions
                .numberOfElementsToBe(By.xpath("//div[@data-field='Engagement.TreeParent']//*[@class='item']/div[2]"), 1));
        clickStaleWebElement(wait.until(ExpectedConditions.elementToBeClickable(engagementTreeParentItem)));
        wait.until(ExpectedConditions
                .attributeContains(By.xpath("(//div[@data-field='Engagement.TreeParent']//div[@class='column'])[3]"),
                        "textContent", parentEngagement));
    }

    @FindBy(xpath = "//div[@name='Engagement.BusinessLineArea']")
    private WebElement engagementBusinessLineAreaDiv;
    @FindBy(xpath = "//div[@data-field='Engagement.BusinessLineArea']//input")
    private WebElement engagementBusinessLineAreaInput;
    @FindBy(xpath = "//div[@data-field='Engagement.BusinessLineArea']//*[@class='item']/div[2]")
    private WebElement engagementBusinessLineAreaItem;

    public void setBlaToNewEngagement(String bla) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if (!itemListIsActive())
            wait.until(ExpectedConditions.elementToBeClickable(engagementBusinessLineAreaDiv)).click();
        wait.until(ExpectedConditions.elementToBeClickable(engagementBusinessLineAreaInput)).sendKeys(bla);
        wait.until(ExpectedConditions
                .numberOfElementsToBe(By.xpath("//div[@data-field='Engagement.BusinessLineArea']//*[@class='item']/div[2]"), 1));
        clickStaleWebElement(wait.until(ExpectedConditions.elementToBeClickable(engagementBusinessLineAreaItem)));
        wait.until(ExpectedConditions
                .attributeToBe(By.xpath("(//div[@data-field='Engagement.BusinessLineArea']//div[@class='column'])[1]"),
                        "textContent", bla));
    }

    @FindBy(xpath = "//div[@name='Engagement.CoLocation']")
    private WebElement engagementCoLocationDiv;
    @FindBy(xpath = "//div[@data-field='Engagement.CoLocation']//input")
    private WebElement engagementCoLocationInput;
    @FindBy(xpath = "//div[@data-field='Engagement.CoLocation']//*[@class='item']/div[2]")
    private WebElement engagementCoLocationItem;

    public void setManagementsUnitToNewEngagement(String managementUnit) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if (!itemListIsActive())
            wait.until(ExpectedConditions.elementToBeClickable(engagementCoLocationDiv)).click();
        wait.until(ExpectedConditions.elementToBeClickable(engagementCoLocationInput)).sendKeys(managementUnit);
        wait.until(ExpectedConditions
                .numberOfElementsToBe(By.xpath("//div[@data-field='Engagement.CoLocation']//*[@class='item']/div[2]"), 1));
        clickStaleWebElement(wait.until(ExpectedConditions.elementToBeClickable(engagementCoLocationItem)));
        wait.until(ExpectedConditions
                .attributeToBe(By.xpath("(//div[@data-field='Engagement.CoLocation']//div[@class='column'])[1]"),
                        "textContent", managementUnit));
    }

    @FindBy(xpath = "//div[@name='Engagement.Managers']")
    private WebElement engagementManagesDiv;
    @FindBy(xpath = "//div[@data-field='Engagement.Managers']//input")
    private WebElement engagementManagesInput;
    @FindBy(xpath = "//div[@data-field='Engagement.Managers']//*[@class='item']/div[2]")
    private WebElement engagementManagesItem;
    @FindBy(xpath = "//div[@data-field='Engagement.Managers']//*[@class='item checked']")
    private WebElement engagementManagesItemChecked;
    @FindBy(xpath = "//div[@class='apply']/div")
    private WebElement managersApplyButton;

    public void setManagerToNewEngagement(String manager) {
        WebElement itemCountElement = driver.findElement(By.xpath("//span[@data-bind='text: SelectedItemCount']"));
        WebDriverWait wait = new WebDriverWait(driver, 30);
        if (!itemListIsActive())
            wait.until(ExpectedConditions.elementToBeClickable(engagementManagesDiv)).click();
        wait.until(ExpectedConditions.elementToBeClickable(engagementManagesInput)).sendKeys(manager);
        wait.until(ExpectedConditions
                .numberOfElementsToBe(By.xpath("//div[@data-field='Engagement.Managers']//*[@class='item']/div[2]"), 1));
        clickStaleWebElement(wait.until(ExpectedConditions.elementToBeClickable(engagementManagesItem)));
        wait.until(ExpectedConditions.visibilityOf(engagementManagesItemChecked));
        wait.until(ExpectedConditions.attributeToBe(itemCountElement, "textContent", "[1]"));
        wait.until(ExpectedConditions.elementToBeClickable(managersApplyButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(managersApplyButton));
    }

    private boolean itemListIsActive() {
        try {
            return driver.findElement
                    (By.xpath("//*[@class='form-group object-input active' or @class='form-group object-input multi active']"))
                    .isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}

