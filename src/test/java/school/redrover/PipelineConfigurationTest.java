package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.PipelineConfigurationPage;
import school.redrover.page.HomePage;
import school.redrover.page.PipelinePage;

import java.util.List;

public class PipelineConfigurationTest extends BaseTest {

    private static final String PIPELINE_NAME = "PipelineName";
    private static final String PIPELINE_NAME_FOR_CHANGE = "pipeline_01";

    @Test
    public void testEnableProject() {

        String toggleLabelText = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PIPELINE_NAME)
                .selectPipelineAndSubmit()
                .getToggleCheckedLabelText();

        Assert.assertEquals(toggleLabelText, "Enabled");
    }

    @Test(dependsOnMethods = "testEnableProject")
    public void testDisableProject() {

        String toggleLabelText = new HomePage(getDriver())
                .openProject(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickToggle()
                .getToggleUncheckedLabelText();

        Assert.assertEquals(toggleLabelText, "Disabled");
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testActivityStatusProject() {

        String actualProjectStatus = new HomePage(getDriver())
                .openProject(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickToggle()
                .clickSubmitButton()
                .gotoHomePage()
                .getProjectStatus(PIPELINE_NAME);

        Assert.assertEquals(actualProjectStatus, "Disabled");
    }

    @Test
    public void testDisablePipelineProject() {

        String warningMessage = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PIPELINE_NAME)
                .selectPipelineAndSubmit()
                .clickToggle()
                .clickSubmitButton()
                .getWarningMessage();

        Assert.assertEquals(warningMessage, "This project is currently disabled\n" +
                "Enable");
    }

    // Advanced Section
    @Test
    public void testNavigationToAdvancedByScrollingDown() {
        String actualAdvancedSectionTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PIPELINE_NAME)
                .selectPipelineAndSubmit()
                .scrollDownToAdvancedSection()
                .getAdvancedTitleText();

        Assert.assertEquals(actualAdvancedSectionTitle, "Advanced");
    }

    @Test(dependsOnMethods = "testNavigationToAdvancedByScrollingDown")
    public void testNavigationToAdvancedBySideMenu() {
        String actualAdvancedSectionTitle = new HomePage(getDriver())
                .openProject(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickAdvancedLinkInSideMenu()
                .getAdvancedTitleText();

        Assert.assertEquals(actualAdvancedSectionTitle, "Advanced");
    }

    @Test(dependsOnMethods = "testNavigationToAdvancedByScrollingDown")
    public void testAdvancedSectionQuietPeriodElements() {
        String actualQuietPeriodLabel = new HomePage(getDriver())
                .openProject(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickAdvancedButton()
                .getQuietPeriodLabelText();

        Assert.assertEquals(actualQuietPeriodLabel, "Quiet period");
        Assert.assertFalse(new PipelineConfigurationPage(getDriver())
                .quietPeriodCheckboxIsSelected(), "Default Checkbox should not be selected");
    }

    @Test(dependsOnMethods = "testNavigationToAdvancedByScrollingDown")
    public void testAdvancedSectionDisplayNameFieldElements() {
        String actualDisplayNameLabel = new HomePage(getDriver())
                .openProject(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickAdvancedButton()
                .getDisplayNameLabelText();

        Assert.assertEquals(actualDisplayNameLabel, "Display Name");
        Assert.assertTrue(new PipelineConfigurationPage(getDriver()).displayNameValueIsEmpty(),
                "Default Display Name field should be empty");
    }

    @Test(dependsOnMethods = "testAdvancedSectionQuietPeriodElements")
    public void testAdvancedSectionQuietPeriodElementsAfterSelecting() {
        String actualNumberOfSecondsLabel = new HomePage(getDriver())
                .openProject(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickAdvancedButton()
                .clickQuitePeriod()
                .getNumberOfSecondsLabelText();

        Assert.assertEquals(actualNumberOfSecondsLabel, "Number of seconds");
        Assert.assertTrue(new PipelineConfigurationPage(getDriver())
                .quietPeriodCheckboxIsSelected(), "Checkbox should be selected");
        Assert.assertTrue(new PipelineConfigurationPage(getDriver())
                .isNumberOfSecondsInputDisplayed());
    }

    @Test
    public void testAdvancedSectionSendDisplayName() {
        final String displayName = "PL_01";

        String actualDisplayNameInStatus = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PIPELINE_NAME_FOR_CHANGE)
                .selectPipelineAndSubmit()
                .clickAdvancedButton()
                .sendDisplayName(displayName)
                .clickSubmitButton()
                .getDisplayNameInStatus();

        Assert.assertEquals(actualDisplayNameInStatus, displayName);
        Assert.assertEquals(new PipelinePage(getDriver()).
                getDisplayNameInBreadcrumbBar(displayName), displayName);

        List<String> actualProjectList = new PipelinePage(getDriver())
                .gotoHomePage()
                .getProjectList();
        Assert.assertTrue(actualProjectList.contains(displayName),
                String.format("Project with Display Name '%s' not found in Project List", displayName));
    }

    @Test(dependsOnMethods = "testNavigationToAdvancedByScrollingDown")
    public void testAdvancedSectionVerifyTooltips() {
        final List<String> expectedTooltipList = List.of(
                "Help for feature: Quiet period",
                "Help for feature: Display Name");

        List<String> actualTooltipList = new HomePage(getDriver())
                .openProject(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickAdvancedButton()
                .getTooltipList();

        Assert.assertEquals(actualTooltipList, expectedTooltipList);
    }

    @Test(dependsOnMethods = "testAdvancedSectionVerifyTooltips")
    public void testAdvancedSectionHelpAreaIsDisplayed() {
        boolean isHelpElementDisplayed = new HomePage(getDriver())
                .openProject(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickAdvancedButton()
                .isHelpElementDisplayed();

        Assert.assertTrue(isHelpElementDisplayed);
    }
}