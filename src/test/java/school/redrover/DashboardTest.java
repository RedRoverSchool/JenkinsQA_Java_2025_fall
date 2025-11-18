package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BasePage;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.EditViewPage;
import school.redrover.page.HomePage;
import school.redrover.testdata.Page;
import school.redrover.testdata.TestDataProvider;

import java.util.*;

public class DashboardTest extends BaseTest {

    private static final List<String> CREATED_JOBS_NAME = List.of(
            "FreestyleName1",
            "FreestyleName2",
            "FreestyleName3",
            "FreestyleName4",
            "FreestyleName5"
    );

    private static final String PIPELINE_NAME = "Pipeline_01";

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

    @Test(dataProvider = "Links", dataProviderClass = TestDataProvider.class)
    public void testContentBlockLinks(String linkText, String expectedUrlEndpoint, Page page) {
        BasePage resultPage = new HomePage(getDriver()).clickHomePageSectionLink(linkText, page.createPage(getDriver()));

        Assert.assertTrue(Objects.requireNonNull(resultPage.getCurrentUrl()).contains(expectedUrlEndpoint));
    }

    @Test
    public void testCheckCreatedJobsOnDashboard() {
        HomePage homePage = new HomePage(getDriver());

        for (int i = 0; i < CREATED_JOBS_NAME.size(); i++) {
            homePage
                    .clickNewItemOnLeftMenu()
                    .sendName(CREATED_JOBS_NAME.get(i))
                    .selectFreestyleProjectAndSubmit()
                    .gotoHomePage();
        }
        List<String> actualJobs = homePage.getProjectList();

        Assert.assertFalse(actualJobs.isEmpty(), "Item's list is empty!");
        Assert.assertEquals(actualJobs, CREATED_JOBS_NAME, "Имена созданных jobs не совпадают!");
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
    public void testGoToManageJenkinsPage() {
        final String expectedTitle = "Manage Jenkins";

        String actualTitle = new HomePage(getDriver())
                .clickManageJenkinsIcon()
                .getHeadingText();

        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test
    public void testAddColumnsInListViewOnDashboard() {
        final String listViewName = "ListView_01";

        HomePage homePage = new HomePage(getDriver());
        homePage.clickCreateJob()
                .sendName(PIPELINE_NAME)
                .selectItemTypeAndSubmitAndGoHome("Pipeline")
                .clickPlusToCreateView()
                .sendViewName(listViewName)
                .selectListViewRadioAndCreate()
                .selectJobCheckbox(PIPELINE_NAME)
                .clickAddColumnDropDownButton();

        EditViewPage editViewPage = new EditViewPage(getDriver());
        List<String> currentColumnListText = editViewPage.getCurrentColumnList();
        Assert.assertNotEquals(currentColumnListText.size(), 0);

        Set<String> addColumnSet = new HashSet<>();

        List<WebElement> columnListForAdd = editViewPage.getAddColumnList();
        Assert.assertNotEquals(columnListForAdd.size(), 0);
        for (WebElement element : columnListForAdd) {
            String columnName = element.getText().trim();
            addColumnSet.add(columnName);
            if (!currentColumnListText.contains(columnName)) {
                TestUtils.mouseEnterJS(getDriver(), element);
                TestUtils.clickJS(getDriver(), element);
            }
        }

        List<String> addedColumnList = editViewPage.getCurrentColumnList();
        Assert.assertTrue(addedColumnList.containsAll(addColumnSet));

        editViewPage.clickSubmitButton();
        int actualCountDisplayedColumns = homePage.getCountOfDisplayedColumnsOnDashboard();
        Assert.assertEquals(actualCountDisplayedColumns,addedColumnList.size());
    }
}
