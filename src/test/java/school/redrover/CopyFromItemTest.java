package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.ConfigurationFreestyleProjectPage;
import school.redrover.page.HomePage;
import java.util.List;

public class CopyFromItemTest extends BaseTest {

    @Test
    public void testCopyFromItem() {
        final String firstJobName = "original_project";
        final String secondJobName = "new_project";
        final String descriptionText = "RedRover is the best";
        final String daysToKeepText = "10";
        final String maxOfBuildsText = "20";
        final String gitHubUrl = "git.com/testUrl";
        final String buildsUrl = "example.com/test";

        HomePage homePage = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(firstJobName)
                .selectFreestyleProjectAndSubmit()
                .setDescription(descriptionText)
                .setCheckBoxDiscardAndSetDaysNum(daysToKeepText, maxOfBuildsText)
                .setCheckBoxGitHubAndSendUrl(gitHubUrl)
                .setCheckBoxTriggerBuildsAndSendUrl(buildsUrl)
                .clickSave()
                .gotoHomePage()
                .clickNewItemOnLeftMenu()
                .sendNewNameAndOriginalNameAndSubmit(secondJobName, firstJobName);

        ConfigurationFreestyleProjectPage configurationFreestyleProjectPage = new ConfigurationFreestyleProjectPage(getDriver());
        List <String> copiedItemSettingsList = configurationFreestyleProjectPage.getSomeSettingsToList();

        List <String> originalItemSettingsList =
                List.of(descriptionText, daysToKeepText, maxOfBuildsText, gitHubUrl, buildsUrl);

        Assert.assertEquals(originalItemSettingsList, copiedItemSettingsList);









    }
}
