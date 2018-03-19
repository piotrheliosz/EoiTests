package PageObjectPattern;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EngagementsPage extends Page {
    public EngagementsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[contains(@id, 'EngagementID')]")
    public WebElement engagementIdFilter;

    @FindBy(xpath = "//li[text()='Partners']")
    WebElement partnersTab;

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
    private WebElement wpTab;

    @FindBy(xpath = "//div[@class='container-fluid']")
    private WebElement fluidContainer;

    @FindBy(className = "overlay")
    private WebElement overlayDiv;

    @FindBy(xpath = "//div[@class='container-fluid']//textarea[@placeholder='Description']")
    private WebElement wpDescTextArea;

    @FindBy(xpath = "//*[text()='View']")
    private WebElement viewButton;

    public void searchForEngagementOnGridById(String engagementId) throws InterruptedException, IOException {
        waitUntilVisibilityOfElement(engagementIdFilter, 10);
        engagementIdFilter.sendKeys(engagementId);
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']/tr[contains(@id, 'row')]"), 1));
        } catch (Exception e) {
            System.out.println("ENGAGEMENT NOT FOUND: " + engagementId + "\n------------------------------");
            takeScreenShot("ENGAGEMENT_NOT_FOUND_" + engagementId);
        }
        loadingElement();
    }

    public void selectFoundEngagement() {
        try {
            driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']/tr[contains(@id, 'row')]/td[3]")).click();
        } catch (NoSuchElementException ignore) {
        }
    }

    public void clearIdFilter() {
        try {
            engagementIdFilter.clear();
        } catch (UnhandledAlertException e) {
            driver.switchTo().alert().accept();
            engagementIdFilter.clear();
        }
    }

    public String foundEngagementId() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[3]")).getText();
        } catch (NoSuchElementException e) {
            return "NOT FOUND";
        }
    }

    public String foundEngagementName() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[2]")).getText()
                    .replace("\uE65A", "")
                    .replace("\n", "");
        } catch (NoSuchElementException e) {
            return "NOT FOUND";
        }
    }

    public String foundEngagementManagementUnit() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[4]")).getText();
        } catch (NoSuchElementException e) {
            return "NOT FOUND";
        }
    }

    public String foundEngagementType() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[6]")).getText();
        } catch (NoSuchElementException e) {
            return "NOT FOUND";
        }
    }

    public String foundEngagementBla() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[5]")).getText();
        } catch (NoSuchElementException e) {
            return "NOT FOUND";
        }
    }

    public String getScope() throws IOException {
        try {
            waitUntilVisibilityOfElement(scopeTextarea, 5);
            return scopeTextarea.getAttribute("value");
        } catch (Exception e) {
            return "NOT_FOND";
        }
    }

    public String getThematicField() {
        try {
            waitUntilVisibilityOfElement(thematicField, 5);
            return thematicField.getText();
        } catch (Exception e) {
            return "NOT_FOND";
        }
    }

    public String getObjective() {
        try {
            waitUntilVisibilityOfElement(objectiveTextarea, 5);
            return objectiveTextarea.getAttribute("value");
        } catch (Exception e) {
            return "NOT_FOND";
        }
    }

    public String getWpDescription() {
        try {
            waitUntilVisibilityOfElement(wpDescTextArea, 5);
            return wpDescTextArea.getAttribute("value");
        } catch (Exception e) {
            return "NOT_FOND";
        }
    }

    public List<String> foundEngagementPartners() throws InterruptedException {
        List<String> partnersList = new ArrayList<String>();
        for (WebElement partnerRow : driver.findElements(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[1]"))) {
            String partner = partnerRow.getText();
            partnersList.add(partner);
        }
        return partnersList;
    }

    public void closePopUp() throws IOException {
        try {
            waitUntilVisibilityOfElement(closePopUp, 5);
            closePopUp.click();
            loadingElement();
        } catch (Exception ignore) {
        }
    }

    public void openEditPopUp() throws IOException {
        try {
            try {
                waitUntilVisibilityOfElement(editEngagementButton, 10);
                editEngagementButton.click();
                loadingElement();
                waitUntilVisibilityOfElement(closePopUp, 10);
            } catch (TimeoutException e) {
                takeScreenShot("openEditPopUp");
            }
        } catch (UnhandledAlertException e) {
            System.out.println(driver.switchTo().alert().getText());
            driver.switchTo().alert().accept();
        }
    }

    public List<WebElement> getWpTitlesList() throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        waitUntilVisibilityOfElement(wpTab, 5);
        wpTab.click();
        loadingElement();
        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[2]"), 0));
        } catch (TimeoutException e) {
            takeScreenShot("NO_WP_" + foundEngagementId());
        }
        return driver.findElements(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[2]"));
    }

    public void editWpFoundById(String wpId) {
        try {
            waitUntilVisibilityOfElement(wpTab, 5);
            wpTab.click();
            loadingElement();
            driver.findElement(By.xpath("//*[text()='" + wpId + "']")).click();
            editEngagementDetailsButton.click();
            waitUntilVisibilityOfElement(fluidContainer, 5);
        } catch (Exception ignore) {
        }
    }

    public boolean footedIsDisplayed() {
        try {
            return driver.findElement(By.xpath("//tfoot[contains(@id, 'rgridfoot')]//span[contains(text(),'Page')]")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void gotoAnotherPage() {
        driver.findElement(By.xpath("//span[contains(@onclick, 'moveNext')]")).click();
    }

    public String getWpLeadingPartner(String wpId) throws IOException {
        try {
            waitUntilVisibilityOfElement(wpTab, 5);
            wpTab.click();
            loadingElement();
            return driver.findElement(By.xpath("//*[text()='" + wpId + "']/../td[3]")).getText();
        } catch (Exception e) {
            e.printStackTrace();
            takeScreenShot(wpId + "_wpId_NoSuchElementException");
            return "";
        }
    }

    public List<String> getPartnerListsInWp() throws IOException {
        List<String> partnersIds = new ArrayList<String>();
        for (WebElement element : driver.findElements(By.xpath("//div[@coll-name='WorkPackage.Partners']/div[3]"))) {
            partnersIds.add(element.getAttribute("textContent"));
        }
        return partnersIds;
    }

    public List<String> getDeliverablesIdsList() {
        List<String> deliverablesIdsListIds = new ArrayList<String>();
        for (WebElement element : driver.findElements(By.xpath("//div[@coll-name='Task.Deliverables']//div[2]"))) {
            deliverablesIdsListIds.add(element.getAttribute("textContent"));
        }
        return deliverablesIdsListIds;
    }

    public void viewActions() {
        loadingElement();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", viewButton);
        waitUntilVisibilityOfElement(viewButton, 10);
        viewButton.click();
        loadingElement();
    }
}
