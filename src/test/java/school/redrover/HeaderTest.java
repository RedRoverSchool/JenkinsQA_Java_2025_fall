package school.redrover;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;

public class HeaderTest extends BaseTest{

        @Test
        public void testSearchResultsAppear() {
            final String[] listOfTypes = new String[]{"Folder", "Freestyle project", "Pipeline", "Multi-configuration project", "Multibranch Pipeline", "Organization Folder"};
            HomePage homePage = new HomePage(getDriver());
            for(String title : listOfTypes) {
                homePage.clickNewItemOnLeftMenu()
                        .sendName(title)
                        .selectItemTypeAndSubmitAndGoHome(title);
            }
            List<String> results = homePage.clickSearchButton()
                    .searchFor("F")
                    .searchResults();

            Assert.assertEquals(results.size(), 4);
        }
    }
