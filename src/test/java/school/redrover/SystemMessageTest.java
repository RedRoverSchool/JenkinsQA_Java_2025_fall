package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.ConfigurationSystemPage;
import school.redrover.page.HomePage;

public class SystemMessageTest extends BaseTest {

    private static final String EXPECTED_MESSAGE = "This is the best project!";

    @Test
    public void testPreview() {
        String message = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickConfigurationSystem()
                .setSystemMessage(EXPECTED_MESSAGE)
                .getPreviewSystemMessage();

        new ConfigurationSystemPage(getDriver()).clickSave();

        Assert.assertEquals(message, EXPECTED_MESSAGE);
    }

    @Test(dependsOnMethods = "testPreview")
    public void testCreateMessage() {
        String message = new HomePage(getDriver())
                .getSystemMessage();

        Assert.assertEquals(message, EXPECTED_MESSAGE);
    }
}
