package CompareTables;

import PageObjectPattern.Page;
import Reports.ReportReader;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class CompareLineCostsProperty {

    private String extractDataPath = "extracts\\KISSExtract_Cost_Lines.xls";
    private String eoiDataPath = "eoiData\\eoi_property_costLines_result.xls";

    @Test
    public void allCostsLinesPropertyShouldBeExtracted() throws IOException {

        //EXTRACTED DATA
        ReportReader extractCostLineIds = new ReportReader
                (extractDataPath, "Cost Line", "Cost Line ID");
        ReportReader extractEngagementsIds = new ReportReader
                (extractDataPath, "Cost Line", "Cost Line ID->Engagement ID");

        //EOI DATA
        ReportReader eoiCostLineIds = new ReportReader
                (eoiDataPath, "costLines_result", "Cost Line ID");

        List<String> engagementsWithoutPrivilegesToEdit = Page.getListOfEngagementsWithoutPrivilegesToEdit();
        System.out.println("engagementsWithoutPrivilegesToEdit: " + engagementsWithoutPrivilegesToEdit);

        int i = 0;
        List<String> extractCostLineIdsList = extractCostLineIds.getList("Cost Line ID");

        for (String extractedCostLineId : extractCostLineIdsList) {

            String extractedEngagementId = extractEngagementsIds.getSingle(extractedCostLineId + "->Engagement ID");

            if (!engagementsWithoutPrivilegesToEdit.contains(extractedEngagementId)) {

                try {
                    assertTrue(eoiCostLineIds.getList("Cost Line ID").contains(extractedCostLineId));
                } catch (AssertionError error) {
                    System.out.println("\n" + ++i + ". Engagement ID: " + extractedEngagementId + "\nMissing Line Cost: " + extractedCostLineId);
                }
            }
        }
    }
}
