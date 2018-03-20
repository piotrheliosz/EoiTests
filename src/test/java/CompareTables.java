import Reports.ReportReader;
import jxl.read.biff.BiffException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CompareTables {

    @Test
    public void deliverablesShouldBeExtracted() throws IOException, BiffException {

        //EXTRACTED DATA
        String extractDataPath = "extracts\\KISSExtract_Deliverable_BP2017Amendment.xls";

        ReportReader extractDeliverableIds = new ReportReader
                (extractDataPath, "Deliverable", "Deliverable ID");
        ReportReader extractDeliverableTitles = new ReportReader
                (extractDataPath, "Deliverable", "Deliverable ID->Title");

        //EOI DATA
        String eioDataPath = "C:\\workspace\\EoiTests\\deliverables_result.xls";

        ReportReader eoiDeliverablesIds = new ReportReader
                (eioDataPath, "deliverables_result", "Deliverable ID");
        ReportReader eoiDeliverablesTitles = new ReportReader
                (eioDataPath, "deliverables_result", "Deliverable ID->Deliverable Title");

        for (String extractedDeliverableId : extractDeliverableIds.getList("Deliverable ID")) {

            List<String> eoiDeliverablesIdsList = eoiDeliverablesIds.getList("Deliverable ID");
            try {
                assertTrue(eoiDeliverablesIdsList.contains(extractedDeliverableId));
            } catch (AssertionError e) {
                System.out.println("extractedDeliverableId: " + extractedDeliverableId);
            }

            String extractedTitle = extractDeliverableTitles.getSingle(extractedDeliverableId + "->Title");
            String eoiTitle = eoiDeliverablesTitles.getSingle(extractedDeliverableId + "->Deliverable Title");
            try {
                assertEquals(extractedTitle, eoiTitle);
            } catch (AssertionError e) {
                System.out.println("\nextractedDeliverableId: " + extractedDeliverableId
                        + "\nextractedTitle: " + extractedTitle
                        + "\neoiTitle: " + eoiTitle);
            }
        }
    }
}
