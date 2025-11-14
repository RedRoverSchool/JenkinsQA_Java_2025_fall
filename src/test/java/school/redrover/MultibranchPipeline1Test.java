package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class MultibranchPipeline1Test extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "Multibranch_Pipeline";
    private static final String MULTIBRANCH_PIPELINE_DISPLAY_NAME = "Multibranch_Pipeline_Display";

    @Test
    public void testCreateClickCreateJob() {

        String name = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .clickSaveButton()
                .gotoHomePage()
                .findItem(MULTIBRANCH_PIPELINE_NAME)
                .getText();

        Assert.assertEquals(name, MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testCreateClickNewItem() {

        String name = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .sendDisplayName(MULTIBRANCH_PIPELINE_DISPLAY_NAME)
                .clickSaveButton()
                .gotoHomePage()
                .findItem(MULTIBRANCH_PIPELINE_NAME)
                .getText();

        Assert.assertEquals(name, MULTIBRANCH_PIPELINE_DISPLAY_NAME);
    }
}
