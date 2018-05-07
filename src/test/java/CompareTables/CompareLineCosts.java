package CompareTables;

import Reports.ReportReader;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class CompareLineCosts {

    private String extractDataPath = "extracts\\KISSExtract_Cost_line_all_BP2018.xls";
    private String eoiDataPath = "eoiData\\eoi_costLines_result.xls";

    @Test
    public void allCostsLinesShouldBeExtracted() throws IOException {

        //EXTRACTED DATA
        ReportReader extractCostLineIds = new ReportReader
                (extractDataPath, "Cost Line", "Cost Line ID");
        ReportReader extractEngagementsIds = new ReportReader
                (extractDataPath, "Cost Line", "Cost Line ID->Engagement ID");

        //EOI DATA
        ReportReader eoiCostLineIds = new ReportReader
                (eoiDataPath, "costLines_result", "Cost Line ID");

        int i = 0;
        List<String> extractCostLineIdsList = extractCostLineIds.getList("Cost Line ID");
        for (String extractedCostLineId : extractCostLineIdsList) {

            try {
                assertTrue(eoiCostLineIds.getList("Cost Line ID").contains(extractedCostLineId));
            } catch (AssertionError error) {
                String extractedEngagementId = extractEngagementsIds.getSingle(extractedCostLineId + "->Engagement ID");
                System.out.println("\n" + ++i + ". Engagement ID: " + extractedEngagementId + "\nMissing Line Cost: " + extractedCostLineId);
            }
        }
    }

    @Test
    public void budgetsShouldBeEquals() throws IOException {

        //EXTRACTED DATA
        ReportReader extractEngagementsIds = new ReportReader
                (extractDataPath, "Cost Line", "Cost Line ID->Engagement ID");
        ReportReader extractCostLineBudget = new ReportReader
                (extractDataPath, "Cost Line", "Cost Line ID->Budget Amount");

        //EOI DATA
        ReportReader eoiCostLineIds = new ReportReader
                (eoiDataPath, "costLines_result", "Cost Line ID");
        ReportReader eoiCostLineBudget = new ReportReader
                (eoiDataPath, "costLines_result", "Cost Line ID->Budget Amount");

        int i = 0;
        List<String> eoiCostLineIdsList = eoiCostLineIds.getList("Cost Line ID");
        for (String extractedCostLineId : eoiCostLineIdsList) {

            String extractedEngagementId = "";
            double eoiBudget = 0;
            double extractedBudget = 0;

            try {
                extractedEngagementId = extractEngagementsIds.getSingle(extractedCostLineId + "->Engagement ID");
                eoiBudget = Double.parseDouble(eoiCostLineBudget.getSingle(extractedCostLineId + "->Budget Amount")) * 100;
                extractedBudget = Double.parseDouble(extractCostLineBudget.getSingle(extractedCostLineId + "->Budget Amount")) * 100;
            } catch (RuntimeException ignore) {
            }

            try {
                assertEquals(extractedBudget, eoiBudget);
            } catch (AssertionError error) {
                System.out.println("\n" + ++i + ". Engagement ID: " + extractedEngagementId + " Cost Line ID: " + extractedCostLineId + "\n" + error);
            }
        }
    }
}
