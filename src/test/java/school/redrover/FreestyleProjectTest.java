package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.FreestyleProjectConfigurationPage;
import school.redrover.page.FreestyleProjectPage;
import school.redrover.page.HomePage;

import java.util.List;

public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";
    private static final String PROJECT_DESCRIPTION_EXPECTED = "This is a description for Freestyle Project";
    private static final String SCM_TITLE_EXPECTED = "Source Code Management";

    private static final List<String> BUILD_STEPS = List.of(
            "Execute Windows batch command",
            "Execute shell",
            "Invoke Ant",
            "Invoke Gradle script",
            "Invoke top-level Maven targets",
            "Run with timeout",
            "Set build status to \"pending\" on GitHub commit"
    );

    @Test
    public void testCreate() {
        List<String> projectList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertEquals(projectList.get(0), PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testScheduleBuild() {
        String actualNotificationBuildScheduled = new HomePage(getDriver())
                .openPage(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNow()
                .getNotificationBuildScheduled();

        Assert.assertEquals(actualNotificationBuildScheduled, "Build scheduled");
    }

    @Test(dependsOnMethods = "testScheduleBuild")
    public void testAddDescription() {
        String actualDescriptionText = new HomePage(getDriver())
                .openPage(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure(PROJECT_NAME)
                .setDescription(PROJECT_DESCRIPTION_EXPECTED)
                .clickSave()
                .getDescription();

        Assert.assertEquals(actualDescriptionText, PROJECT_DESCRIPTION_EXPECTED);
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testDisableProjectViaConfigureDropdownMenu() {
        String disableProjectMessage = new HomePage(getDriver())
                .openDropdownMenu(PROJECT_NAME)
                .clickConfigureInDropdownMenu()
                .clickEnableDisableProject()
                .clickSave()
                .getDisableProjectMessage();

        Assert.assertEquals(disableProjectMessage, "This project is currently disabled");
    }

    @Test(dependsOnMethods = "testDisableProjectViaConfigureDropdownMenu")
    public void testEnableProjectViaMainMenuConfigure() {
        boolean visibleBuildButtonForEnabledProject = new HomePage(getDriver())
                .openPage(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure(PROJECT_NAME)
                .clickEnableDisableProject()
                .clickSave()
                .gotoHomePage()
                .isBuildButtonVisible(PROJECT_NAME);

        Assert.assertTrue(visibleBuildButtonForEnabledProject);
    }


    @Test(dependsOnMethods = "testEnableProjectViaMainMenuConfigure")
    public void testBuildStepsFilterNames() {

        FreestyleProjectConfigurationPage configPage = new HomePage(getDriver())
                .openPage(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure(PROJECT_NAME)
                .clickBuildStepMenuOption();

        for (String buildStep : BUILD_STEPS) {

            configPage
                    .typeIntoFilterBuildStep(buildStep.substring(0, Math.min(5, buildStep.length())));

            WebElement visibleStep = configPage.verifySentNameIsInFilter(buildStep);

            Assert.assertEquals(
                    visibleStep.getText(),
                    buildStep,
                    "Filter didn't match expected build step");
        }
    }

    @Test
    public void testBuildSteps() {

        new HomePage(getDriver())
                .clickCreateJob()
                .sendName("TestProject")
                .selectFreestyleProjectAndSubmit()
                .clickBuildStepMenuOption();

        for (String step : BUILD_STEPS) {
            WebElement buildStep = getDriver().findElement(By.xpath("//button[contains(text(),'%s')]".formatted(step)));
            Assert.assertEquals(buildStep.getText(), step);
        }
    }


    @Test
    public void testAccessSCMInNewJob() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testSCMSectionElements() {
        FreestyleProjectConfigurationPage configPage = new HomePage(getDriver()).clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit();

        Assert.assertTrue(configPage.getScmDescription().isDisplayed(),"SCM Description is not displayed or the description text doesn't match");
        Assert.assertEquals(configPage.getSelectedRadioLabel(), "None","Radio button 'None' should be selected by default");
        Assert.assertTrue(configPage.isGitOptionDisplayed(),"Radio button 'Git' should be displayed");
        Assert.assertEquals(configPage.getGitTooltipText(),"Help for feature: Git","Tooltip text should match expected value");
    }

    @Test
    public void testAccessSCMInExistingJob() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .gotoHomePage()
                .openPage(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure(PROJECT_NAME)
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToSCMViaMenu() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickSourceCodeManagementMenuOption()
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToSCMByScrollingDown() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .scrollToSourceCodeManagementWithJS()
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToTriggersBySideMenu () {
        String triggerTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickTriggerLinkInSideMenu()
                .getTriggerTitleText();

        Assert.assertEquals(triggerTitle, "Triggers");
    }

    @Test
    public void testSaveButtonIsVisibleAndClickable() {

        WebElement saveButton = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .getSaveButton();

        Assert.assertTrue(saveButton.isDisplayed());
    }

    @Test(dependsOnMethods = "testBuildStepsFilterNames")
    public void testDeleteFreestyleProject() {
        final String expectedHeadingText = "Welcome to Jenkins!";

        HomePage homePage = new HomePage(getDriver())
                .openDropdownMenu(PROJECT_NAME)
                .clickDeleteItemInDropdownMenu()
                .confirmDelete();

        Assert.assertEquals(homePage.getHeadingText(), expectedHeadingText);
    }

}
