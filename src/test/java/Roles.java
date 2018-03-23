import PageObjectPattern.EngagementsPage;
import PageObjectPattern.MainPage;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertTrue;

public class Roles extends Scenario {

    @Test
    public void userShouldBeAdded() throws IOException, InterruptedException {

        String filePath = "extracts\\KISSExtract_Engagement_BP2017Amendment.xls";

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        int i = 0;
        for (String id : engagementsPage.getEngagementsIdsList(filePath)) {

            engagementsPage.searchForEngagementOnGridById(id);
            engagementsPage.selectFoundEngagement();
            engagementsPage.openEditPopUp();

            try {
                assertTrue(engagementsPage.getEngagementManagesList().contains("Piotr Heliosz"));
            } catch (AssertionError error) {
                System.out.println(++i + ". " + error + " Engagement ID: " + id);
                engagementsPage.takeScreenShot(id + "_missing Manager");
            }
            engagementsPage.closePopUp();
            engagementsPage.engagementIdFilter.clear();
        }
    }
}