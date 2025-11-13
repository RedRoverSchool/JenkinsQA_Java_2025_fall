package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.FolderPage;
import school.redrover.page.HomePage;

import java.util.List;

public class Folder10Test extends BaseTest {

    private static final String FOLDER_NAME = "Folder_name";

    @Test
    public void testCreate() {
        List<String> myList = new HomePage(getDriver())
                .clickNewItem()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(myList.size(), 0);
        Assert.assertEquals(myList.get(0), FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testIsEmpty() {
        String actualContext = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .getFolderContext();

        String expectedContext = "This folder is empty";
        Assert.assertEquals(actualContext, expectedContext);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testCreateJobToFolder() {
        final String newJob = "multibrunch pipeline";

        WebElement jobCreated = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickCreateJob()
                .sendName(newJob)
                .selectMultibranchPipelineAndSubmit()
                .gotoHomePage()
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .getElement(newJob);

        Assert.assertTrue(jobCreated.isDisplayed());
    }

    @Test(dependsOnMethods = "testCreate")
    public void testAddNewItemToFolder() {
        final String newJob = "multibrunch pipeline";

        WebElement jobCreated = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickNewItem()
                .sendName(newJob)
                .selectMultibranchPipelineAndSubmit()
                .gotoHomePage()
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .getElement(newJob);

        Assert.assertTrue(jobCreated.isDisplayed());
    }

    @Test
    public void testSameNameItemsInDiffFolders() {
        final String folderName1 = "Folder1";
        final String folderName2 = "Folder2";
        final String multibrunchPipeline = "pipeline1";

        String sameNamedJobInFirstFolder = new HomePage(getDriver())
                .clickNewItem()
                .sendName(folderName1)
                .selectFolderAndSubmit()
                .gotoHomePage()
                .openJobPage(folderName1, new FolderPage(getDriver()))
                .clickCreateJob()
                .sendName(multibrunchPipeline)
                .selectMultibranchPipelineAndSubmit()
                .gotoHomePage()
                .openJobPage(folderName1, new FolderPage(getDriver()))
                .getElement(multibrunchPipeline)
                .getText();

        String sameNamedJobInSecondFolder = new HomePage(getDriver())
                .gotoHomePage()
                .clickNewItem()
                .sendName(folderName2)
                .selectFolderAndSubmit()
                .gotoHomePage()
                .openJobPage(folderName2, new FolderPage(getDriver()))
                .clickCreateJob()
                .sendName(multibrunchPipeline)
                .selectMultibranchPipelineAndSubmit()
                .gotoHomePage()
                .openJobPage(folderName2, new FolderPage(getDriver()))
                .getElement(multibrunchPipeline)
                .getText();

        Assert.assertEquals(sameNamedJobInFirstFolder, sameNamedJobInSecondFolder);
    }
}
