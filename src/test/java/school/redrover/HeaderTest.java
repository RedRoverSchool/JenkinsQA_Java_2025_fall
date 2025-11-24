package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BasePage;
import school.redrover.common.BaseTest;
import school.redrover.page.*;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

public class HeaderTest extends BaseTest {

    @Test
    public void testSearchResultsAppear() {
        final String[] listOfTypes = new String[]{"Folder", "Freestyle project", "Pipeline", "Multi-configuration project", "Multibranch Pipeline", "Organization Folder"};

        HomePage homePage = new HomePage(getDriver());
        for (String title : listOfTypes) {
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
    public void testGlobalSearchButtonAppears() throws InterruptedException {
        Navigation navigation = new Navigation();
        List<BasePage> mainJenkinsPages = List.of(
                navigation.goToNewItem(),
                navigation.goToManageJenkins(),
                navigation.goToUserPage(),
                navigation.goToBuildHistory()
        );
        for (BasePage page : mainJenkinsPages) {
            Assert.assertTrue(page.isSearchButtonPresent());
            Thread.sleep(2000);
            page.gotoHomePage();
        }
    }

    class Navigation {
        static private final Properties properties = new Properties();

//        static {
//            try {
//                FileInputStream file = new FileInputStream("src/test/resources/.properties");
//                properties.load(file);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        private String getBaseURL() {
//            return properties.getProperty("jenkins.host") + ":" + properties.getProperty("jenkins.port");
//        }

        public NewItemPage goToNewItem() {
            new HomePage(getDriver()).clickNewItemOnLeftMenu();
            return new NewItemPage(getDriver());
        }

        public BuildHistoryOfJenkinsPage goToBuildHistory() {
            new HomePage(getDriver()).clickBuildHistoryWithGetWait();
            return new BuildHistoryOfJenkinsPage(getDriver());
        }

        public ManageJenkinsPage goToManageJenkins() {
            new HomePage(getDriver()).clickManageJenkinsIcon();
            return new ManageJenkinsPage(getDriver());
        }

        public ManageUsersPage goToUserPage() {
            new HomePage(getDriver()).clickUserAccount();
            return new ManageUsersPage(getDriver());
        }
    }
}
