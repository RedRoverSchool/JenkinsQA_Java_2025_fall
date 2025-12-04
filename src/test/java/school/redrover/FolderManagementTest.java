package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.*;
import school.redrover.testdata.ProjectPage;
import school.redrover.testdata.TestDataProvider;

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
                .openItemPage(itemName, new PipelinePage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .getHeadingText();

        Assert.assertEquals(actualHeadingText, "Configure");
    }

    @Test(dataProvider = "ConfigurationMenuItem", dataProviderClass = TestDataProvider.class)
    public void testNavigateToConfigurationViaSideMenuForEachJob(String itemName, String itemType, String expectedHeading, ProjectPage page) {
        createFolder();

        String actualHeadingText = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickSidebarNewItem()
                .sendName(itemName)
                .selectItemTypeAndSubmitAndGoHome(itemType)
                .clickFolder(FOLDER_NAME)
                .openItemPage(itemName, page.createProjectPage(getDriver()))
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
