package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.FolderPage;
import school.redrover.page.HomePage;
import school.redrover.page.RenameFolderPage;

import java.util.Arrays;
import java.util.List;

public class FolderTest extends BaseTest {
    private static final String FOLDER_NAME = "TestFolder";
    private static final String SUB_FOLDER_NAME = "SubFolder";
    private static final String FOLDER_NAME_2 = "Folder2";

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
                .sendName(SUB_FOLDER_NAME)
                .selectFolderAndSubmit()
                .clickSave()
                .getBreadcrumbTexts();

        Assert.assertEquals(
                childFolderBreadcrumbList,
                List.of(FOLDER_NAME, SUB_FOLDER_NAME),
                "Путь хлебных крошек не соответствует ожидаемому");
    }

    @Test(dependsOnMethods = "testNewFolderDefaultAddedToExistingFolder")
    public void testPreventDuplicateItemNamesInFolder() {
        String duplicateErrorMessage = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickSidebarNewItem()
                .sendName(SUB_FOLDER_NAME)
                .selectFolder()
                .getDuplicateErrorMessage();

        Assert.assertEquals(
                duplicateErrorMessage,
                "» A job already exists with the name ‘%s’".formatted(SUB_FOLDER_NAME),
                "Неверное сообщение о дублировании имени");
    }

    @Test(dependsOnMethods = "testPreventDuplicateItemNamesInFolder")
    public void deleteFolderBySidebar() {
        boolean isFolderDeleted = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .openFolderPage(SUB_FOLDER_NAME)
                .clickDeleteFolder()
                .confirmDeleteChildItem()
                .clickSearchButton()
                .searchFor(SUB_FOLDER_NAME)
                .isNoResultsFound(SUB_FOLDER_NAME);

        Assert.assertTrue(isFolderDeleted,
                "%s не должна отображаться в поиске после удаления".formatted(SUB_FOLDER_NAME));
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

    @Test(dependsOnMethods = "testCreate")
    public void testSameItemNamesInTwoFolders() {
        final String pipelineName = "TwoPipelines";

        List<String> jobsInFirstFolder = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickSidebarNewItem()
                .sendName(pipelineName)
                .selectPipelineAndSubmit()
                .gotoHomePage()
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .getProjectList();

        List<String> jobsInSecondFolder = new HomePage(getDriver())
                .gotoHomePage()
                .clickSidebarNewItem()
                .sendName(FOLDER_NAME_2)
                .selectFolderAndSubmit()
                .clickSave()
                .clickSidebarNewItem()
                .sendName(pipelineName)
                .selectPipelineAndSubmit()
                .gotoHomePage()
                .openJobPage(FOLDER_NAME_2, new FolderPage(getDriver()))
                .getProjectList();

        Assert.assertTrue(jobsInFirstFolder.contains(pipelineName),
                "Пайплайн '%s' должен присутствовать в первой папке '%s'".formatted(pipelineName, FOLDER_NAME));
        Assert.assertTrue(jobsInSecondFolder.contains(pipelineName),
                "Пайплайн '%s' должен присутствовать во второй папке '%s'".formatted(pipelineName, FOLDER_NAME_2));
    }

    @Test(dependsOnMethods = "testSameItemNamesInTwoFolders")
    public void deleteFolderByDashboardDropdownMenu() {
        boolean isFolderDeleted = new HomePage(getDriver())
                .openDropdownMenu(FOLDER_NAME_2)
                .clickDeleteItemInDropdownMenu()
                .confirmDelete()
                .clickSearchButton()
                .searchFor(FOLDER_NAME_2)
                .isNoResultsFound(FOLDER_NAME_2);

        Assert.assertTrue(isFolderDeleted,
                "%s не должна отображаться в поиске после удаления".formatted(FOLDER_NAME_2));
    }

    @Test(dependsOnMethods = {"testCreate","deleteFolderBySidebar"})
    public void testPutItemsToFolder() {
        final Object[][] items = {
                {SUB_FOLDER_NAME, "Folder"},
                {"SubFreestyleProject", "Freestyle project"},
                {"SubMultibranchPipeline", "Multibranch Pipeline"},
                {"SubMulticonfigurationProject", "Multi-configuration project"},
                {"SubOrganizationFolder", "Organization Folder"},
                {"SubPipeline", "Pipeline"}
        };
        List<String> expectedItems = Arrays.stream(items)
                .map(item -> (String) item[0])
                .toList();

        for (Object[] item : items) {
            String itemName = (String) item[0];
            String itemType = (String) item[1];
            new HomePage(getDriver())
                    .clickSidebarNewItem()
                    .sendName(itemName)
                    .selectItemTypeAndSubmitAndGoHome(itemType)
                    .openDropdownMenu(itemName)
                    .clickMoveInDropdownMenu()
                    .selectDestinationFolder(FOLDER_NAME)
                    .clickMoveButtonAndGoHome();
        }

        List<String> folderItemList = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME)
                .getProjectList();

        Assert.assertTrue(folderItemList.size() >= expectedItems.size(),
                "В папке должно быть как минимум %s элементов".formatted(expectedItems.size()));
        Assert.assertTrue(folderItemList.containsAll(expectedItems),
                "В папке должны быть все перенесенные элементы: " + expectedItems);
    }

    @Test(dependsOnMethods = "testPutItemsToFolder")
    public void testFolderIsIdentifiedByIcon() {
        FolderPage folderPage = new HomePage(getDriver())
                .clickFolder(FOLDER_NAME);
        String folderIconAttribute = folderPage.getFolderIconAttribute(SUB_FOLDER_NAME);
        List<String> itemsWithIconAttribute = folderPage.getItemsWithIconAttribute(folderIconAttribute);

        Assert.assertNotEquals(itemsWithIconAttribute.size(), 0);
        Assert.assertEquals(
                itemsWithIconAttribute,
                List.of(SUB_FOLDER_NAME),
                "Ошибка в отображении иконок");
    }
    @Ignore
    @Test (dependsOnMethods = "testCreate")
    public void testRenameFolder(){

        HomePage homePage = new HomePage(getDriver());
                String newNameFolder = homePage.openDropdownMenu(FOLDER_NAME)
                .clickRenameItemInDropdownMenu()
                .clearName()
                .sendNewName(FOLDER_NAME_2)
                .renameButtonClick()
                .getNameFolder();

        Assert.assertEquals(newNameFolder, FOLDER_NAME_2);
    }
}
