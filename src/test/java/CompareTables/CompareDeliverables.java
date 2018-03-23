package CompareTables;

import Reports.ReportReader;
import jxl.read.biff.BiffException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CompareDeliverables {

    @Test
    public void deliverablesShouldBeExtracted() throws IOException, BiffException {

        //EXTRACTED DATA
        String extractDataPath = "extracts\\KISSExtract_Deliverable_BP2017Amendment.xls";

        ReportReader extractDeliverableIds = new ReportReader
                (extractDataPath, "Deliverable", "Deliverable ID");
        ReportReader extractDeliverableTitles = new ReportReader
                (extractDataPath, "Deliverable", "Deliverable ID->Title");

        //EOI DATA
        String eioDataPath = "eoiData\\deliverables_result.xls";

        ReportReader eoiDeliverablesIds = new ReportReader
                (eioDataPath, "deliverables_result", "Deliverable ID");
        ReportReader eoiDeliverablesTitles = new ReportReader
                (eioDataPath, "deliverables_result", "Deliverable ID->Deliverable Title");

        List<String> extractDeliverableIdsList = extractDeliverableIds.getList("Deliverable ID");
        for (String extractedDeliverableId : extractDeliverableIdsList) {

            try {
                assertTrue(eoiDeliverablesIds.getList("Deliverable ID").contains(extractedDeliverableId));
            } catch (AssertionError error) {
                System.out.println("\nMissing Extracted Deliverable Id: " + extractedDeliverableId);
            }

            String extractedTitle = extractDeliverableTitles.getSingle(extractedDeliverableId + "->Title");
            String eoiTitle = eoiDeliverablesTitles.getSingle(extractedDeliverableId + "->Deliverable Title");
            try {
                assertEquals(extractedTitle.trim().replaceAll(" +", " "),
                        eoiTitle.trim().replaceAll(" +", " "));
            } catch (AssertionError e) {
                System.out.println("\nExtracted Deliverable Id: " + extractedDeliverableId
                        + "\nExtracted Title: " + extractedTitle
                        + "\nEoi Title: " + eoiTitle);
            }
        }
    }
}
