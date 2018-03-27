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


public class Page {
    static WebDriver driver;
    private static File configFile = new File("config.properties");

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

    void waitUntilVisibility(WebElement element, int timeout) {
        loadingElement();
        new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
        loadingElement();
    }

    void loadingElement() {
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfAllElements
                (driver.findElements(By.xpath("//div[contains(@class, 'loader')]"))));
    }

    public void takeScreenShot(String fileName) throws IOException, WebDriverException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("screenshots\\" + fileName + ".png"));
    }

    public List<String> getEngagementsIdsList(String filePath) throws IOException {
        ReportReader extractIds = new ReportReader(filePath, "Engagement", "Engagement ID");
        return (extractIds.getList("Engagement ID"));
    }

    public Set<String> getEngagementsIdsSet(String filePath) throws IOException {
        ReportReader extractIds = new ReportReader(filePath, "Engagement", "Engagement ID");
        return (extractIds.getSet("Engagement ID"));
    }

    public Set<String> getWpEngagementsIdsSet(String filePath) throws IOException {
        ReportReader extractIds = new ReportReader(filePath, "WorkPackage", "Engagement ID");
        return (extractIds.getSet("Engagement ID"));
    }

}
