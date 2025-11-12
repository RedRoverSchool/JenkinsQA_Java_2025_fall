package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;

public class Folder100Test extends BaseTest {

    @Test
    public void testCreate() {
        final String folderName = "Test Folder";

        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .sendName(folderName)
                .selectFolderAndSubmit()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertEquals(projectList.get(0), folderName);
    }

}