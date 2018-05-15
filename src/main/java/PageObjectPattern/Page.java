package PageObjectPattern;

import Reports.ReportReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;


public class Page {
    static WebDriver driver;
    private static final File configFile = new File("config.properties");
    public static final String filePath = "BP2018_Engagements_Ids.xls";
    public static final String engagementName = UUID.randomUUID().toString();
    public static final String existingEngagementName = "ba2f6d06-fd60-4de5-9e06-1a05bbcb6233";

    Page(WebDriver driver) {
        Page.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static String getCredentials(String credential) {
        try {
            FileReader fileReader = new FileReader(configFile);
            Properties properties = new Properties();
            properties.load(fileReader);
            return properties.getProperty(credential);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    WebElement waitUntilVisibility(WebElement element, int timeout) {
        loadingElement();
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
    }

    public void clickStaleElementBy(By by) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                driver.findElement(by).click();
                break;
            } catch (StaleElementReferenceException ignored) {
            }
            attempts++;
        }
    }

    void clickStaleWebElement(WebElement element) {
        int attempts = 0;
        while (attempts < 5) {
            try {
                element.click();
                break;
            } catch (StaleElementReferenceException ignored) {
            }
            attempts++;
        }
    }

    void loadingElement() {
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfAllElements
                (driver.findElements(By.xpath("//div[contains(@class, 'loader')]"))));
    }

    public static void takeScreenShot(String fileName) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("screenshots\\" + fileName + ".png"));
    }

    public List<String> getEngagementsIdsListFromFile(String filePath) throws IOException {
        ReportReader extractIds = new ReportReader(filePath, "Engagement", "Engagement ID");
        return (extractIds.getList("Engagement ID"));
    }

    public Set<String> getEngagementsIdsSetFromFile(String filePath, String sheetName) throws IOException {
        ReportReader extractIds = new ReportReader(filePath, sheetName, "Engagement ID");
        return (extractIds.getSet("Engagement ID"));
    }

    public Set<String> getWpEngagementsIdsSetFromFile(String filePath) throws IOException {
        ReportReader extractIds = new ReportReader(filePath, "WorkPackage", "Engagement ID");
        return (extractIds.getSet("Engagement ID"));
    }

    public void navigateToEngagementOverviewPage() {
        if (!driver.getCurrentUrl().contains("engagements-overview")) {
            driver.navigate().to(getCredentials("baseUrl") + "/portal/kicinnoenergy-acc/page/engagements-overview/");
        }
    }

    public void clickHomeButton() throws IOException {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("homeButton"))).click();
        } catch (WebDriverException e) {
            e.printStackTrace();
            takeScreenShot("clickHomeButton");
        }
        loadingElement();
    }

}
