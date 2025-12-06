package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.ArchitectingforScalePage;
import school.redrover.page.CloudsPage;
import school.redrover.page.EditViewPage;
import school.redrover.page.HomePage;
import school.redrover.page.NewNodePage;

import java.util.List;

public class DashboardTest extends BaseTest {

    private static final List<String> CREATED_JOBS_NAME = List.of(
            "FreestyleName1",
            "FreestyleName2"
    );

    @DataProvider
    public Object[][] projectsName() {
        return new String[][]{
                {"FreestyleName1"},
                {"FreestyleName2"},
                {"FreestyleName3"},
                {"FreestyleName4"},
                {"FreestyleName5"}
        };
    }

    private static final String PIPELINE_NAME = "Pipeline_01";


    public void createProject(String name) {
        new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(name)
                .selectFreestyleProjectAndSubmit()
                .gotoHomePage();
    }

    @Test
    public void testHomePageHeading() {
        Assert.assertEquals(
                new HomePage(getDriver()).getHeadingText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testHomePageParagraph() {
        final String expectedParagraphText = "This page is where your Jenkins jobs will be displayed. " +
                "To get started, you can set up distributed builds or start building a software project.";

        Assert.assertEquals(
                new HomePage(getDriver()).getParagraghText(),
                expectedParagraphText);
    }

    @Test
    public void testLearnMoreAboutDistributedBuildsLink() {
        ArchitectingforScalePage resultPage = new HomePage(getDriver())
                .clickLearnMoreAboutDistributedBuildsLink();

        Assert.assertTrue(resultPage.getCurrentUrl().contains("architecting-for-scale"));
    }

    @Test(dataProvider = "projectsName")
    public void testCheckCreatedJobsOnDashboard(String projectName) {
        String actualJobs = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(projectName)
                .selectFreestyleProjectAndSubmit()
                .gotoHomePage()
                .getProjectName();

        Assert.assertEquals(actualJobs, projectName, "Имена созданных проектов не совпадают!");
    }

    @Test
    public void testSearchCreatedJobs() {
        createProject(CREATED_JOBS_NAME.get(0));

        String searchResults = new HomePage(getDriver())
                .clickSearchButton()
                .searchFor(CREATED_JOBS_NAME.get(0))
                .moveAndClickResult()
                .getHeadingText();

        Assert.assertEquals(searchResults, CREATED_JOBS_NAME.get(0));
    }

    @Test
    public void testCheckDeleteViewOnDashboard() {
        final String viewName = "myView";

        int viewListSize = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(CREATED_JOBS_NAME.get(0))
                .selectFreestyleProjectAndSubmit()
                .gotoHomePage()
                .clickPlusToCreateView()
                .sendViewName(viewName)
                .clickMyViewName()
                .clickCreateButtonForNewView()
                .clickDeleteViewOnSidebar()
                .clickYesToConfirmDelete()
                .getSizeOfViewNameList();

        Assert.assertEquals(viewListSize, 2, "Есть созданный пользователем View на Dashboard");
    }

    @Test
    public void testStatusProjectIconHasTooltip() {
        final String tooltipEnableText = "Not built";

        String actualStatusTooltip = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(CREATED_JOBS_NAME.get(0))
                .selectFreestyleProjectAndSubmit()
                .gotoHomePage()
                .getStatusProjectIconTooltipTextOnHover();

        Assert.assertEquals(actualStatusTooltip, tooltipEnableText, "Проект отключен или не создан!");
    }

    @Test
    public void testLogo() {
        String logoText = new HomePage(getDriver())
                .getLogoText();

        Assert.assertEquals(logoText, "Jenkins", "Надпись рядом с логотипом должна быть 'Jenkins'");
    }

    @Test
    public void testGoToManageJenkinsPage() {
        final String expectedTitle = "Manage Jenkins";

        String actualTitle = new HomePage(getDriver())
                .clickManageJenkinsGear()
                .getHeadingText();

        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test
    public void testAddColumnsInListViewOnDashboard() {
        final String listViewName = "ListView_01";
        final List<String> expectedColumnList = List.of(
                "Status",
                "Weather",
                "Name",
                "Last Success",
                "Last Failure",
                "Last Duration",
                "Build Button",
                "Last Stable",
                "Git Branches",
                "Project description"
        );

        HomePage homePage = new HomePage(getDriver());
        homePage
                .clickCreateJob()
                .sendName(PIPELINE_NAME)
                .selectItemTypeAndSubmitAndGoHome("Pipeline")
                .clickPlusToCreateView()
                .sendViewName(listViewName)
                .selectListViewRadioAndCreate()
                .selectJobCheckbox(PIPELINE_NAME)
                .clickAddColumnDropDownButton();

        EditViewPage editViewPage = new EditViewPage(getDriver());
        List<String> actualColumnList = editViewPage
                .addColumnInListView()
                .getCurrentColumnList();

        Assert.assertNotEquals(actualColumnList.size(), 0);
        Assert.assertEquals(actualColumnList, expectedColumnList);

        editViewPage.clickSubmitButton();
        int actualCountDisplayedColumns = homePage.getCountOfDisplayedColumnsOnDashboard();
        Assert.assertEquals(actualCountDisplayedColumns, actualColumnList.size());
    }

    @Test(dependsOnMethods = "testAddColumnsInListViewOnDashboard")
    public void testRemoveColumnsInListView() {
        final String listViewName = "ListView_01";
        final String columnName = "Last Success";

        HomePage homePage = new HomePage(getDriver());
        int initialCountDisplayedColumns = homePage
                .clickViewName(listViewName)
                .getCountOfDisplayedColumnsOnDashboard();

        List<String> actualColumnListAfterDelete = homePage
                .clickViewName(listViewName)
                .clickEditViewButton(listViewName)
                .clickDeleteButton(columnName)
                .getCurrentColumnList();

        Assert.assertFalse(actualColumnListAfterDelete.contains(columnName));

        new EditViewPage(getDriver()).clickSubmitButton();

        int actualCountDisplayedColumns = homePage.getCountOfDisplayedColumnsOnDashboard();
        Assert.assertEquals(actualCountDisplayedColumns, initialCountDisplayedColumns - 1);
    }

    @Test
    public void testSetUpAgent() {
        NewNodePage newNodePage = new HomePage(getDriver())
                .openPage("Set up an agent", new NewNodePage(getDriver()));

        Assert.assertEquals(newNodePage.getHeadingText(), "New node");
        Assert.assertTrue(newNodePage.isFormDisplayed(), "New Node form is not visible");
    }

    @Test
    public void testConfigureCloudIntegration() {
        CloudsPage cloudsPage = new HomePage(getDriver())
                .openPage("Configure a cloud", new CloudsPage(getDriver()));

        Assert.assertEquals(cloudsPage.getHeadingText(), "Clouds");
        Assert.assertEquals(cloudsPage.getParagraphText(), "There is no plugin installed that supports clouds.");
    }
}