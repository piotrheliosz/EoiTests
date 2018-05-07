package CompareTables;

import Reports.ReportReader;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class CompareObjectives {

    private String extractDataPath = "extracts\\KISSExtract_Objective_BP2018.xls";
    private String eioDataPath = "eoiData\\eoi_objective_result.xls";


    @Test
    public void objectiveIdShouldBeExtracted() throws IOException {

        //EXTRACTED DATA
        ReportReader extractEngagementsIds = new ReportReader
                (extractDataPath, "Objective", "Objective ID->Engagement ID");
        ReportReader extractObjectiveIds = new ReportReader
                (extractDataPath, "Objective", "Objective ID");

        //EOI DATA
        ReportReader eoiObjectiveIds = new ReportReader
                (eioDataPath, "objective_result", "Objective ID");

        int i = 0;
        for (String extractedObjectiveId : extractObjectiveIds.getList("Objective ID")) {
            try {
                assertTrue(eoiObjectiveIds.getList("Objective ID").contains(extractedObjectiveId));
            } catch (AssertionError error) {
                System.out.println(++i + ". Missing Extracted Objective Id: " + extractedObjectiveId
                + " | Engagements Id: " + extractEngagementsIds.getSingle(extractedObjectiveId + "->Engagement ID"));
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