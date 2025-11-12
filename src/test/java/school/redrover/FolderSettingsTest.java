package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;

public class FolderSettingsTest extends BaseTest {

    private static final String FOLDER_NAME = "Just folder";
    private static final String DISPLAY_NAME = "Look at this";

    @Test
    public void testDisplayName() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit()
                .setDisplayName(DISPLAY_NAME)
                .clickSave()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertEquals(projectList.get(0), DISPLAY_NAME);
    }
}
