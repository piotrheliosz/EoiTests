package FunctionalityTests;

import PageObjectPattern.EngagementsPage;
import PageObjectPattern.EngagementsPerformancePage;
import PageObjectPattern.MainPage;
import SetUp.Scenario;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertTrue;

public class AssignPartnerToEngagement extends Scenario {

    @DataProvider(name = "partnersNames")
    public static Object[] partnerToAdd() {
        return new Object[][]{{"TAURON Ciepło S.A."}, {"Tauron Dystrybucja S.A."},
                {"TAURON Sprzedaż spółka z ograniczoną odpowiedzialnością"}, {"Tauron Wytwarzanie S.A."}};
    }

    @Test(dataProvider = "partnersNames")
    public void partnerShouldBeAssignedToEngagement(String partnerToAdd) throws IOException {
        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        EngagementsPage engagementsPage = new EngagementsPage(driver);
        engagementsPage.goToEngagementsPerformanceSection();

        EngagementsPerformancePage engagementsPerformancePage = new EngagementsPerformancePage(driver);
        engagementsPerformancePage.clickPartnersTab();
        engagementsPerformancePage.clickAddNewPartnerButton();
        engagementsPerformancePage.addNewPartnerFromDropdown(partnerToAdd);
        engagementsPerformancePage.selectRoleType("Lead Partner");
        engagementsPerformancePage.saveAddPartner();

        assertTrue(engagementsPerformancePage.partnerIsDisplayedOnGrid(partnerToAdd));

        mainPage.clickHomeButton();
    }
}

