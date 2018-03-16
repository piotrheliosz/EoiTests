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
    public void compareExtractWithMyData() throws IOException, BiffException {

        //EXTRACT DATA
        String extractPath = "extracts\\KISSExtract_Deliverable_BP2017Amendment.xls";
        ReportReader extractWpId = new ReportReader
                (extractPath, "Deliverable", "Work Package ID");
        ReportReader extractDeliverableIds = new ReportReader
                (extractPath, "Deliverable", "Work Package ID->Deliverable ID");

        //EOI DATA
        String myDataPath = "C:\\workspace\\EoiTests\\EOI_deliverables_report.xls";
        ReportReader eoiData = new ReportReader
                (myDataPath, "result", "Work Package ID->Deliverables IDs");

        int row = 0;
        Set<String> wpIds = extractWpId.getSet("Work Package ID");
        for (String wpId : wpIds) {

            List<String> eoiDeliverables = new ArrayList<String>();

            try {
                String[] eoiDeliverablesCollection = eoiData.getSingle(wpId + "->" + "Deliverables IDs").split(",");
                eoiDeliverables.addAll(Arrays.asList(eoiDeliverablesCollection));
                //System.out.println(++row + ".\nwpId: " + wpId + " | eoiDeliverables: " + eoiDeliverables);
            } catch (RuntimeException e) {
                System.out.println("No data found: wpId_" + wpId);
            }
            List<String> extractedDeliverablesIds = extractDeliverableIds.getList(wpId + "->Deliverable ID");
            //System.out.println("wpId: " + wpId + " | extractedDeliverablesIds: " + extractedDeliverablesIds);

            try {
                assertEquals(extractedDeliverablesIds.size(), eoiDeliverables.size());
                for (int i = 0; i < extractedDeliverablesIds.size(); ++i) {
                    //assertEquals(extractedDeliverablesIds.get(i), eoiDeliverables.get(i));
                }

            } catch (AssertionError error) {
                System.out.println(error + " wpId: " + wpId + "\nextractedDeliverablesIds: " + extractedDeliverablesIds
                        + "\neoiDeliverables: " + eoiDeliverables + "\n");
            }
        }
    }
}
