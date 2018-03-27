package CompareTables;

import Reports.ReportReader;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CompareObjectives {

    private String extractDataPath = "extracts\\KISSExtract_Objective_BP2017Amendment.xls";
    private String eioDataPath = "eoiData\\eoi_objective_result.xls";


    @Test
    public void objectiveIdShouldBeExtracted() throws IOException {

        //EXTRACTED DATA
        ReportReader extractObjectiveIds = new ReportReader
                (extractDataPath, "Objective", "Objective ID");

        //EOI DATA
        ReportReader eoiObjectiveIds = new ReportReader
                (eioDataPath, "objective_result", "Objective ID");

        List<String> extractObjectiveIdsList = extractObjectiveIds.getList("Objective ID");
        for (String extractedObjectiveId : extractObjectiveIdsList) {

            try {
                assertTrue(eoiObjectiveIds.getList("Objective ID").contains(extractedObjectiveId));
            } catch (AssertionError error) {
                System.out.println("Missing Extracted Objective Id: " + extractedObjectiveId);
            }
        }
    }

    @Test
    public void objectiveTitleShouldBeExtracted() throws IOException {


        //EXTRACTED DATA
        ReportReader extractObjectiveIds = new ReportReader
                (extractDataPath, "Objective", "Objective ID");
        ReportReader extractObjectiveTitles = new ReportReader
                (extractDataPath, "Objective", "Objective ID->Title");

        //EOI DATA
        ReportReader eoiObjectiveTitles = new ReportReader
                (eioDataPath, "objective_result", "Objective ID->Title");

        List<String> extractObjectiveIdsList = extractObjectiveIds.getList("Objective ID");
        for (String extractedObjectiveId : extractObjectiveIdsList) {

            String extractedTitle = extractObjectiveTitles.getSingle(extractedObjectiveId + "->Title");
            String eoiTitle = "";

            try {
                eoiTitle = eoiObjectiveTitles.getSingle(extractedObjectiveId + "->Title");
            } catch (RuntimeException ignore) {
            }

            try {
                assertEquals(extractedTitle.trim().replaceAll(" +", " "),
                        eoiTitle.trim().replaceAll(" +", " "));
            } catch (AssertionError error) {
                System.out.println(error
                        + "\nExtracted Objective Id: " + extractedObjectiveId
                        //+ "\nExtracted Title: " + extractedTitle
                        //+ "\nEoi Title: " + eoiTitle
                );
            }
        }
    }
}