package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.FolderPage;
import school.redrover.page.HomePage;

import java.util.List;

public class FolderTest extends BaseTest {
    private static final String FOLDER_NAME = "TestFolder";
    private static final String CHILD_FOLDER_NAME = "ChildFolder";

    @Test
    public void testCreate() {
        List<String> projectList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertEquals(projectList.get(0), FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testNewFolderDefaultAddedToExistingFolder() {
        List<String> childFolderBreadcrumbList = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickSidebarNewItem()
                .sendName(CHILD_FOLDER_NAME)
                .selectFolderAndSubmit()
                .clickSave()
                .getBreadcrumbTexts();

        Assert.assertEquals(
                childFolderBreadcrumbList,
                List.of(FOLDER_NAME, CHILD_FOLDER_NAME),
                "Путь хлебных крошек не соответствует ожидаемому");
    }

    @Test(dependsOnMethods = "testNewFolderDefaultAddedToExistingFolder")
    public void testPreventDuplicateItemNamesInFolder() {
        String duplicateErrorMessage = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickSidebarNewItem()
                .sendName(CHILD_FOLDER_NAME)
                .selectFolder()
                .getDuplicateErrorMessage();

        Assert.assertEquals(
                duplicateErrorMessage,
                "» A job already exists with the name ‘%s’".formatted(CHILD_FOLDER_NAME),
                "Неверное сообщение о дублировании имени");
    }

    @Test(dependsOnMethods = "testPreventDuplicateItemNamesInFolder")
    public void deleteFolder() {
        boolean isFolderDeleted = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .openFolderPage(CHILD_FOLDER_NAME)
                .clickDeleteFolder()
                .confirmDeleteChild()
                .gotoHomePage()
                .clickSearchButton()
                .searchFor(CHILD_FOLDER_NAME)
                .isNoResultsFound(CHILD_FOLDER_NAME);

        Assert.assertTrue(isFolderDeleted,
                "%s не должна отображаться в поиске после удаления".formatted(CHILD_FOLDER_NAME));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testAddDescriptionToFolder() {
        final String descriptionText = "Folder description";

        String actualDescription = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickAddDescriptionButton()
                .addDescriptionAndSave(descriptionText)
                .getDescription();

        Assert.assertEquals(
                actualDescription,
                descriptionText,
                "Описание папки не соответствует ожидаемому");
    }
}
