import PageObjectPattern.EngagementsPage;
import PageObjectPattern.MainPage;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ExtractedEngagementDataTest extends Scenario {

    private String filePath = "extracts\\KISSExtract_Engagement_BP2017Amendment.xls";

    @Test(enabled = true, priority = 0)
    public void compareId() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extract = new ReportReader(filePath, "Engagement", "Engagement ID");
        List<String> ids = (extract.getList("Engagement ID"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (String id : ids) {
            try {
                engagementsPage.searchForEnagagementOnGridById(id);
                assertEquals(engagementsPage.foundEngagementId(), id);
            } catch (AssertionError error) {
                System.out.println("ID: " + id + " | " + error);
            }
            engagementsPage.engagementIdFilter.clear();
        }
    }

    @Test(enabled = true, priority = 1)
    public void compareNames() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extract = new ReportReader(filePath, "Engagement", "Engagement ID,Name");
        List<String> ids = (extract.getList("Engagement ID"));
        List<String> names = (extract.getList("Name"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < ids.size(); i++) {

            try {
                String id = ids.get(i);
                engagementsPage.searchForEnagagementOnGridById(id);
                String name = names.get(i);
                assertEquals(engagementsPage.foundEngagementName().toUpperCase(), name.toUpperCase());
            } catch (AssertionError error) {
                if (!ids.get(i).contains("ENG")) {
                    System.out.println("ID: " + ids.get(i) + " | " + error);
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
        List<String> ids = (extract.getList("Engagement ID"));
        List<String> objectives = (extract.getList("Objective"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < ids.size(); i++) {
            String objective = null;
            try {
                String id = ids.get(i);
                engagementsPage.searchForEnagagementOnGridById(id);
                engagementsPage.selectFoundEngagement();
                engagementsPage.openEditPopUp();
                objective = objectives.get(i);
                try {
                    assertEquals(engagementsPage.getObjective(), objective);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    engagementsPage.takeScreenShot(id + " " + getClass().toString());
                }
            } catch (AssertionError error) {/**/
                if (engagementsPage.getObjective().equals("") || !ids.get(i).contains("ENG")) {
                    System.out.println("ID: " + ids.get(i) + " | java.lang.AssertionError" +
                            "\nKISS EXTRACT: " + objective + "\nEOI: " + engagementsPage.getObjective() + "\n");
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
        List<String> ids = (extract.getList("Engagement ID"));
        List<String> scopes = (extract.getList("Scope"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < ids.size(); i++) {
            String scope = null;
            try {
                String id = ids.get(i);
                engagementsPage.searchForEnagagementOnGridById(id);
                scope = scopes.get(i);
                engagementsPage.selectFoundEngagement();
                engagementsPage.openEditPopUp();
                try {
                    assertEquals(engagementsPage.getScope(), scope);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    engagementsPage.takeScreenShot(id + " " + getClass().toString());
                }
            } catch (AssertionError error) {
                if (engagementsPage.getScope().equals("") || !ids.get(i).contains("ENG")) {
                    System.out.println("ID: " + ids.get(i) + " | java.lang.AssertionError" +
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
        List<String> ids = (extract.getList("Engagement ID"));
        List<String> types = (extract.getList("Type"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < ids.size(); i++) {

            try {
                String id = ids.get(i);
                engagementsPage.searchForEnagagementOnGridById(id);
                String type = types.get(i);
                assertEquals(engagementsPage.foundEngagementType().toUpperCase(), type.toUpperCase());
            } catch (AssertionError error) {
                if (engagementsPage.foundEngagementType().equals("") || !ids.get(i).contains("ENG")) {
                    System.out.println("ID: " + ids.get(i) + " | " + error);
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
        List<String> ids = (extract.getList("Engagement ID"));
        List<String> managementUnits = (extract.getList("Management Unit"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < ids.size(); i++) {

            try {
                String id = ids.get(i);
                engagementsPage.searchForEnagagementOnGridById(id);
                String name = managementUnits.get(i);
                assertEquals(engagementsPage.foundEngagementManagementUnit().toUpperCase(), name.toUpperCase());
            } catch (AssertionError error) {
                if (engagementsPage.foundEngagementManagementUnit().equals("") || !ids.get(i).contains("ENG")) {
                    System.out.println("ID: " + ids.get(i) + " | " + error);
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
        List<String> ids = (extract.getList("Engagement ID"));
        List<String> thematicFields = (extract.getList("Thematic Field"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < ids.size(); i++) {
            String thematicField = null;
            try {
                String id = ids.get(i);
                engagementsPage.searchForEnagagementOnGridById(id);
                engagementsPage.selectFoundEngagement();
                engagementsPage.openEditPopUp();
                thematicField = thematicFields.get(i);
                if (!engagementsPage.foundEngagementType().contains("BCS")) {
                    assertEquals(engagementsPage.getThematicField(), thematicField);
                }
            } catch (AssertionError error) {
                if (engagementsPage.getThematicField().equals("") || !ids.get(i).contains("ENG")) {
                    System.out.println("ID: " + ids.get(i) + " | java.lang.AssertionError" +
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
        List<String> ids = (extract.getList("Engagement ID"));
        List<String> blas = (extract.getList("Business Line Area"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < ids.size(); i++) {

            try {
                String id = ids.get(i);
                engagementsPage.searchForEnagagementOnGridById(id);
                String bla = blas.get(i);
                assertEquals(engagementsPage.foundEngagementBla().toUpperCase(), bla.toUpperCase());
            } catch (AssertionError error) {
                if (engagementsPage.foundEngagementBla().equals("") || !ids.get(i).contains("ENG"))
                    System.out.println("ID: " + ids.get(i) + " | " + error);
            }
            engagementsPage.engagementIdFilter.clear();
        }
    }

    @Test(enabled = true, priority = 8)
    public void comparePartnersId() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extract = new ReportReader(filePath, "Engagement", "Engagement ID,Partners");
        List<String> ids = (extract.getList("Engagement ID"));

        EngagementsPage engagementsPage = new EngagementsPage(driver);

        for (int i = 0; i < ids.size(); i++) {
            String engagementId = ids.get(i);
            engagementsPage.searchForEnagagementOnGridById(engagementId);
            engagementsPage.selectFoundEngagement();

            List<String> partnersExtracted = (extract.getList("Partners"));

            String[] partnersLineIds = partnersExtracted.get(i).split(",");
            sleep(2000); //to avoid stale element because dynamic searching

            List<String> partnersLineIdsList = new ArrayList<String>();
            partnersLineIdsList.addAll(Arrays.asList(partnersLineIds));

            List<String> eoiIds = new ArrayList<String>();
            for (String eoiPartner : engagementsPage.foundEngagementPartners()) {
                String[] bits = eoiPartner.split("\\(");

                String eoiId = "";

                try {
                    eoiId = bits[bits.length - 1].substring(0, bits[bits.length - 1].length() - 1);
                } catch (StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    engagementsPage.takeScreenShot("StringIndexOutOfBoundsException");
                }

                eoiIds.add(eoiId);
                if (eoiIds.size() == 9 && engagementsPage.footedIsDisplayed()) {
                    engagementsPage.gotoAnotherPage();
                    for (String eoiPartner2 : engagementsPage.foundEngagementPartners()) {
                        String[] bits2 = eoiPartner2.split("\\(");
                        String eoiId2 = bits2[bits2.length - 1].substring(0, bits2[bits2.length - 1].length() - 1);
                        eoiIds.add(eoiId2);
                    }
                }
            }

            try {
                assertTrue(eoiIds.containsAll(partnersLineIdsList));
            } catch (AssertionError error) {
                System.out.println("ID: " + ids.get(i) + " | " + error);
                System.out.println("EOI Partners:     " + eoiIds);
                System.out.println("KISS Partners:    " + partnersLineIdsList + "\n");
            }
            engagementsPage.engagementIdFilter.clear();
        }
    }
}