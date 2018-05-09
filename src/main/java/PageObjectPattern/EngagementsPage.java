package PageObjectPattern;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EngagementsPage extends Page {
    @FindBy(xpath = "//input[contains(@id, 'EngagementID')]")
    public WebElement engagementIdFilter;
    @FindBy(xpath = "//input[contains(@id, 'Name')]")
    private WebElement engagementNameFilter;
    @FindBy(xpath = "//li[text()='Partners']")
    private WebElement partnersTab;
    @FindBy(xpath = "(//div[contains(@id, 'partTitle')]//div[@id='Edit'])[1]")
    private WebElement editEngagementButton;
    @FindBy(xpath = "(//div[contains(@id, 'partTitle')]//div[@id='Edit'])[2]")
    private WebElement editEngagementDetailsButton;
    @FindBy(xpath = "//div[@data-field='Engagement.Scope']//textarea")
    private WebElement scopeTextarea;
    @FindBy(xpath = "//div[@data-field='Engagement.Objective']//textarea")
    private WebElement objectiveTextarea;
    @FindBy(xpath = "//div[@name='Engagement.ThematicField']//div[@class='item']/div[1]")
    private WebElement thematicField;
    @FindBy(xpath = "//i[@class='icon-close color-dark-hover']")
    private WebElement closePopUp;
    @FindBy(xpath = "//li[text()='Work Packages / Sections']")
    public WebElement wpTab;
    @FindBy(xpath = "//div[@class='container-fluid']")
    private WebElement fluidContainer;
    @FindBy(className = "overlay")
    private WebElement overlayDiv;
    @FindBy(xpath = "//div[@class='container-fluid']//textarea[@placeholder='Description']")
    private WebElement wpDescTextArea;
    @FindBy(xpath = "//*[text()='View']")
    private WebElement viewButton;
    @FindBy(xpath = "//div[@data-field='Engagement.Managers']//span[@class='input-group-addon icon text-main']")
    private WebElement addManagerButton;
    @FindBy(xpath = "//div[@data-field='Engagement.Managers']//input")
    private WebElement addManagerInput;
    @FindBy(xpath = "//div[@event='Save' or @event='Done']")
    private WebElement saveEvent;
    @FindBy(xpath = "(//div[@id='mainToolbar']//span[text()='Engagements'])[1]")
    private WebElement engagementTopMenu;
    @FindBy(xpath = "//div[@id='mainToolbar']//span[text()='Engagement Performance']")
    private WebElement engagementPerformanceSubmenu;
    @FindBy(xpath = "(//div[@id='mainToolbar']//span[text()='Engagement Performance']/..//a)[1]")
    private WebElement firstEngagementFromSubmenu;

    public EngagementsPage(WebDriver driver) {
        super(driver);
    }

    public void searchForEngagementOnGridById(String engagementId) throws InterruptedException, IOException {
        waitUntilVisibility(engagementIdFilter, 10);
        engagementIdFilter.sendKeys(engagementId);
        try {
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.numberOfElementsToBe
                            (By.xpath("//table[1]/tbody[@class='rgrid rgridtree']/tr[contains(@id, 'row')]"), 1));
        } catch (WebDriverException e) {
            System.out.println("Engagement Id NOT FOUND: " + engagementId);
        }
        loadingElement();
    }

    public boolean searchForEngagementOnGridByName(String engagementName) {
        waitUntilVisibility(engagementNameFilter, 10);
        engagementNameFilter.sendKeys(engagementName);
        try {
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.numberOfElementsToBe
                            (By.xpath("//table[1]/tbody[@class='rgrid rgridtree']/tr[contains(@id, 'row')]"), 1));
            return driver.findElement(By.xpath("//*[text()=' " + engagementName + "']")).isDisplayed();
        } catch (WebDriverException e) {
            System.out.println("Engagement Name NOT FOUND: " + engagementName);
            loadingElement();
            return false;
        }
    }

    public void selectFoundEngagement() {
        try {
            driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']/tr[contains(@id, 'row')]/td[3]")).click();
        } catch (Exception ignore) {
        }
        loadingElement();
    }

    public void clearIdFilter() {
        try {
            engagementIdFilter.clear();
        } catch (UnhandledAlertException e) {
            driver.switchTo().alert().accept();
            engagementIdFilter.clear();
        }
    }

    public void clearNameFilter() {
        try {
            engagementNameFilter.clear();
        } catch (UnhandledAlertException e) {
            driver.switchTo().alert().accept();
            engagementNameFilter.clear();
        }
    }

    public String foundEngagementId() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[3]")).getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "NOT FOUND";
        }
    }

    public String foundEngagementName() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[2]")).getText()
                    .replace("\uE65A", "")
                    .replace("\n", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "NOT FOUND";
        }
    }

    public String foundEngagementManagementUnit() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[4]")).getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "NOT FOUND";
        }
    }

    public String foundEngagementType() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[6]")).getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "NOT FOUND";
        }
    }

    public String foundEngagementBla() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[5]")).getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "NOT FOUND";
        }
    }

    public String getScope() throws IOException {
        try {
            return waitUntilVisibility(scopeTextarea, 5).getAttribute("value");
        } catch (Exception e) {
            e.printStackTrace();
            return "NOT_FOND";
        }
    }

    public String getThematicField() throws IOException {
        if (!foundEngagementType().contains("BCS")) {
            try {
                waitUntilVisibility(thematicField, 5);
                return thematicField.getText();
            } catch (Exception e) {
                e.printStackTrace();
                takeScreenShot("noThemeticField");
                return "NOT_FOND";
            }
        }
        return "NOT_FOND";
    }

    public String getObjective() {
        try {
            return waitUntilVisibility(objectiveTextarea, 5).getAttribute("value");
        } catch (Exception e) {
            e.printStackTrace();
            return "NOT_FOND";
        }
    }

    public String getWpDescription() {
        try {
            return waitUntilVisibility(wpDescTextArea, 5).getAttribute("value");
        } catch (Exception e) {
            e.printStackTrace();
            return "NOT_FOND";
        }
    }

    public List<String> getFoundEngagementPartnersList() throws InterruptedException, IOException {
        partnersTab.click();
        List<String> partnersList = new ArrayList<String>();
        try {
            new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[1]"), 0));

            try {
                for (WebElement partnerRow : driver.findElements(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[1]"))) {
                    String partner = partnerRow.getAttribute("textContent");
                    partnersList.add(partner);
                }
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
                partnersList.clear();
                for (WebElement partnerRow : driver.findElements(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[1]"))) {
                    String partner = partnerRow.getAttribute("textContent");
                    partnersList.add(partner);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            takeScreenShot(e.toString().substring(0, 20));
        }
        return partnersList;
    }

    public void closePopUp() throws IOException {
        try {
            waitUntilVisibility(closePopUp, 5).click();
            loadingElement();
        } catch (Exception ignore) {
        }
    }

    public void openEditPopUp() throws IOException {
        try {
            try {
                waitUntilVisibility(editEngagementButton, 10).click();
                loadingElement();
                waitUntilVisibility(closePopUp, 10);
            } catch (TimeoutException e) {
                takeScreenShot("openEditPopUp");
            }
        } catch (UnhandledAlertException e) {
            System.out.println(driver.switchTo().alert().getText());
            driver.switchTo().alert().accept();
        }
    }

    public List<String> getWpTitlesList() throws IOException {
        List<String> wpTitles = new ArrayList<String>();
        waitUntilVisibility(wpTab, 5).click();
        loadingElement();
        try {
            new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[2]"), 0));
        } catch (TimeoutException e) {
            takeScreenShot("NO_WP_" + foundEngagementId());
        }
        for (WebElement element : driver.findElements(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[2]"))) {
            wpTitles.add(element.getText().trim().replaceAll(" +", " "));
        }
        return wpTitles;
    }

    public void clickEditWpFoundById(String wpId) {
        try {
            waitUntilVisibility(wpTab, 5).click();
            loadingElement();
            driver.findElement(By.xpath("//*[text()='" + wpId + "']")).click();
            editEngagementDetailsButton.click();
            waitUntilVisibility(fluidContainer, 5);
        } catch (Exception ignore) {
        }
    }

    public WebElement goToAnotherPageButton() {
        return driver.findElement(By.xpath("//span[contains(@onclick, 'moveNext')]"));
    }

    public boolean goToAnotherPageButtonIsDisplayed() {
        try {
            return driver.findElement(By.xpath("//span[contains(@onclick, 'moveNext')]")).isDisplayed();
        } catch (NoSuchElementException ignore) {
            return false;
        }
    }

    public String getWpLeadingPartner(String wpId) throws IOException {
        try {
            waitUntilVisibility(wpTab, 5).click();
            loadingElement();
            return driver.findElement(By.xpath("//*[text()='" + wpId + "']/../td[3]")).getAttribute("textContent");
        } catch (NoSuchElementException e) {
            System.out.println("WP Id NOT FOUND: " + wpId);
            return "NOT FOUND";
        }
    }

    public List<String> getPartnerListsInWp() throws IOException {
        List<String> partnersIds = new ArrayList<String>();
        for (WebElement element : driver.findElements(By.xpath("//div[@coll-name='WorkPackage.Partners']/div[3]"))) {
            partnersIds.add(element.getAttribute("textContent"));
        }
        return partnersIds;
    }

    public List<String> getEngagementManagesList() {
        try {
            waitUntilVisibility(fluidContainer, 5);
        } catch (WebDriverException ignore) {
        }
        List<String> engagementManagesList = new ArrayList<String>();
        for (WebElement s : driver.findElements(By.xpath("//div[@coll-name='Engagement.Managers']//div[2]"))) {
            engagementManagesList.add(s.getAttribute("textContent"));
        }
        return engagementManagesList;
    }

    public List<String> getEngagementEditorsList() {
        try {
            waitUntilVisibility(fluidContainer, 5);
        } catch (WebDriverException ignore) {
        }
        List<String> engagementManagesList = new ArrayList<String>();
        for (WebElement s : driver.findElements(By.xpath("//div[@name='Engagement.Editor']//div[@class='item']/div[1]"))) {
            engagementManagesList.add(s.getAttribute("textContent"));
        }
        return engagementManagesList;
    }

    public void clickAddNewEngagementButton() {
        driver.findElement(By.xpath("//div[@id='partTitle-" + getPartId(1) + "']//div[@id='New']")).click();
    }

    public void deleteSelectedEngagement() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        loadingElement();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//div[@id='partTitle-" + getPartId(1) + "']//div[@id='Delete']"))).click();
        wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//div[@event='Yes']"))).click();
        loadingElement();
    }

    public void goToEngagementsPerformanceSection() {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitUntilVisibility(engagementTopMenu, 5)).perform();
        actions.moveToElement(waitUntilVisibility(engagementPerformanceSubmenu, 3)).perform();
        actions.moveToElement(waitUntilVisibility(firstEngagementFromSubmenu, 3)).click().perform();
    }
}
