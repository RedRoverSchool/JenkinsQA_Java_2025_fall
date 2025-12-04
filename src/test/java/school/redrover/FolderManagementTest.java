package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.*;
import school.redrover.testdata.ProjectPage;

public class FolderManagementTest extends BaseTest {

    private static final String FOLDER_NAME = "MyFolder";

    @DataProvider
    public Object[][] itemsData() {
        return new Object[][]{
                {"freestyle01", "Freestyle project"},
                {"pipeline01", "Pipeline"},
                {"multiConfig01", "Multi-configuration project"},
                {"folder01", "Folder"},
                {"Multibranch01", "Multibranch Pipeline"},
                {"orgFolder01", "Organization Folder"}
        };
    }

    @DataProvider
    Object[][] configurationOfProjectData() {
        return new Object[][]{
                {"freestyle01", "Freestyle project", "Configure", ProjectPage.FREESTYLE_PROJECT_PAGE},
                {"pipeline01", "Pipeline", "Configure", ProjectPage.PIPELINE_PROJECT_PAGE},
                {"multiConfig01", "Multi-configuration project", "Configure", ProjectPage.MULTI_CONFIGURATION_PROJECT_PAGE},
                {"folder01", "Folder", "Configuration", ProjectPage.FOLDER_PROJECT_PAGE},
                {"Multibranch01", "Multibranch Pipeline", "Configuration", ProjectPage.MULTIBRANCH_PIPELINE_PROJECT_PAGE},
                {"orgFolder01", "Organization Folder", "Configuration", ProjectPage.ORGANIZATION_FOLDER_PROJECT_PAGE}
        };
    }

    @DataProvider
    public Object[][] sideMenuItemsData() {
        return new Object[][]{
                {"Configure", "Configuration"},
                {"New Item", "New Item"},
                {"Build History", "Build History of Jenkins"},
                {"Rename", "Rename Folder MyFolder"},
                {"Credentials", "Credentials"}
        };
    }

    public void createFolder() {
        new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit()
                .gotoHomePage();
    }

    @Test(dataProvider = "itemsData")
    public void testConfigureMenuItemInDropdownForEachJob(String itemName, String itemType) {
        final String menuItem = "Configure";

        createFolder();

        boolean isConfigureMenuItemDisplayed = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickSidebarNewItem()
                .sendName(itemName)
                .selectItemTypeAndSubmitAndGoHome(itemType)
                .clickFolder(FOLDER_NAME)
                .openItemDropdownMenu(itemName)
                .isMenuItemInDropdownDisplayed(menuItem);

        Assert.assertTrue(isConfigureMenuItemDisplayed);
    }

    @Test
    public void testNavigateToConfigurationViaSideMenuForPipeline() {
        final String itemName = "pipeline01";

        createFolder();

        String actualHeadingText = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickSidebarNewItem()
                .sendName(itemName)
                .selectPipelineAndSubmit()
                .gotoHomePage()
                .clickFolder(FOLDER_NAME)
                .openSubItemPage(itemName, new PipelinePage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .getHeadingText();

        Assert.assertEquals(actualHeadingText, "Configure");
    }

    @Test(dataProvider = "configurationOfProjectData")
    public void testNavigateToConfigurationViaSideMenuForEachJob(String itemName, String itemType, String expectedHeading, ProjectPage page) {
        createFolder();

        String actualHeadingText = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickSidebarNewItem()
                .sendName(itemName)
                .selectItemTypeAndSubmitAndGoHome(itemType)
                .clickFolder(FOLDER_NAME)
                .openSubItemPage(itemName, page.createProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .getHeadingText();

        Assert.assertEquals(actualHeadingText, expectedHeading);
    }

    @Test(dataProvider = "sideMenuItemsData")
    public void testNavigateToPageOfSideMenuItemOfFolder(String menuItemName, String expectedHeading){
        createFolder();

        String actualHeading = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .goToSideMenuItemPage(menuItemName)
                .getHeadingText();

        Assert.assertEquals(actualHeading, expectedHeading);
    }
}
