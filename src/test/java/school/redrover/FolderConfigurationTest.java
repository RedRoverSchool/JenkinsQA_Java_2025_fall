package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.*;
import java.util.Arrays;
import java.util.List;

public class FolderConfigurationTest extends BaseTest {

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

    public void createFolder() {
        new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit()
                .gotoHomePage();
    }

    @Test
    public void testHealthMetricLinkIsDisplayed(){
        String linkDisplayed = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit()
                .getHealthMetricsSidebarLink();

        Assert.assertEquals(linkDisplayed, "Health metrics");
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testHealthMetricButtonIsDisplayed(){
        String buttonDisplayed = new HomePage(getDriver())
                .openProject(FOLDER_NAME, () -> new FolderPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .getHealthMetricsButton();

        Assert.assertEquals(buttonDisplayed,"Health metrics");
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testHealthMetricSectionNavigation(){
        String sectionName = new HomePage(getDriver())
                .openProject(FOLDER_NAME, () -> new FolderPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickHealthMetricsSidebarLink()
                .getSectionName();

        Assert.assertEquals(sectionName,"Health metrics");
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testVerifyMetricTypeList(){

        final List<String> expectedMetricTypes= Arrays.asList("Child item with the given name","Child item with worst health");

        List<String> actualMetricTypes = new HomePage(getDriver())
                .openProject(FOLDER_NAME, () -> new FolderPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickHealthMetricsSidebarLink()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .getAllMetricTypeNames();

        Assert.assertEquals(actualMetricTypes, expectedMetricTypes,
                "The list of displayed metric types in the dropdown did not match the expected list.");
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testAddWorstHealth() {

        final String expectedMetric = "Child item with worst health";

        String actualMetricAdded = new HomePage(getDriver())
                .openProject(FOLDER_NAME, () -> new FolderPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .clickWorstHealthButton()
                .getMetricRowName();

        Assert.assertEquals(actualMetricAdded, expectedMetric);
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testAddGivenName() {

        final String expectedMetric = "Child item with the given name";

        String actualMetricAdded = new HomePage(getDriver())
                .openProject(FOLDER_NAME, () -> new FolderPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .clickGivenNameButton()
                .getMetricRowName();

        Assert.assertEquals(actualMetricAdded, expectedMetric);
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testDragWorstHealthToTop() {

        final String expectedTopMetric = "Child item with worst health";

        String actualTopMetric = new HomePage(getDriver())
                .openProject(FOLDER_NAME, () -> new FolderPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .clickGivenNameButton()
                .clickAddMetricButton()
                .clickWorstHealthButton()
                .dragWorstHealthRowToTop()
                .getMetricRowName();

        Assert.assertEquals(actualTopMetric, expectedTopMetric);
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testDeleteMetric() {
        List <String> metricList = new HomePage(getDriver())
                .openProject(FOLDER_NAME, () -> new FolderPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .clickGivenNameButton()
                .clickAddMetricButton()
                .clickWorstHealthButton()
                .deleteMetric()
                .getMetricList();

        Assert.assertNotEquals(metricList.size(), 0);
        Assert.assertEquals(metricList.size(), 1);
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testChildNameTooltip() {

        final String textOnHover = "Help for feature: Child Name";

        String actualText = new HomePage(getDriver())
                .openProject(FOLDER_NAME, () -> new FolderPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .clickGivenNameButton()
                .hoverChildNameTooltip()
                .getChildNameHelpText();

        Assert.assertEquals(actualText, textOnHover);
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testChildNameTooltipContent() {

        final String expectedTooltip = "Controls the child item within this folder representing to the health of this folder.";

        String actualText = new HomePage(getDriver())
                .openProject(FOLDER_NAME, () -> new FolderPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .clickGivenNameButton()
                .clickChildNameTooltip()
                .getChildNameTooltipText();

        Assert.assertEquals(actualText, expectedTooltip);
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testWorstHealthTooltip() {

        final String textOnHover = "Help";

        String actualText = new HomePage(getDriver())
                .openProject(FOLDER_NAME, () -> new FolderPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .clickWorstHealthButton()
                .hoverRecursiveTooltip()
                .getTooltipTitle();

        Assert.assertEquals(actualText, textOnHover);
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testRecursiveTooltipContent() {

        final String expectedTooltip = "Controls whether items within sub-folders will be considered as contributing to the health of this folder.";

        String actualText = new HomePage(getDriver())
                .openProject(FOLDER_NAME, () -> new FolderPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .clickWorstHealthButton()
                .clickRecursiveTooltip()
                .getRecursiveTooltipText();

        Assert.assertEquals(actualText, expectedTooltip);
    }

    @Test(dataProvider = "itemsData")
    public void testConfigureMenuItemInDropdownForEachJob(String itemName, String itemType) {
        final String menuItem = "Configure";
        createFolder();

        WebElement configureMenuItem = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickSidebarNewItem()
                .sendName(itemName)
                .selectItemTypeAndSubmitAndGoHome(itemType)
                .clickFolder(FOLDER_NAME)
                .openItemDropdownMenu(itemName)
                .getMenuItemInDropdown(menuItem);

        Assert.assertTrue(configureMenuItem.isDisplayed());
    }

    @Test
    public void testNavigateToConfigurationViaSideMenuForPipeline() {
        final String itemName = "pipeline01";
        final String expectedPageHeader = "Configuration";

        createFolder();

        String actualPageHeader = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .clickSidebarNewItem()
                .sendName(itemName)
                .selectPipelineAndSubmit()
                .gotoHomePage()
                .clickFolder(FOLDER_NAME)
                .openItemPage(itemName, new PipelinePage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .getPageHeader();

        Assert.assertEquals(actualPageHeader, expectedPageHeader);
    }
}