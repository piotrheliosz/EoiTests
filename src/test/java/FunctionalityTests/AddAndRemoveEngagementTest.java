package FunctionalityTests;

import PageObjectPattern.EngagementsPage;
import PageObjectPattern.HomePage;
import PageObjectPattern.NewEngagementPage;
import SetUp.Scenario;
import org.testng.annotations.Test;

import java.io.IOException;

import static PageObjectPattern.Page.engagementName;

public class AddAndRemoveEngagementTest extends Scenario {

    @Test(priority = 1)
    public void engagementShouldBeAdded() throws IOException {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnEngagementItemFromMenu();

        EngagementsPage engagementsPage = new EngagementsPage(driver);
        engagementsPage.clickAddNewEngagementButton();

        NewEngagementPage newEngagementsPage = new NewEngagementPage(driver);
        newEngagementsPage.sendNewEngagementName(engagementName);
        newEngagementsPage.setTypeOfNewEngagement("Master School");
        newEngagementsPage.setParentPartnerToNewEngagement("KRAKEN");
        newEngagementsPage.setBlaToNewEngagement("Innovation Projects Strategy & Business Development");
        newEngagementsPage.setManagementsUnitToNewEngagement("Central Europe");
        newEngagementsPage.setManagerToNewEngagement("piotr.heliosz");
        newEngagementsPage.clickSaveButton();

        System.out.println("Engagement name: " + engagementName);

        softAssert.assertTrue(engagementsPage.searchForEngagementOnGridByName(engagementName));

        engagementsPage.clickHomeButton();
    }

    @Test(priority = 2)
    public void engagementShouldBeRemoved() throws IOException {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnEngagementItemFromMenu();

        EngagementsPage engagementsPage = new EngagementsPage(driver);
        engagementsPage.searchForEngagementOnGridByName(engagementName);
        engagementsPage.selectFoundEngagement();
        engagementsPage.deleteSelectedEngagement();
        engagementsPage.clickHomeButton();

        homePage.clickOnEngagementItemFromMenu();

        softAssert.assertFalse(engagementsPage.searchForEngagementOnGridByName(engagementName));
    }
}
