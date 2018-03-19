import PageObjectPattern.EngagementsPage;
import PageObjectPattern.MainPage;
import org.openqa.selenium.UnhandledAlertException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class WpEditIsEnable extends Scenario {


    @Test
    public void wpEditShouldBeEnable() throws InterruptedException, IOException {

        String filePath = "extracts\\KISSExtract_WorkPackage_BP2017Amendment.xls";
        int numberOfAlerts = 0;

        MainPage mainPage = new MainPage(driver);
        mainPage.navigateToEngagementsSection();

        ReportReader extractIds = new ReportReader(filePath, "WorkPackage", "Engagement ID");
        Set<String> engagementsIds = (extractIds.getSet("Engagement ID"));
        for (String engagementId : engagementsIds) {

            EngagementsPage engagementsPage = new EngagementsPage(driver);
            engagementsPage.searchForEngagementOnGridById(engagementId);
            engagementsPage.selectFoundEngagement();

            ReportReader extractWpId = new ReportReader(filePath, "WorkPackage", "Engagement ID->Work Package ID");
            List<String> wpIdList = extractWpId.getList(engagementId + "->Work Package ID");
            for (String wpId : wpIdList) {
                try {
                    engagementsPage.editWpFoundById(wpId);
                    engagementsPage.closePopUp();
                } catch (Exception e) {
                    e.getStackTrace();
                    System.out.println(++numberOfAlerts + ". ALERT: " + driver.switchTo().alert().getText());
                    driver.switchTo().alert().accept();
                    System.out.println("Engagement ID: " + engagementId + "\nWP ID: " + wpId + "\n");
                }
                engagementsPage.engagementIdFilter.clear();
            }
        }
    }
}
