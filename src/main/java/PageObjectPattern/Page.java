package PageObjectPattern;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Page {
    Page(WebDriver driver) {
        Page.driver = driver;
        PageFactory.initElements(driver, this);
    }

    static WebDriver driver;

    private static File configFile = new File("config.properties");

    void waitUntilVisibilityOfElement(WebElement element, int timeout) {
        loadingElement();
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOf(element));
        loadingElement();
    }

    void loadingElement() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        /*System.out.println("loading elements: "
                + driver.findElements
                (By.xpath("//div[contains(@class, 'loader')or contains(@class ,'overlay')]")).size());*/
        wait.until(ExpectedConditions.invisibilityOfAllElements(
                driver.findElements(By.xpath("//div[contains(@class, 'loader')]"))));
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

    public void takeScreenShot(String fileName) throws IOException, WebDriverException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("screenshots\\" + fileName + ".png"));
    }

    public void clearByBackSpaceKey(WebElement element, int stringLength) {
        for (int in = 0; in < stringLength; in++) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }
}
