import jxl.read.biff.BiffException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;

public class CompareTables {

    @Test
    public void deliverablesShouldBeExtracted() throws IOException, BiffException {

        //EXTRACT DATA
        String extractPath = "extracts\\KISSExtract_Deliverable_BP2017Amendment.xls";

        ReportReader extractWpId = new ReportReader
                (extractPath, "Deliverable", "Work Package ID");
        ReportReader extractEngagementId = new ReportReader
                (extractPath, "Deliverable", "Work Package ID->Engagement ID");
        ReportReader extractDeliverableIds = new ReportReader
                (extractPath, "Deliverable", "Work Package ID->Deliverable ID");
        ReportReader extractDeliverableTitles = new ReportReader
                (extractPath, "Deliverable", "Work Package ID->Title");

        //EOI DATA
        String myDataPath = "C:\\workspace\\EoiTests\\EOI_deliverables_report.xls";

        ReportReader eoiDeliverablesIds = new ReportReader
                (myDataPath, "result", "Work Package ID->Deliverables IDs");
        ReportReader eoiDeliverablesTitles = new ReportReader
                (myDataPath, "result", "Work Package ID->Deliverables Titles");

        int row = 0;
        Set<String> wpIds = extractWpId.getSet("Work Package ID");
        for (String wpId : wpIds) {

            String engagementId = extractEngagementId.getSingle(wpId + "->Engagement ID");

            List<String> eoiExtractedDeliverablesIds = new ArrayList<String>();
            try {
                String[] eoiDeliverablesIdsCollection = eoiDeliverablesIds.getSingle(wpId + "->Deliverables IDs").split(",");
                eoiExtractedDeliverablesIds.addAll(Arrays.asList(eoiDeliverablesIdsCollection));
                //System.out.println(++row + ".\nwpId: " + wpId + " | eoiExtractedDeliverablesIds: " + eoiExtractedDeliverablesIds);
            } catch (RuntimeException e) {
                System.out.println("Engagement Id: " + engagementId + "\nNo data found: wpId " + wpId);
            }
            List<String> extractedDeliverablesIds = extractDeliverableIds.getList(wpId + "->Deliverable ID");
            //System.out.println("wpId: " + wpId + " | extractedDeliverablesIds: " + extractedDeliverablesIds);

            List<String> extractedDeliverablesTitles = extractDeliverableTitles.getList(wpId + "->Title");

            List<String> eoiExtractedDeliverablesTitles = new ArrayList<String>();
            String[] eoiDeliverablesTitlesCollection = eoiDeliverablesTitles.getSingle(wpId + "->Deliverables Titles").split("@");
            eoiExtractedDeliverablesTitles.addAll(Arrays.asList(eoiDeliverablesTitlesCollection));

            try {
                assertEquals(extractedDeliverablesIds.size(), eoiExtractedDeliverablesIds.size());
                for (int i = 0; i < extractedDeliverablesIds.size(); ++i) {
                    assertEquals(eoiExtractedDeliverablesIds.get(i).trim(), extractedDeliverablesIds.get(i).trim());
                    assertEquals(eoiExtractedDeliverablesTitles.get(i).trim(), extractedDeliverablesTitles.get(i).trim());
                }
            } catch (AssertionError error) {
                System.out.println(error
                        + "\nEngagement Id: " + engagementId
                        + "\nwpId: " + wpId + "\nextractedDeliverablesIds: " + extractedDeliverablesIds
                        + "\neoiExtractedDeliverablesIds: " + eoiExtractedDeliverablesIds + "\n");
            }
        }
    }
}
