package PageObjectPattern;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class EngagementsPage extends Page {
    public EngagementsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[contains(@id, 'EngagementID')]")
    public WebElement engagementIdFilter;

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
    WebElement wpTab;

    @FindBy(xpath = "//li[text()='Partners']")
    WebElement partnersTab;

    @FindBy(xpath = "//div[@class='container-fluid']")
    WebElement fluidContainer;

    @FindBy(xpath = "//div[@class='container-fluid']//textarea[@placeholder='Description']")
    WebElement wpDescTextArea;


    public void searchForEnagagementOnGrid(String id) throws InterruptedException {
        waitUntilVisibilityOfElement(engagementIdFilter, 10);
        engagementIdFilter.sendKeys(id);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']/tr[contains(@id, 'row')]"), 1));
        loadingElement();
        sleep(3000); //TODO
    }

    public String foundEngagementById() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[3]")).getText();
        } catch (NoSuchElementException e) {
            return "NOT FOUND";
        }
    }

    public String foundEngagementByName() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[2]")).getText()
                    .replace("\uE65A", "")
                    .replace("\n", "");
        } catch (NoSuchElementException e) {
            return "NOT FOUND";
        }
    }

    public String foundEngagementByManagementUnit() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[4]")).getText();
        } catch (NoSuchElementException e) {
            return "NOT FOUND";
        }
    }

    public String foundEngagementByType() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[6]")).getText();
        } catch (NoSuchElementException e) {
            return "NOT FOUND";
        }
    }

    public String foundEngagementByBla() {
        try {
            return driver.findElement(By.xpath("//table[1]/tbody[@class='rgrid rgridtree']//td[5]")).getText();
        } catch (NoSuchElementException e) {
            return "NOT FOUND";
        }
    }

    public List<String> foundEngagementByPartner(int partnersLineLength) throws InterruptedException {
        List<String> partnersList = new ArrayList<String>();
        List<WebElement> partnersOnGrid = driver.findElements(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[1]"));
        for (WebElement partnerRow : partnersOnGrid) {
            String partner = partnerRow.getText();
            partnersList.add(partner);
        }
        return partnersList;
    }

    public String getObjective() {
        waitUntilVisibilityOfElement(objectiveTextarea, 5);
        return objectiveTextarea.getAttribute("value");
    }

    public String getScope() throws IOException {
        waitUntilVisibilityOfElement(scopeTextarea, 5);
        return scopeTextarea.getAttribute("value");
    }

    public String getThematicField() {
        waitUntilVisibilityOfElement(thematicField, 5);
        return thematicField.getText();
    }

    public void closePopUp() throws IOException {
        try {
            waitUntilVisibilityOfElement(closePopUp, 5);
            closePopUp.click();
            loadingElement();
        } catch (WebDriverException e) {
            e.printStackTrace();
            takeScreenShot("closePopUp");
            closePopUp.click();
        }
    }

    public void openEditPopUp() throws IOException {
        try {
            waitUntilVisibilityOfElement(editEngagementButton, 10);
            editEngagementButton.click();
            loadingElement();
            waitUntilVisibilityOfElement(closePopUp, 10);
        } catch (TimeoutException e) {
            takeScreenShot("openEditPopUp");
            waitUntilVisibilityOfElement(editEngagementButton, 10);
            editEngagementButton.click();
            loadingElement();
            waitUntilVisibilityOfElement(closePopUp, 10);
        }
    }

    public List<WebElement> getWpTitlesList() throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
    /*    loadingElement();
        partnersTab.click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[1]"),0));*/
        loadingElement();
        waitUntilVisibilityOfElement(wpTab, 5);
        wpTab.click();
        loadingElement();
        try {
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[2]"), 0));
        } catch (TimeoutException e) {
            takeScreenShot("NO_WP_" + foundEngagementById());
        }
        return driver.findElements(By.xpath("//table/tbody[@class='rgrid rgridlist']/tr/td[2]"));
    }

    public void editWp(String wpId) {
        wpTab.click();
        loadingElement();
        driver.findElement(By.xpath("//*[text()='" + wpId + "']")).click();
        editEngagementDetailsButton.click();
        waitUntilVisibilityOfElement(fluidContainer,5);
    }

    public String getWpDesc() {
        return wpDescTextArea.getAttribute("value");
    }
}
