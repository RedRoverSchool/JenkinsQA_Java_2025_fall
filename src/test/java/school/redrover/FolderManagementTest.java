package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.*;

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
                {"Status", FOLDER_NAME},
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

    @Test(dataProvider = "itemsData")
    public void testNavigateToConfigurationViaSideMenuForEachJob(String itemName, String itemType) {
        final String expectedBreadcrumbItem = "Configuration";

        createFolder();

        new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickSidebarNewItem()
                .sendName(itemName)
                .selectItemTypeAndSubmitAndGoHome(itemType)
                .clickFolder(FOLDER_NAME);

        String actualBreadcrumbItem = getBreadcrumbItem(itemName, itemType);
        Assert.assertEquals(actualBreadcrumbItem, expectedBreadcrumbItem);
    }

    private String getBreadcrumbItem(String itemName, String itemType) {
        switch (itemType) {
            case "Freestyle project":
                return new FolderPage(getDriver())
                        .openItemPage(itemName, new FreestyleProjectPage(getDriver()))
                        .clickConfigureLinkInSideMenu()
                        .getBreadcrumbItem();
            case "Pipeline":
                return new FolderPage(getDriver())
                        .openItemPage(itemName, new PipelinePage(getDriver()))
                        .clickConfigureLinkInSideMenu()
                        .getBreadcrumbItem();
            case "Multi-configuration project":
                return new FolderPage(getDriver())
                        .openItemPage(itemName, new MultiConfigurationProjectPage(getDriver()))
                        .clickConfigureLinkInSideMenu()
                        .getBreadcrumbItem();
            case "Folder":
                return new FolderPage(getDriver())
                        .openItemPage(itemName, new FolderPage(getDriver()))
                        .clickConfigureLinkInSideMenu()
                        .getBreadcrumbItem();
            case "Multibranch Pipeline":
                return new FolderPage(getDriver())
                        .openItemPage(itemName, new MultibranchPipelineProjectPage(getDriver()))
                        .clickConfigureLinkInSideMenu()
                        .getBreadcrumbItem();
            case "Organization Folder":
                return new FolderPage(getDriver())
                        .openItemPage(itemName, new OrganizationFolderPage(getDriver()))
                        .clickConfigureLinkInSideMenu()
                        .getBreadcrumbItem();
            default:
                throw new IllegalArgumentException("Unknown item type: " + itemType);
        }
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
