package school.redrover.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class GlobalSearchTest extends BaseTest{

        @Test
        public void testSearchResultsAppear() {
            String[] listOfObjects = new String[]{"Folder", "Freestyle project", "Pipeline", "Multi-configuration project", "Multibranch Pipeline", "Organization Folder"};
            HomePage homePage = new HomePage(getDriver());
            for(String title : listOfObjects) {
                getWait10().until(ExpectedConditions.not(ExpectedConditions.urlContains("configure")));
                homePage.clickNewItemOnLeftMenu()
                        .sendName(title)
                        .selectItemTypeAndSubmitAndGoHome(title);
            }
            List<String> results = homePage.clickSearchButton()
                    .searchFor("F")
                    .searchResults();
            System.out.println("res: " + results.toString());

            Assert.assertEquals(results.size(), 4);
        }
    }
