package FunctionalityTests;

import PageObjectPattern.EngagementsPage;
import PageObjectPattern.EngagementsPerformancePage;
import PageObjectPattern.MainPage;
import SetUp.Scenario;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertTrue;

public class AssignAndRemovePartnerToEngagement extends Scenario {

    private String engagementName = "ba2f6d06-fd60-4de5-9e06-1a05bbcb6233";

    @DataProvider(name = "partnersNames")
    public static Object[] partnerToAdd() {
        return new Object[][]{{"TAURON Ciepło S.A."}, {"Tauron Dystrybucja S.A."},
                {"TAURON Sprzedaż spółka z ograniczoną odpowiedzialnością"}, {"Tauron Wytwarzanie S.A."}};
    }

    @Test(dataProvider = "partnersNames")
    public void partnersShouldBeAssignedToEngagement(String partnerToAdd) throws IOException {

        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        EngagementsPage engagementsPage = new EngagementsPage(driver);
        engagementsPage.goToEngagementPerformancePageByJs(engagementName);

        EngagementsPerformancePage engagementsPerformancePage = new EngagementsPerformancePage(driver);
        engagementsPerformancePage.clickPartnersTab();
        engagementsPerformancePage.clickAddNewPartnerButton();
        engagementsPerformancePage.addNewPartnerFromDropdown(partnerToAdd);
        engagementsPerformancePage.selectRoleType("Lead Partner");
        engagementsPerformancePage.saveAddPartner();

        assertTrue(engagementsPerformancePage.partnerIsDisplayedOnGrid(partnerToAdd));

        mainPage.clickHomeButton();
    }

    @Test
    public void partnersShouldBeRemovedFromEngagement() {

        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        EngagementsPage engagementsPage = new EngagementsPage(driver);
        engagementsPage.goToEngagementPerformancePageByJs(engagementName);

        EngagementsPerformancePage engagementsPerformancePage = new EngagementsPerformancePage(driver);
        engagementsPerformancePage.clickPartnersTab();
        for (String partnerName : engagementsPerformancePage.getPartnersNameList()) {
            engagementsPerformancePage.findPartnerOnGrid(partnerName).click();
            engagementsPerformancePage.removePartnerFromEngagement();
        }
        engagementsPage.goToEngagementPerformancePageByJs(engagementName);

        assertTrue(engagementsPerformancePage.getPartnersList().isEmpty());
    }
}