package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;

public class CopyFieldTest extends BaseTest {

    private static final String FOLDER_NAME = "MY_FIRST_FOLDER";

    @Test
    public void testCreateFolder() {

        List<String> projectList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit()
                .clickSave()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertEquals(projectList.get(0), FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void testLocateTextHintAndCopyFromField() {

        String textHintExists = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .findCopyFromField()
                .getTextHintFromCopyField();

        Assert.assertEquals(textHintExists, "If you want to create a new item from other existing, you can use this option:");
    }
}

