package SetUp;

import PageObjectPattern.LoginPage;
import PageObjectPattern.Page;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;


public class Scenario {

    protected WebDriver driver;
    protected SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setUp() throws IOException {

        FileUtils.deleteDirectory(new File("screenshots"));

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        System.setProperty("webdriver.edge.driver", "C:\\MicrosoftWebDriver.exe");

        //driver = new ChromeDriver();
        driver = new EdgeDriver();

        driver.manage().window().setPosition(new Point(1920, 1));
        driver.manage().window().maximize();
        driver.get(Page.getCredentials("baseUrl"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.sendLogin(Page.getCredentials("login"));
        loginPage.sendPassword(Page.getCredentials("password"));
        loginPage.clickSubmit();
    }

    @AfterClass
    public void tearDown() {
        driver.close();
        softAssert.assertAll();
    }
}