import PageObjectPattern.EngagementsPage;
import PageObjectPattern.NewEngagementPage;
import SetUp.Scenario;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;


public class AddAndRemoveEngagementTest extends Scenario {

    private String engagementName = UUID.randomUUID().toString();

    @Test(priority = 1)
    public void engagementShouldBeAdded() {

        EngagementsPage engagementsPage = new EngagementsPage(driver);
        engagementsPage.navigateToEngegementOverviewPage();
        engagementsPage.clickAddNewEngagementButton();

        NewEngagementPage newEngagementsPage = new NewEngagementPage(driver);
        newEngagementsPage.sendNewEngagementName(engagementName);
        newEngagementsPage.setTypeOfNewEngagement("Master School");
        newEngagementsPage.setParentPartnerToNewEngagement("KRAKEN");
        newEngagementsPage.setBlaToNewEngagement("Innovation Projects");
        newEngagementsPage.setManagementsUnitToNewEngagement("Central Europe");
        newEngagementsPage.setManagerToNewEngagement("Piotr Heliosz");
        newEngagementsPage.saveButton.click();

        System.out.println("Engagement name: " + engagementName);

        assertTrue(engagementsPage.searchForEngagementOnGridByName(engagementName));

        engagementsPage.clearNameFilter();
    }

    @Test(priority = 2)
    public void engagementShouldBeRemoved() {

        EngagementsPage engagementsPage = new EngagementsPage(driver);
        engagementsPage.navigateToEngegementOverviewPage();
        engagementsPage.searchForEngagementOnGridByName(engagementName);
        engagementsPage.selectFoundEngagement();
        engagementsPage.deleteSelectedEngagement();
        engagementsPage.clearNameFilter();

        assertFalse(engagementsPage.searchForEngagementOnGridByName(engagementName));
    }
}
