package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.time.Duration;

public class PipelineBuildTriggersTest extends BaseTest {
    private static final String pipelineName = "pipeline_name";

    @Test
    public void testSelectTriggers() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickNewItemOnLeftMenu()
                .sendName(pipelineName)
                        .selectPipelineAndSubmit()
                .selectAllTriggersWithAssert()
                        .clickSaveButton();
    }
}
