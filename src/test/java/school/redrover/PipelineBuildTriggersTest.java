package school.redrover;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;


public class PipelineBuildTriggersTest extends BaseTest {
    private static final String pipelineName = "pipeline_name";

    @Test
    public void testSelectTriggers() {
        WebElement[] triggersSelected =
                new HomePage(getDriver()).clickNewItemOnLeftMenu()
                .sendName(pipelineName)
                .selectPipelineAndSubmit()
                .selectAllTriggers();

        for (WebElement trigger : triggersSelected){
            Assert.assertTrue(trigger.isEnabled());
        }
        Assert.assertNotNull(triggersSelected);
    }
}
