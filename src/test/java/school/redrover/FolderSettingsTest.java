package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.FolderPage;
import school.redrover.page.HomePage;


import java.util.List;


public class FolderSettingsTest extends BaseTest {

    private static final String FOLDER_NAME = "Just folder";
    private static final String DISPLAY_NAME = "Look at this";

    @Test
    public void testDisplayName() {
        List<String> projectList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit()
                .setDisplayName(DISPLAY_NAME)
                .clickSave()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertEquals(projectList.get(0), DISPLAY_NAME);
    }

    @Test(dependsOnMethods = "testDisplayName")
    public void testFolderInfo() {
        final String description = "This is lorem text... or not.";
        final List<String> expectedInfo = List.of(DISPLAY_NAME, description);

        FolderPage.FolderInfo actualInfo = new HomePage(getDriver())
                .clickFolder(DISPLAY_NAME)
                .clickConfigureLinkInSideMenu()
                .setDescription(description)
                .clickSave()
                .getInfo();

        Assert.assertTrue(expectedInfo.containsAll(List.of(actualInfo.getDisplayName(), actualInfo.getDescription())));
    }
}
