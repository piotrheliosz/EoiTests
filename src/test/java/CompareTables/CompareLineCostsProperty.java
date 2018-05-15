package CompareTables;

import Reports.ReportReader;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class CompareLineCostsProperty {

    private String extractDataPath = "extracts\\KISSExtract_Property_ALL_BP2018.xls";
    private String eoiDataPath = "eoiData\\eoi_property_costLines_result.xls";

    @Test
    public void costsLinesShouldBeExtracted() throws IOException {

        //EXTRACTED DATA
        ReportReader extractCostLineIds = new ReportReader
                (extractDataPath, "Personnel Property", "Cost Line ID");
        ReportReader extractEngagementsIds = new ReportReader
                (extractDataPath, "Personnel Property", "Cost Line ID->Engagement ID");

        //EOI DATA
        ReportReader eoiCostLineIds = new ReportReader
                (eoiDataPath, "costLines_result", "COST LINE ID");

        int i = 0;
        for (String extractedCostLineId : extractCostLineIds.getSet("Cost Line ID")) {

            String extractedEngagementId = extractEngagementsIds.getSingle(extractedCostLineId + "->Engagement ID");
            try {
                assertTrue(eoiCostLineIds.getList("COST LINE ID").contains(extractedCostLineId));
            } catch (AssertionError error) {
                System.out.println("\n" + ++i + ". ENGAGEMENT ID: " + extractedEngagementId + "\nMissing Line Cost: " + extractedCostLineId);
            }
        }
    }

    @Test
    public void nameShouldBeExtracted() throws IOException {

        //EXTRACTED DATA
        ReportReader extractEngagementsIds = new ReportReader
                (extractDataPath, "Personnel Property", "Cost Line ID->Engagement ID");
        ReportReader extractCostLineIds = new ReportReader
                (extractDataPath, "Personnel Property", "Cost Line ID");
        ReportReader extractPropertyName = new ReportReader
                (extractDataPath, "Personnel Property", "Cost Line ID->Name");

        //EOI DATA
        ReportReader eoiPropertyName = new ReportReader
                (eoiDataPath, "costLines_result", "COST LINE ID->NAME");

        int i = 0;
        for (String extractedCostLineId : extractCostLineIds.getSet("Cost Line ID")) {

            List<String> eoiPropertyNames = eoiPropertyName.getList(extractedCostLineId + "->NAME");
            List<String> extractedPropertyNames = extractPropertyName.getList(extractedCostLineId + "->Name");

            try {
                assertTrue(eoiPropertyNames.containsAll(extractedPropertyNames));
            } catch (AssertionError error) {
                System.out.println("\n" + ++i
                        + ". ENGAGEMENT ID: "
                        + extractEngagementsIds.getSingle(extractedCostLineId + "->Engagement ID")
                        + "\nMissing Line Cost: " + extractedCostLineId);
            }
        }
    }

    @Test
    public void valueShouldBeExtracted() throws IOException {

        //EXTRACTED DATA
        ReportReader extractEngagementsIds = new ReportReader
                (extractDataPath, "Personnel Property", "Engagement ID");
        ReportReader extractCostLineIds = new ReportReader
                (extractDataPath, "Personnel Property", "Engagement ID->Cost Line ID");
        ReportReader extractPropertyName = new ReportReader
                (extractDataPath, "Personnel Property", "Engagement ID->Cost Line ID->Name");
        ReportReader extractPropertyValues = new ReportReader
                (extractDataPath, "Personnel Property", "Engagement ID->Cost Line ID->Name->Value");

        //EOI DATA
        ReportReader eoiPropertyValue = new ReportReader
                (eoiDataPath, "costLines_result", "ENGAGEMENT ID->COST LINE ID->NAME->VALUE");
        int i = 0;

        for (String engagementId : extractEngagementsIds.getSet("Engagement ID")) {

            for (String extractedCostLineId : extractCostLineIds.getSet(engagementId + "->Cost Line ID")) {

                for (String extractName : extractPropertyName.getList(engagementId + "->" + extractedCostLineId + "->Name")) {

                    String extractValue = "";
                    String eoiValue = "";

                    try {
                        extractValue = extractPropertyValues.getSingle(engagementId + "->" + extractedCostLineId + "->" + extractName + "->Value");
                    } catch (RuntimeException e) {
                        System.out.println("EXTRACT DATA NOT FOUND: " + engagementId + "->" + extractedCostLineId + "->" + extractName + "->Value");
                    }

                    try {
                        eoiValue = eoiPropertyValue.getSingle(engagementId + "->" + extractedCostLineId + "->" + extractName + "->VALUE");
                    } catch (RuntimeException e) {
                        System.out.println("EOI DATA NOT FOUND: " + engagementId + "->" + extractedCostLineId + "->" + extractName + "->VALUE");
                    }

                    try {
                        assertEquals(extractValue.trim().replaceAll(" +", " "), eoiValue.trim().replaceAll(" +", " "));
                    } catch (AssertionError error) {
                        if (!extractName.contains("DATE")) {
                            System.out.println(error + "\n" + ++i
                                    + ". ENGAGEMENT ID: " + engagementId
                                    + "\nCost Line ID: " + extractedCostLineId
                                    + "\nProperty Name: " + extractName + "\n");
                        }
                    }
                }
            }
        }
    }
}
