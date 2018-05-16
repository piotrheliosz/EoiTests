package FunctionalityTests;

import PageObjectPattern.EngagementsPerformancePage;
import PageObjectPattern.HomePage;
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

        HomePage homePage = new HomePage(driver);
        homePage.navigateToEngagementsSection();
        homePage.goToEngagementPerformancePageByJs(existingEngagementName);

        EngagementsPerformancePage engagementsPerformancePage = new EngagementsPerformancePage(driver);
        engagementsPerformancePage.clickPartnersTab();
        engagementsPerformancePage.clickAddNewPartnerButton();
        engagementsPerformancePage.selectNewPartnerFromDropdown(partnerToAdd);
        engagementsPerformancePage.selectRoleType("Lead Partner");
        engagementsPerformancePage.saveAddPartner();

        assertTrue(engagementsPerformancePage.partnerIsDisplayedOnGrid(partnerToAdd));

        homePage.clickHomeButton();
    }

    @Test
    public void partnersShouldBeRemovedFromEngagement() {

        HomePage homePage = new HomePage(driver);
        homePage.navigateToEngagementsSection();
        homePage.goToEngagementPerformancePageByJs(existingEngagementName);

        EngagementsPerformancePage engagementsPerformancePage = new EngagementsPerformancePage(driver);
        engagementsPerformancePage.clickPartnersTab();
        for (String partnerName : engagementsPerformancePage.getPartnersNameList()) {
            engagementsPerformancePage.findPartnerOnGrid(partnerName).click();
            engagementsPerformancePage.removePartnerFromEngagement();
        }

        homePage.goToEngagementPerformancePageByJs(existingEngagementName);

        assertTrue(engagementsPerformancePage.getPartnersList().isEmpty());
    }
}