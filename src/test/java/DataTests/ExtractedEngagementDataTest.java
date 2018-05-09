package DataTests;

import PageObjectPattern.EngagementsPage;
import PageObjectPattern.MainPage;
import Reports.ReportReader;
import SetUp.Scenario;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ExtractedEngagementDataTest extends Scenario {

    //extracts/KISSExtract_Property_ALL_BP2018.xls
    //private String filePath = "extracts\\KISSExtract_Engagement_BP2017Amendment.xls";
    private String filePath = "extracts\\KISSExtract_Property_ALL_BP2018.xls";

    @Test(enabled = true, priority = 0)
    public void compareId() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (String id : engagementsPage.getEngagementsIdsSetFromFile(filePath, "Personnel Property")) {
            try {
                engagementsPage.searchForEngagementOnGridById(id);
                assertEquals(engagementsPage.foundEngagementId(), id);
            } catch (AssertionError error) {
                System.out.println("ID: " + id + " | " + error);
            }
            engagementsPage.engagementIdFilter.clear();
        }
        int i = 0;
        String s =  String.valueOf(i * i);
        int ii = Integer.valueOf(s) * i;
    }

    @Test(enabled = true, priority = 1)
    public void compareNames() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extract = new ReportReader(filePath, "Engagement", "Engagement ID,Name");
        List<String> engagementsIds = mainPage.getEngagementsIdsListFromFile(filePath);
        List<String> engagementsNames = (extract.getList("Name"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        int ii = 0;
        for (int i = 0; i < engagementsIds.size(); i++) {

            try {
                String id = engagementsIds.get(i);
                engagementsPage.searchForEngagementOnGridById(id);
                String name = engagementsNames.get(i);
                assertEquals(engagementsPage.foundEngagementName().toUpperCase(), name.toUpperCase());
            } catch (AssertionError error) {
                if (!engagementsIds.get(i).contains("ENG")) {
                    System.out.println(++ii + ". ID: " + engagementsIds.get(i) + " | " + error);
                }
            }
            engagementsPage.engagementIdFilter.clear();
        }
    }

    @Test(enabled = true, priority = 2)
    public void compareObjective() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extract = new ReportReader(filePath, "Engagement", "Engagement ID,Objective");
        List<String> engagementsIds = (extract.getList("Engagement ID"));
        List<String> engagementsObjectives = (extract.getList("Objective"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < engagementsIds.size(); i++) {
            String objective = "";
            String id = engagementsIds.get(i);
            engagementsPage.searchForEngagementOnGridById(id);
            engagementsPage.selectFoundEngagement();
            engagementsPage.openEditPopUp();
            objective = engagementsObjectives.get(i);
            try {
                try {
                    assertEquals(engagementsPage.getObjective(), objective);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    engagementsPage.takeScreenShot(id + " " + getClass().toString());
                }
            } catch (AssertionError error) {
                if (engagementsPage.getObjective().equals("") || !engagementsIds.get(i).contains("ENG")) {
                    System.out.println("ID: " + engagementsIds.get(i)
                            + "\nKISS EXTRACT: " + objective + "\nEOI: " + engagementsPage.getObjective()
                    );
                }
            }

            engagementsPage.closePopUp();
            engagementsPage.engagementIdFilter.clear();
        }
    }

    @Test(enabled = true, priority = 3)
    public void compareScope() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extract = new ReportReader(filePath, "Engagement", "Engagement ID,Scope");
        List<String> engagementsIds = (extract.getList("Engagement ID"));
        List<String> engagementsScopes = (extract.getList("Scope"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < engagementsIds.size(); i++) {
            String scope = null;
            try {
                String id = engagementsIds.get(i);
                engagementsPage.searchForEngagementOnGridById(id);
                scope = engagementsScopes.get(i);
                engagementsPage.selectFoundEngagement();
                engagementsPage.openEditPopUp();
                try {
                    assertEquals(engagementsPage.getScope(), scope);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    engagementsPage.takeScreenShot(id + " " + getClass().toString());
                }
            } catch (AssertionError error) {
                if (engagementsPage.getScope().equals("") || !engagementsIds.get(i).contains("ENG")) {
                    System.out.println("ID: " + engagementsIds.get(i) + " | java.lang.AssertionError" +
                            "\nKISS EXTRACT: " + scope + "\nEOI: " + engagementsPage.getScope() + "\n");
                }
            }
            engagementsPage.closePopUp();
            engagementsPage.engagementIdFilter.clear();
        }
    }

    @Test(enabled = true, priority = 4)
    public void compareTypes() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extract = new ReportReader(filePath, "Engagement", "Engagement ID,Type");
        List<String> engagementsIds = (extract.getList("Engagement ID"));
        List<String> engagementsTypes = (extract.getList("Type"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < engagementsIds.size(); i++) {

            try {
                String id = engagementsIds.get(i);
                engagementsPage.searchForEngagementOnGridById(id);
                String type = engagementsTypes.get(i);
                assertEquals(engagementsPage.foundEngagementType().toUpperCase(), type.toUpperCase());
            } catch (AssertionError error) {
                if (engagementsPage.foundEngagementType().equals("") || !engagementsIds.get(i).contains("ENG")) {
                    System.out.println("ID: " + engagementsIds.get(i) + " | " + error);
                }
            }
            engagementsPage.engagementIdFilter.clear();
        }
    }

    @Test(enabled = true, priority = 5)
    public void compareManagementUnits() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extract = new ReportReader(filePath, "Engagement", "Engagement ID,Management Unit");
        List<String> engagementsIds = (extract.getList("Engagement ID"));
        List<String> engagementsManagementUnits = (extract.getList("Management Unit"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < engagementsIds.size(); i++) {

            try {
                String id = engagementsIds.get(i);
                engagementsPage.searchForEngagementOnGridById(id);
                String name = engagementsManagementUnits.get(i);
                assertEquals(engagementsPage.foundEngagementManagementUnit().toUpperCase(), name.toUpperCase());
            } catch (AssertionError error) {
                if (engagementsPage.foundEngagementManagementUnit().equals("") || !engagementsIds.get(i).contains("ENG")) {
                    System.out.println("ID: " + engagementsIds.get(i) + " | " + error);
                }
            }
            engagementsPage.engagementIdFilter.clear();
        }
    }

    @Test(enabled = true, priority = 6)
    public void compareThematicFields() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extract = new ReportReader(filePath, "Engagement", "Engagement ID,Thematic Field");
        List<String> engagementsIds = (extract.getList("Engagement ID"));
        List<String> engagementsThematicFields = (extract.getList("Thematic Field"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < engagementsIds.size(); i++) {
            String thematicField = null;
            try {
                String id = engagementsIds.get(i);
                engagementsPage.searchForEngagementOnGridById(id);
                engagementsPage.selectFoundEngagement();
                engagementsPage.openEditPopUp();
                thematicField = engagementsThematicFields.get(i);
                //if (!engagementsPage.foundEngagementType().contains("BCS")) {
                assertEquals(engagementsPage.getThematicField(), thematicField);
                //}
            } catch (AssertionError error) {
                if (engagementsPage.getThematicField().equals("") || !engagementsIds.get(i).contains("ENG")) {
                    System.out.println("ID: " + engagementsIds.get(i) + " | java.lang.AssertionError" +
                            "\nKISS EXTRACT: " + thematicField + "\nEOI: " + engagementsPage.getThematicField() + "\n");
                }
            }
            engagementsPage.closePopUp();
            engagementsPage.engagementIdFilter.clear();
        }
    }

    @Test(enabled = true, priority = 7)
    public void compareBla() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extract = new ReportReader(filePath, "Engagement", "Engagement ID,Business Line Area");
        List<String> engagementsIds = (extract.getList("Engagement ID"));
        List<String> engagementsBla = (extract.getList("Business Line Area"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < engagementsIds.size(); i++) {

            try {
                String id = engagementsIds.get(i);
                engagementsPage.searchForEngagementOnGridById(id);
                String bla = engagementsBla.get(i);
                assertEquals(engagementsPage.foundEngagementBla().toUpperCase(), bla.toUpperCase());
            } catch (AssertionError error) {
                if (engagementsPage.foundEngagementBla().equals("") || !engagementsIds.get(i).contains("ENG"))
                    System.out.println("ID: " + engagementsIds.get(i) + " | " + error);
            }
            engagementsPage.engagementIdFilter.clear();
        }
    }

    @Test(enabled = true, priority = 8)
    public void comparePartnersId() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extract = new ReportReader(filePath, "Engagement", "Engagement ID,Partners");
        List<String> engagementsIds = (extract.getList("Engagement ID"));

        for (int i = 0; i < engagementsIds.size(); i++) {

            String engagementId = engagementsIds.get(i);

            EngagementsPage engagementsPage = new EngagementsPage(driver);
            engagementsPage.searchForEngagementOnGridById(engagementId);
            engagementsPage.selectFoundEngagement();

            List<String> partnersExtracted = (extract.getList("Partners"));

            String[] partnersLineIds = partnersExtracted.get(i).split(",");

            List<String> partnersLineIdsList = new ArrayList<String>();
            partnersLineIdsList.addAll(Arrays.asList(partnersLineIds));

            List<String> eoiIds = new ArrayList<String>();
            for (String eoiPartner : engagementsPage.getFoundEngagementPartnersList()) {

                String[] bits = eoiPartner.split("\\(");

                String eoiId = "";

                try {
                    eoiId = bits[bits.length - 1].substring(0, bits[bits.length - 1].length() - 1);
                } catch (StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    engagementsPage.takeScreenShot("StringIndexOutOfBoundsException");
                }

                eoiIds.add(eoiId);

                while (engagementsPage.goToAnotherPageButtonIsDisplayed()) {
                    engagementsPage.goToAnotherPageButton().click();
                    for (String eoiPartner2 : engagementsPage.getFoundEngagementPartnersList()) {
                        String[] bits2 = eoiPartner2.split("\\(");
                        String eoiId2 = bits2[bits2.length - 1].substring(0, bits2[bits2.length - 1].length() - 1);
                        eoiIds.add(eoiId2);
                    }
                }
            }
            try {
                assertTrue(eoiIds.containsAll(partnersLineIdsList));
            } catch (AssertionError error) {
                System.out.println("ID: " + engagementsIds.get(i) + " | " + error);
                System.out.println("EOI Partners:     " + eoiIds);
                System.out.println("KISS Partners:    " + partnersLineIdsList + "\n");
            }
            engagementsPage.engagementIdFilter.clear();
        }
    }
}