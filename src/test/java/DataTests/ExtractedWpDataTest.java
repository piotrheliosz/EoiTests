package DataTests;

import PageObjectPattern.EngagementsPage;
import Reports.ReportReader;
import SetUp.Scenario;
import org.openqa.selenium.UnhandledAlertException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class ExtractedWpDataTest extends Scenario {

    private String filePath = "extracts\\KISSExtract_WorkPackage_BP2017Amendment.xls";

    @Test(enabled = true, priority = 0)
    public void compareWpTitle() throws InterruptedException, IOException {
        ReportReader extractTitles = new ReportReader(filePath, "WorkPackage", "Engagement ID->Title");

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (String id : engagementsPage.getWpEngagementsIdsSet(filePath)) {
            engagementsPage.searchForEngagementOnGridById(id);
            engagementsPage.selectFoundEngagement();

            List<String> titlesExtracted = new ArrayList<String>();
            for (String titleExtracted : extractTitles.getList(id + "->Title")) {
                titlesExtracted.add(titleExtracted.trim().replaceAll(" +", " "));
            }

            try {
                assertEquals(engagementsPage.getWpTitlesList().size(), titlesExtracted.size());
                assertTrue(engagementsPage.getWpTitlesList().containsAll(titlesExtracted));
            } catch (AssertionError error) {
                System.out.println(error + "\nEngagement Id: " + id
                        + "\nExtracted KISS titles - number: " + titlesExtracted.size() + ": " + titlesExtracted
                        + "\nEOI titles - number: " + engagementsPage.getWpTitlesList().size()
                        + ": " + engagementsPage.getWpTitlesList() + "\n");
            }
            engagementsPage.clearIdFilter();
        }
    }

    @Test(enabled = true, priority = 1)
    public void compareWpDescription() throws InterruptedException, IOException {
        ReportReader extractWpIds = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID");
        ReportReader extractDesc = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID->Description");

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (String engagementId : engagementsPage.getEngagementsIdsList(filePath)) {
            engagementsPage.searchForEngagementOnGridById(engagementId);
            engagementsPage.selectFoundEngagement();

            for (String wpId : extractWpIds.getList(engagementId + "->Work Package ID")) {

                String wpDescriptionExtracted = (extractDesc.getSingle(engagementId + "->" + wpId + "->Description"));

                try {
                    engagementsPage.clickEditWpFoundById(wpId);
                    try {
                        assertEquals(engagementsPage.getWpDescription().trim().replaceAll(" +", " "),
                                wpDescriptionExtracted.trim().replaceAll(" +", " "));
                    } catch (AssertionError error) {
                        System.out.println("Engagement Id: " + engagementId
                                + "\nWP Id: " + wpId
                                + "\nWP Description Extracted: " + wpDescriptionExtracted
                                + "\nWP Description EOI: " + engagementsPage.getWpDescription());
                    }
                    engagementsPage.closePopUp();
                } catch (UnhandledAlertException e) {
                    System.out.println(driver.switchTo().alert().getText());
                    driver.switchTo().alert().accept();
                }
            }
            engagementsPage.clearIdFilter();
        }
    }

    @Test(enabled = true, priority = 2)
    public void compareWpPartners() throws InterruptedException, IOException {
        ReportReader extractWpIds = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID");
        ReportReader extractWpPartners = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID->Partners");

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (String engagementId : engagementsPage.getEngagementsIdsList(filePath)) {

            engagementsPage.searchForEngagementOnGridById(engagementId);
            engagementsPage.selectFoundEngagement();

            for (String wpId : extractWpIds.getList(engagementId + "->Work Package ID")) {

                String extractedWpPartners = (extractWpPartners.getSingle(engagementId + "->" + wpId + "->Partners"));
                String[] extractedWpPartnersSplit = extractedWpPartners.split(",");
                List<String> extractedWpPartnersList = new ArrayList<String>();
                extractedWpPartnersList.addAll(Arrays.asList(extractedWpPartnersSplit));

                try {
                    try {
                        engagementsPage.clickEditWpFoundById(wpId);
                    } catch (Exception e) {
                        engagementsPage.takeScreenShot(engagementId + "_editWpFoundById");
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
                    driver.switchTo().alert().accept();
                }
                engagementsPage.clearIdFilter();
            }
        }
    }

    @Test(enabled = true, priority = 3)
    public void compareLeadingPartner() throws IOException, InterruptedException {
        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (String engagementId : engagementsPage.getWpEngagementsIdsSet(filePath)) {

            engagementsPage.searchForEngagementOnGridById(engagementId);
            engagementsPage.selectFoundEngagement();

            ReportReader extractedWpIds = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID");
            List<String> wpIdsList = extractedWpIds.getList(engagementId + "->Work Package ID");
            for (String wpId : wpIdsList) {

                String wpLeadingPartner = engagementsPage.getWpLeadingPartner(wpId);
                String[] wpLeadingPartnerSplit = wpLeadingPartner.split("\\(");

                String wpLeadingPartnerId = "";
                try {
                    wpLeadingPartnerId = wpLeadingPartnerSplit[wpLeadingPartnerSplit.length - 1].substring(0, wpLeadingPartnerSplit[wpLeadingPartnerSplit.length - 1].length() - 1);
                } catch (StringIndexOutOfBoundsException e) {
                    wpLeadingPartnerId = "";
                }

                ReportReader extractedLeadingPartners = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID->Lead Partner");
                String extractedLeadingPartnerId = extractedLeadingPartners.getSingle(engagementId + "->" + wpId + "->Lead Partner");

                try {
                    assertEquals(wpLeadingPartnerId, extractedLeadingPartnerId);
                } catch (AssertionError error) {
                    System.out.println(error + "\nEngagement Id: " + engagementId + "\nWork package Id: " + wpId
                            + "\nextractedLeadingPartnerId: " + extractedLeadingPartnerId
                            + "\nwpLeadingPartnerId: " + wpLeadingPartnerId);
                }
                engagementsPage.clearIdFilter();
            }
        }
    }
}