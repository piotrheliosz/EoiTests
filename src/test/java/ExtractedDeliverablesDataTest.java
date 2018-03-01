

import PageObjectPattern.EngagementsPage;
import PageObjectPattern.MainPage;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertTrue;

public class ExtractedDeliverablesDataTest extends Scenario {

    private String filePath = "extracts\\KISSExtract_Deliverable_BP2017Amendment.xls";

    @Test
    public void compareExtractedDeliverablesIds() throws InterruptedException, IOException {

        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();


        EngagementsPage engagementsPage = new EngagementsPage(driver);

        ReportReader extractIds = new ReportReader(filePath, "Deliverable", "Engagement ID");
        Set<String> engagementsIds = extractIds.getSet("Engagement ID");
        for (String engagementId : engagementsIds) {

            engagementsPage.searchForEnagagementOnGridById(engagementId);
            engagementsPage.selectFoundEngagement();

            ReportReader extractWpIds = new ReportReader(filePath, "Deliverable", "Engagement ID->Work Package ID");
            List<String> wpIds = extractWpIds.getList(engagementId + "->Work Package ID");
            for (String wpId : wpIds) {

                System.out.println("\nengagementId: " + engagementId + "\nwpId: " + wpId);

                engagementsPage.editWpFoundById(wpId);
                engagementsPage.viewActions();

                List<String> eoiDeliverablesIds = engagementsPage.getDeliverablesIdsList();

                ReportReader extractDeliverablesIds = new ReportReader(filePath, "Deliverable", "Engagement ID->Work Package ID->Deliverable ID");
                List<String> extractedDeliverablesIds = extractDeliverablesIds.getList(engagementId + "->" + wpId + "->" + "Deliverable ID");

                engagementsPage.closePopUp();
                engagementsPage.closePopUp();

                try {
                    assertTrue(eoiDeliverablesIds.containsAll(extractedDeliverablesIds));
                } catch (AssertionError error) {
                    System.out.println("\n" + error + "\nengagementId: " + engagementId + "\nwpId: " + wpId);
                }
            }
            engagementsPage.engagementIdFilter.clear();
        }
    }
}


