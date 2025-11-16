package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class MultibranchPipeline1Test extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "Multibranch_Pipeline";
    private static final String MULTIBRANCH_PIPELINE_DISPLAY_NAME = "Multibranch_Pipeline_Display";
    private static final String FIRST_DESCRIPTION = "First Description";
    private static final String SECOND_DESCRIPTION = "Second Description";

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
@Ignore
@Test (dependsOnMethods = "testCreateClickCreateJob")
    public void testChangeDescription() {

        String name = new HomePage(getDriver())
                .clickDescription()
                .sendDescriptionText(FIRST_DESCRIPTION)
                .submitDescription()
                .clickDescription()
                .clearTextDescription()
                .sendDescriptionText(SECOND_DESCRIPTION)
                .submitDescription()
                .getDescription();

        Assert.assertEquals(name, SECOND_DESCRIPTION);

    }
}
