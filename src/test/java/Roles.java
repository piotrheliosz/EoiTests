import PageObjectPattern.EngagementsPage;
import SetUp.Scenario;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class Roles extends Scenario {

    @Test
    public void managerShouldBeAdded() throws IOException, InterruptedException {

        String filePath = "extracts\\KISSExtract_Engagement_BP2017Amendment.xls";

        EngagementsPage engagementsPage = new EngagementsPage(driver);
        engagementsPage.navigateToEngegementOverviewPage();

        List<String> engagementsWithNoPrivilegeToEdit = new ArrayList<String>();

        int i = 0;
        for (String id : engagementsPage.getEngagementsIdsList(filePath)) {

            engagementsPage.searchForEngagementOnGridById(id);
            engagementsPage.selectFoundEngagement();
            engagementsPage.openEditPopUp();

            try {
                assertTrue(engagementsPage.getEngagementManagesList().contains("Piotr Heliosz"));
            } catch (AssertionError error) {
                System.out.println(++i + ". " + error + " Engagement ID: " + id);
                engagementsWithNoPrivilegeToEdit.add(id);
            }
            engagementsPage.closePopUp();
            engagementsPage.engagementIdFilter.clear();
        }
        System.out.println("engagementsWithNoPrivilegeToEdit: " + engagementsWithNoPrivilegeToEdit);
    }

    @Test
    public void editorShouldBeAdded() throws IOException, InterruptedException {

        String filePath = "extracts\\KISSExtract_Engagement_BP2017Amendment.xls";

        EngagementsPage engagementsPage = new EngagementsPage(driver);
        engagementsPage.navigateToEngegementOverviewPage();

        List<String> engagementsWithNoPrivilegeToEdit = new ArrayList<String>();

        int i = 0;
        for (String id : engagementsPage.getEngagementsIdsList(filePath)) {

            engagementsPage.searchForEngagementOnGridById(id);
            engagementsPage.selectFoundEngagement();
            engagementsPage.openEditPopUp();

            try {
                assertTrue(engagementsPage.getEngagementEditorsList().contains("Piotr Heliosz"));
            } catch (AssertionError error) {
                System.out.println(++i + ". " + error + " Engagement ID: " + id);
                engagementsWithNoPrivilegeToEdit.add(id);
            }
            engagementsPage.closePopUp();
            engagementsPage.engagementIdFilter.clear();
        }
        System.out.println("engagementsWithNoPrivilegeToEdit: " + engagementsWithNoPrivilegeToEdit);
    }
}