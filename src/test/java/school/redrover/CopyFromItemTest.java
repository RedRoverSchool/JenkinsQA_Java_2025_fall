package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.FreestyleProjectConfigurationPage;
import school.redrover.page.FreestyleProjectPage;
import school.redrover.page.HomePage;
import java.util.List;

public class CopyFromItemTest extends BaseTest {

    @Test
    public void testCopyFromItem() {
        final String originalName = "originalProject";
        final String newName = "newProject";
        final String descriptionText = "RedRover is the best";
        final String daysToKeepText = "10";
        final String maxOfBuildsText = "20";
        final String gitHubUrl = "git.com/testUrl";
        final String buildsUrl = "example.com/test";

        List <String> originalItemSettingsList =
                List.of(descriptionText, daysToKeepText, maxOfBuildsText, gitHubUrl, buildsUrl);

        List<String> copiedItemSettingsList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(originalName)
                .selectFreestyleProjectAndSubmit()
                .setDescription(descriptionText)
                .setCheckBoxDiscardAndSetDaysNum(daysToKeepText, maxOfBuildsText)
                .setCheckBoxGitHubAndSendUrl(gitHubUrl)
                .setCheckBoxTriggerBuildsAndSendUrl(buildsUrl)
                .clickSave()
                .gotoHomePage()
                .clickNewItemOnLeftMenu()
                .sendName(newName)
                .sendNameToCopyFromAndSubmit(originalName)
                .gotoHomePage()
                .openJobPage(newName, new FreestyleProjectPage(getDriver()))
                .clickConfigure(newName)
                .getSettingsToList();

        Assert.assertEquals(originalItemSettingsList, copiedItemSettingsList);
    }
}
