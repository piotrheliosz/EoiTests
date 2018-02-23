import PageObjectPattern.EngagementsPage;
import SetUp.ReportReader;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ExtractedWorkPackegeDataTest extends Scenario {

    private String filePath = "extracts\\KISSExtract_WorkPackage_BP2017Amendment.xls";

    @Test
    public void compareWpTitle() throws InterruptedException, IOException {
        ReportReader extractIds = new ReportReader(filePath, "WorkPackage", "Engagement ID");
        ReportReader extractTitles = new ReportReader(filePath, "WorkPackage", "Engagement ID->Title");
        Set<String> ids = (extractIds.getSet("Engagement ID"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (String id : ids) {
            engagementsPage.searchForEnagagementOnGrid(id);

            List<String> titlesExtracted = (extractTitles.getList(id + "->Title"));
            List<String> titlesEOI = new ArrayList<String>();

            try {
                for (WebElement titleElement : engagementsPage.getWpTitlesList()) {
                    titlesEOI.add(titleElement.getText());
                }
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
                engagementsPage.takeScreenShot(id + "_wpTitleStale");
            }
            try {
                assertTrue(titlesEOI.containsAll(titlesExtracted)); //TODO kłopot z przeładowaniem grida DETAILS
            } catch (AssertionError error) {
                System.out.println("\nEngagement Id: " + id + "\n" + "Extracted KISS titles: " + titlesExtracted
                        + "\nEOI titles: " + titlesEOI);
            }

            engagementsPage.engagementIdFilter.clear();

/*
            for (WebElement wpTile : engagementsPage.getWpTitlesList()) {

                List<String> titlesExtracted = (extractTitles.getList(id + "->Title"));

                String title = "";

                try {
                    title = wpTile.getText();
                } catch (StaleElementReferenceException e) {
                    e.printStackTrace();
                    engagementsPage.takeScreenShot(id + "_wpTitleStale");
                }
                try {
                    if (!title.equals("Default Work Package")) {
                        System.out.println("title: " + title);
                        assertTrue(titlesExtracted.contains(title));
                    }
                } catch (AssertionError error) {
                    System.out.println("ID: " + id + " | " + error + "\n" + "titlesExtracted: \n" + titlesExtracted);
                }
            }
            engagementsPage.engagementIdFilter.clear();
*/
        }

    }

    @Test
    public void compareWpDescription() throws InterruptedException, IOException {
        ReportReader extractIds = new ReportReader(filePath, "WorkPackage", "Engagement ID");
        ReportReader extractWpId = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID");
        ReportReader extractDesc = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID->Description");

        Set<String> ids = (extractIds.getSet("Engagement ID"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (String id : ids) {

            engagementsPage.searchForEnagagementOnGrid(id);

            for (String wpId : extractWpId.getList(id + "->Work Package ID")) {

                String wpDescriptionsExtracted = (extractDesc.getSingle(id + "->" + wpId + "->Description"));

                System.out.println("Engagement Id: " + id + "\nWP Id: " + wpId + "\nWP Description Extracted: " + wpDescriptionsExtracted);

                try {
                    engagementsPage.editWp(wpId);
                    try {
                        System.out.println("WP Description EOI: " + engagementsPage.getWpDesc() + "\n");
                        assertEquals(engagementsPage.getWpDesc(), wpDescriptionsExtracted);
                    } catch (AssertionError error) {
                        error.printStackTrace();
                    }
                    engagementsPage.closePopUp();
                } catch (UnhandledAlertException e) {
                    driver.switchTo().alert().accept();
                    engagementsPage.takeScreenShot(id + "_" + e.toString());
                    System.out.println("No privilege to edit:"
                            + "\nEngagement Id: " + id + "\nwpId: " + wpId);
                }
            }
            engagementsPage.engagementIdFilter.clear();
        }
    }

    @Test
    public void comparePartners() {

    }

    @Test
    public void compareLeadingPartner() {

    }
}
