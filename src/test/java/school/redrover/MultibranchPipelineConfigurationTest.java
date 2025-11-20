package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.MultibranchPipelineProjectPage;

public class MultibranchPipelineConfigurationTest extends BaseTest {

    private static final String JOB_NAME = "multibranchJobName";
    private static final String JOB_DESCRIPTION = "This is a job description";

    @Test
    public void testCreateMultibranchPipelineJob() {
        String actualHeadingText = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(JOB_NAME)
                .selectMultibranchPipelineAndSubmit()
                .clickSaveButton()
                .getHeadingText();

        Assert.assertEquals(actualHeadingText, JOB_NAME);
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipelineJob")
    public void testDisableToggle() {
        final String expectedToggleState = "Disabled";

        String actualToggleState = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickToggle()
                .getToggleState();

        Assert.assertEquals(actualToggleState, expectedToggleState);
    }

    @Test(dependsOnMethods = "testDisableToggle")
    public void testTooltipOnToggleHover() {
        final String expectedTooltipText = "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        String actualTooltipText = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .getToggleTooltipTextOnHover();

        Assert.assertEquals(actualTooltipText, expectedTooltipText);
    }

    @Test(dependsOnMethods = "testTooltipOnToggleHover")
    public void testDisabledMessageOnStatusPage() {
        final String expectedDisabledMessage = "This Multibranch Pipeline is currently disabled";

        String actualDisabledMessage = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickToggle()
                .clickSaveButton()
                .getDisabledText();

        Assert.assertEquals(actualDisabledMessage, expectedDisabledMessage);
    }

    @Test(dependsOnMethods = "testDisabledMessageOnStatusPage")
    public void testJobDescriptionPreview() {
        String jobDescriptionPreviewText = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .enterDescription(JOB_DESCRIPTION)
                .getJobDescriptionPreviewText();

        Assert.assertEquals(jobDescriptionPreviewText, JOB_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testJobDescriptionPreview")
    public void testMultibranchJobDescription() {
        String actualJobDescription = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .enterDescription(JOB_DESCRIPTION)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualJobDescription, JOB_DESCRIPTION);
    }
}
