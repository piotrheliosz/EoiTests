package FunctionalityTests;

import PageObjectPattern.EngagementsPage;
import PageObjectPattern.EngagementsPerformancePage;
import PageObjectPattern.MainPage;
import SetUp.Scenario;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static PageObjectPattern.Page.existingEngagementName;
import static org.testng.AssertJUnit.assertTrue;

public class AssignAndRemovePartnerToEngagement extends Scenario {

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
        engagementsPage.goToEngagementPerformancePageByJs(existingEngagementName);

        EngagementsPerformancePage engagementsPerformancePage = new EngagementsPerformancePage(driver);
        engagementsPerformancePage.clickPartnersTab();
        engagementsPerformancePage.clickAddNewPartnerButton();
        engagementsPerformancePage.selectNewPartnerFromDropdown(partnerToAdd);
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
        engagementsPage.goToEngagementPerformancePageByJs(existingEngagementName);

        EngagementsPerformancePage engagementsPerformancePage = new EngagementsPerformancePage(driver);
        engagementsPerformancePage.clickPartnersTab();
        for (String partnerName : engagementsPerformancePage.getPartnersNameList()) {
            engagementsPerformancePage.findPartnerOnGrid(partnerName).click();
            engagementsPerformancePage.removePartnerFromEngagement();
        }
        engagementsPage.goToEngagementPerformancePageByJs(existingEngagementName);

        assertTrue(engagementsPerformancePage.getPartnersList().isEmpty());
    }
}