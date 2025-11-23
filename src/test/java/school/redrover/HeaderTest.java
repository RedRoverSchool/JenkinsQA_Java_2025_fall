package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.NewItemPage;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

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

        @Test

class Navigation {
            static private final Properties properties = new Properties();

        static {
            try {
                FileInputStream file = new FileInputStream("resources.properties");
                properties.load(file);
            } catch ( Exception e) {
                throw new RuntimeException(e);
            }
        }

        private String getBaseURL() {
            return properties.getProperty("jenkins.host") + ":" + properties.getProperty("jenkins.port");
        }

        public NewItemPage goToNewItem() {
            getDriver().get(getBaseURL() + "/view/all/newJob");
            return new NewItemPage(getDriver());
        }
//        public NewItemPage goToNewItem() {
//          getDriver().get(getBaseURL() + "/view/all/newJob");
//         return new NewItemPage(getDriver());
    }
    }
