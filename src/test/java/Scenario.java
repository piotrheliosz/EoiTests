import PageObjectPattern.LoginPage;
import PageObjectPattern.Page;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class Scenario {

    WebDriver driver;

    @BeforeClass
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        System.setProperty("webdriver.edge.driver", "C:\\MicrosoftWebDriver.exe");
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");

        driver = new ChromeDriver();
        //driver = new EdgeDriver();
        driver.manage().window().setPosition(new Point(1920, 1));
        driver.manage().window().maximize();
        driver.get(Page.getCredentials("baseUrl"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.sendLogin(Page.getCredentials("login"));
        loginPage.sendPassword(Page.getCredentials("password"));
        loginPage.loginButton.click();

    }

    @AfterClass
    public void tearDown() {
        driver.close();
    }

}
