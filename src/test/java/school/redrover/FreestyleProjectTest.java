package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
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
    private static final String PARAMETRIZATION_CHECKBOX = "This project is parameterized";

    private static final List<String> BUILD_STEPS = List.of(
            "Execute Windows batch command",
            "Execute shell",
            "Invoke Ant",
            "Invoke Gradle script",
            "Invoke top-level Maven targets",
            "Run with timeout",
            "Set build status to \"pending\" on GitHub commit"
    );

    private static final List<String> PARAMETER_EXPECTED = List.of(
            "Boolean Parameter",
            "Choice Parameter",
            "Credentials Parameter",
            "File Parameter",
            "Multi-line String Parameter",
            "Password Parameter",
            "Run Parameter",
            "String Parameter"
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
                .openProject(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNow()
                .getNotificationBuildScheduled();

        Assert.assertEquals(actualNotificationBuildScheduled, "Build scheduled");
    }

    @Test(dependsOnMethods = "testScheduleBuild")
    public void testAddDescription() {
        String actualDescriptionText = new HomePage(getDriver())
                .openProject(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
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
                .openProject(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickEnableDisableProject()
                .clickSave()
                .gotoHomePage()
                .isBuildButtonVisible(PROJECT_NAME);

        Assert.assertTrue(visibleBuildButtonForEnabledProject);
    }

    @Test(dependsOnMethods = "testEnableProjectViaMainMenuConfigure")
    public void testBuildStepsFilterNames() {

        FreestyleProjectConfigurationPage configPage = new HomePage(getDriver())
                .openProject(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
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
        String scmTitleText = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .getSCMTitleText();

        Assert.assertEquals(scmTitleText, SCM_TITLE_EXPECTED);
    }

    @Test
    public void testSCMSectionElements() {
        final String expectedCSMText = "Connect and manage your code repository to automatically pull the latest code for your builds.";

        FreestyleProjectConfigurationPage configPage = new HomePage(getDriver()).clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit();

        Assert.assertEquals(configPage.getScmDescriptionText(), expectedCSMText, "SCM Description is not displayed or the description text doesn't match");
        Assert.assertEquals(configPage.getSelectedRadioLabel(), "None", "Radio button 'None' should be selected by default");
        Assert.assertTrue(configPage.isGitOptionDisplayed(), "Radio button 'Git' should be displayed");
        Assert.assertEquals(configPage.getGitTooltipText(), "Help for feature: Git", "Tooltip text should match expected value");
    }

    @Test
    public void testAccessSCMInExistingJob() {
        String scmTitleText = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .gotoHomePage()
                .openProject(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .getSCMTitleText();

        Assert.assertEquals(scmTitleText, SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToSCMViaMenu() {
        String scmTitleText = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickSourceCodeManagementMenuOption()
                .getSCMTitleText();

        Assert.assertEquals(scmTitleText, SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToSCMByScrollingDown() {
        String scmTitleText = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .scrollToSourceCodeManagementWithJS()
                .getSCMTitleText();

        Assert.assertEquals(scmTitleText, SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToTriggersBySideMenu() {
        String triggerTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickTriggersLinkInSideMenu()
                .getTriggerTitleText();

        Assert.assertEquals(triggerTitle, "Triggers");
    }

    @Test(dependsOnMethods = "testNavigationToTriggersBySideMenu")
    public void testTriggersSectionDescriptionIsDisplayed() {
        final String expectedDescription = "Set up automated actions that start your build based on specific events, like code changes or scheduled times.";

        String triggersDescription = new HomePage(getDriver())
                .openProject(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickTriggersLinkInSideMenu()
                .getTriggersDescriptionText();

        Assert.assertEquals(triggersDescription, expectedDescription);
    }

    @Test
    public void testAllTriggerCheckboxesAreAvailable() {
        final List<String> expectedCheckboxes = List.of(
                "Trigger builds remotely (e.g., from scripts)",
                "Build after other projects are built",
                "Build periodically",
                "GitHub hook trigger for GITScm polling",
                "Poll SCM"
        );

        List<String> actualCheckboxes = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickTriggersLinkInSideMenu()
                .getTriggerCheckboxLabels();

        Assert.assertEquals(actualCheckboxes, expectedCheckboxes);
    }

    @Test
    public void testSaveButtonIsVisibleAndClickable() {

        boolean saveButton = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .isSaveButtonDisplayed();

        Assert.assertTrue(saveButton);
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

    @Test
    public void testParametersForParameterizationOfBuildsIsDisplayed() {
        List<String> actualParameterList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .selectCheckbox(PARAMETRIZATION_CHECKBOX)
                .clickAddParameterDropDownButton()
                .getAddParameterList();

        Assert.assertEquals(actualParameterList, PARAMETER_EXPECTED);
    }

    @Test
    public void testAddParameterForParameterizationOfBuilds() {
        String parameterName = PARAMETER_EXPECTED.get(0);

        List<String> selectedParameterList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .selectCheckbox(PARAMETRIZATION_CHECKBOX)
                .clickAddParameterDropDownButton()
                .selectParameterInDropDownButton(parameterName)
                .getSelectedParameterList();

        Assert.assertTrue(selectedParameterList.contains(parameterName));
    }
}
