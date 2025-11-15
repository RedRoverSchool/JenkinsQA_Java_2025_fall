package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.NewItemPage;

import java.util.List;

public class CreateFolderFromCopy extends BaseTest {

    private static final String FOLDER_NAME = "MY_FIRST_FOLDER";

    @Test
    public void createFolder() {
        final String folderName = "My First Folder";

        HomePage homePage = new HomePage(getDriver());
        List<String> projectList = homePage.clickCreateJob()
                .sendName(folderName)
                .selectFolderAndSubmit()
                .clickSave()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertEquals(projectList.get(0), folderName);
    }

    @Test(dependsOnMethods = "createFolder")
    public void testLocateTextHintAndCopyFromField() {
        boolean textHintExists = true;
        boolean copyFromFieldExists = true;

        HomePage homePage = new HomePage(getDriver());
        homePage.clickNewItemOnLeftMenu()
                .getTextHintFromCopyField();

        NewItemPage newItemPage = new NewItemPage(getDriver());
        newItemPage.CopyFromField();


        Assert.assertEquals(textHintExists, true);
        Assert.assertEquals(copyFromFieldExists, true);
    }
}

