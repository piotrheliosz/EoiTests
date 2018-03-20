package CompareTables;

import Reports.ReportReader;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CompareObjectives {
    @Test
    public void objectiveShouldBeExtracted() throws IOException {

        //EXTRACTED DATA
        String extractDataPath = "extracts\\KISSExtract_Objective_BP2017Amendment.xls";

        ReportReader extractObjectiveIds = new ReportReader
                (extractDataPath, "Objective", "Objective ID");
        ReportReader extractObjectiveTitles = new ReportReader
                (extractDataPath, "Objective", "Objective ID->Title");

        //EOI DATA
        String eioDataPath = "eoiData\\objective_result.xls";

        ReportReader eoiObjectiveIds = new ReportReader
                (eioDataPath, "objective_result", "Objective ID");
        ReportReader eoiObjectiveTitles = new ReportReader
                (eioDataPath, "objective_result", "Objective ID->Objective Title");

        List<String> extractObjectiveIdsList = extractObjectiveIds.getList("Objective ID");

        for (String extractedObjectiveId : extractObjectiveIdsList) {

            try {
                assertTrue(eoiObjectiveIds.getList("Objective ID").contains(extractedObjectiveId));
            } catch (AssertionError error) {
                System.out.println("Missing Extracted Objective Id: " + extractedObjectiveId);
            }

            String extractedTitle = extractObjectiveTitles.getSingle(extractedObjectiveId + "->Title");
            String eoiTitle = eoiObjectiveTitles.getSingle(extractedObjectiveId + "->Objective Title");

            try {
                assertEquals(extractedTitle.trim().replaceAll(" +", " "),
                        eoiTitle.trim().replaceAll(" +", " "));
            } catch (AssertionError error) {
                System.out.println("\nExtracted Objective Id: " + extractedObjectiveId
                        + "\nExtracted Title: " + extractedTitle
                        + "\nEoi Title: " + eoiTitle);
            }
        }
    }
}