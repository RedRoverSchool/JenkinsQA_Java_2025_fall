package school.redrover;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.PipelineConfigurationPage;


public class PipelineBuildTriggersTest extends BaseTest {
    private static final String pipelineName = "pipeline_name";

    @Test
    public void testSelectTriggers() {
        HomePage homePage = new HomePage(getDriver());
        PipelineConfigurationPage myPage = homePage.clickNewItemOnLeftMenu()
                .sendName(pipelineName)
                        .selectPipelineAndSubmit();
                WebElement[] triggersSelected = myPage.selectAllTriggers();

        for (WebElement trigger : triggersSelected){
            Assert.assertTrue(trigger.isEnabled());
        }
        myPage.clickSaveButton();

    }
}
