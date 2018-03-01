import PageObjectPattern.EngagementsPage;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
            engagementsPage.searchForEnagagementOnGridById(id);
            engagementsPage.selectFoundEngagement();

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
                assertEquals(titlesEOI.size(), titlesExtracted.size());
                assertTrue(titlesEOI.containsAll(titlesExtracted));
            } catch (AssertionError error) {
                System.out.println(error + "\nEngagement Id: " + id
                        + "\nExtracted KISS titles " + titlesExtracted.size() + ": " + titlesExtracted
                        + "\nEOI titles: " + titlesEOI.size() + ": " + titlesEOI + "\n");
            }

            engagementsPage.engagementIdFilter.clear();
        }

    }

    @Test
    public void compareWpDescription() throws InterruptedException, IOException {
        ReportReader extractIds = new ReportReader(filePath, "WorkPackage", "Engagement ID");
        ReportReader extractWpId = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID");
        ReportReader extractDesc = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID->Description");

        Set<String> engagementsIds = (extractIds.getSet("Engagement ID"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (String engagementId : engagementsIds) {

            engagementsPage.searchForEnagagementOnGridById(engagementId);
            engagementsPage.selectFoundEngagement();

            for (String wpId : extractWpId.getList(engagementId + "->Work Package ID")) {

                String wpDescriptionsExtracted = (extractDesc.getSingle(engagementId + "->" + wpId + "->Description"));

                try {
                    try {
                        engagementsPage.editWpFoundById(wpId);
                    } catch (NullPointerException e) {
                        engagementsPage.takeScreenShot(engagementId + "_null");
                        e.printStackTrace();
                    }
                    try {
                        assertEquals(engagementsPage.getWpDesc(), wpDescriptionsExtracted);
                    } catch (AssertionError error) {
                        System.out.println("Engagement Id: " + engagementId + "\nWP Id: " + wpId + "\nWP Description Extracted: " + wpDescriptionsExtracted);
                        System.out.println("WP Description EOI: " + engagementsPage.getWpDesc() + "\n");
                    }
                    engagementsPage.closePopUp();
                } catch (UnhandledAlertException e) {
                    System.out.println("Alert: " + driver.switchTo().alert().getText());
                    driver.switchTo().alert().accept();
                    System.out.println("No privilege to edit:"
                            + "\nEngagement Id: " + engagementId + "\nwpId: " + wpId);
                }
                engagementsPage.engagementIdFilter.clear();
            }
        }
    }

    @Test
    public void comparePartners() throws InterruptedException, IOException {

        ReportReader extractIds = new ReportReader(filePath, "WorkPackage", "Engagement ID");
        ReportReader extractWpId = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID");
        ReportReader extractDesc = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID->Partners");

        Set<String> engagementsIds = (extractIds.getSet("Engagement ID"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (String engagementId : engagementsIds) {

            engagementsPage.searchForEnagagementOnGridById(engagementId);
            engagementsPage.selectFoundEngagement();

            for (String wpId : extractWpId.getList(engagementId + "->Work Package ID")) {

                String extractedWpPartners = (extractDesc.getSingle(engagementId + "->" + wpId + "->Partners"));
                String[] extractedWpPartnersSplit = extractedWpPartners.split(",");
                List<String> extractedWpPartnersList = new ArrayList<String>();
                extractedWpPartnersList.addAll(Arrays.asList(extractedWpPartnersSplit));

                try {
                    try {
                        engagementsPage.editWpFoundById(wpId);
                    } catch (NullPointerException e) {
                        engagementsPage.takeScreenShot(engagementId + "_null");
                        e.printStackTrace();
                    }
                    try {
                        assertTrue(engagementsPage.getPartnerListsInWp().containsAll(extractedWpPartnersList));
                    } catch (AssertionError error) {
                        if (!engagementsPage.getPartnerListsInWp().isEmpty() && !extractedWpPartnersList.isEmpty()) {
                            engagementsPage.takeScreenShot(engagementId + "_getPartnerListsInWp");
                            System.out.println(error + "\nEngagement Id: " + engagementId + "\nwpId: " + wpId
                                    + "\nengagementsPage.getPartnerListsInWp(): " + engagementsPage.getPartnerListsInWp()
                                    + "\nextractedWpPartnersList: " + extractedWpPartnersList + "\n");
                        }
                    }
                    engagementsPage.closePopUp();
                } catch (UnhandledAlertException e) {
                    //System.out.println("Alert: " + driver.switchTo().alert().getText());
                    driver.switchTo().alert().accept();
                    //System.out.println("Engagement Id: " + engagementId + "\nwpId: " + wpId + "\n");
                }
                engagementsPage.engagementIdFilter.clear();
            }
        }

    }

    @Test
    public void compareLeadingPartner() throws IOException, InterruptedException {

        ReportReader extractIds = new ReportReader(filePath, "WorkPackage", "Engagement ID");
        Set<String> ids = (extractIds.getSet("Engagement ID"));
        for (String engagementId : ids) {

            EngagementsPage engagementsPage = new EngagementsPage(driver);
            engagementsPage.searchForEnagagementOnGridById(engagementId);
            engagementsPage.selectFoundEngagement();

            ReportReader extractedWpIds = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID");
            List<String> wpIdsList = extractedWpIds.getList(engagementId + "->Work Package ID");
            for (String wpId : wpIdsList) {

                System.out.println("Engagement ID: " + engagementId + " | WP ID: " + wpId);

                String wpLeadingPartner = engagementsPage.getWpLeadingPartner(wpId);
                String[] wpLeadingPartnerSplit = wpLeadingPartner.split("\\(");

                String wpLeadingPartnerId = "";
                try {
                    wpLeadingPartnerId = wpLeadingPartnerSplit[wpLeadingPartnerSplit.length - 1].substring(0, wpLeadingPartnerSplit[wpLeadingPartnerSplit.length - 1].length() - 1);
                } catch (StringIndexOutOfBoundsException e) {
                    wpLeadingPartnerId = "";
                    //engagementsPage.takeScreenShot(engagementId + "_StringIndexOutOfBoundsException");
                }

                ReportReader extractedLeadingPartners = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID->Lead Partner");
                String extractedLeadingPartnerId = extractedLeadingPartners.getSingle(engagementId + "->" + wpId + "->Lead Partner");

                try {
                    assertEquals(wpLeadingPartnerId, extractedLeadingPartnerId);
                } catch (AssertionError error) {
                    error.printStackTrace();
                }
                engagementsPage.engagementIdFilter.clear();
            }
        }
    }
}