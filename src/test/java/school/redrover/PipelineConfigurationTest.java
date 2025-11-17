package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.PipelinePage;

public class PipelineConfigurationTest extends BaseTest {

    private static final String PIPELINE_NAME = "PipelineName";

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
                .openJobPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureInSideMenu(PIPELINE_NAME)
                .clickToggle()
                .getToggleUncheckedLabelText();

        Assert.assertEquals(toggleLabelText, "Disabled");
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testActivityStatusProject() {

        String actualProjectStatus = new HomePage(getDriver())
                .openJobPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureInSideMenu(PIPELINE_NAME)
                .clickToggle()
                .clickSaveButton()
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
                .clickSaveButton()
                .getWarningMessage();

        Assert.assertEquals(warningMessage, "This project is currently disabled\n" +
                "Enable");
    }
}
