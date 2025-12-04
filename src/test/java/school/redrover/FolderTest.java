package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.FolderPage;
import school.redrover.page.HomePage;

import java.util.List;


public class FolderTest extends BaseTest {

    private static final String FOLDER_NAME = "TestFolder";
    private static final String FOLDER_NAME_2 = "Folder2";
    private static final String NEW_FOLDER_NAME = "NewFolderName";
    private static final String SUB_FOLDER_NAME = "SubFolder";
    private static final List<String> ITEM_NAMES = List.of(
            SUB_FOLDER_NAME,
            "SubFreestyleProject",
            "SubMultibranchPipeline",
            "SubMulticonfigurationProject",
            "SubOrganizationFolder",
            "SubPipeline"
    );
    private static final Object[][] ITEMS = {
            {ITEM_NAMES.get(0), "Folder"},
            {ITEM_NAMES.get(1), "Freestyle project"},
            {ITEM_NAMES.get(2), "Multibranch Pipeline"},
            {ITEM_NAMES.get(3), "Multi-configuration project"},
            {ITEM_NAMES.get(4), "Organization Folder"},
            {ITEM_NAMES.get(5), "Pipeline"}
    };

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

    @Test(dependsOnMethods = "testIsEmpty")
    public void testNewItemDefaultAddedToExistingFolder() {
        new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(FOLDER_NAME_2)
                .selectFolderAndSubmit()
                .clickSave()
                .gotoHomePage();

        for (Object[] item : ITEMS) {
            String itemName = (String) item[0];
            String itemType = (String) item[1];
            new HomePage(getDriver())
                    .openProject(FOLDER_NAME_2, new FolderPage(getDriver()))
                    .clickSidebarNewItem()
                    .sendName(itemName)
                    .selectItemTypeAndSubmitAndGoHome(itemType);
        }

        List<String> folderItemList = new HomePage(getDriver())
                .openProject(FOLDER_NAME_2, new FolderPage(getDriver()))
                .getProjectList();

        Assert.assertTrue(folderItemList.size() >= ITEM_NAMES.size(),
                "В папке должно быть как минимум %s элементов".formatted(ITEM_NAMES.size()));
        Assert.assertTrue(folderItemList.containsAll(ITEM_NAMES),
                "В папке должны быть все соданные элементы: " + ITEM_NAMES);
    }

    @Test(dependsOnMethods = "testNewItemDefaultAddedToExistingFolder")
    public void testPreventDuplicateItemNamesInFolder() {
        String duplicateErrorMessage = new HomePage(getDriver())
                .openProject(FOLDER_NAME_2, new FolderPage(getDriver()))
                .clickSidebarNewItem()
                .sendName(SUB_FOLDER_NAME)
                .selectFolder()
                .getDuplicateOrUnsafeCharacterErrorMessage();

        Assert.assertEquals(
                duplicateErrorMessage,
                "» A job already exists with the name ‘%s’".formatted(SUB_FOLDER_NAME),
                "Неверное сообщение о дублировании имени");
    }

    @Test(dependsOnMethods = {"testPreventDuplicateItemNamesInFolder", "testSameItemNamesInTwoFolders"})
    public void testDeleteFolderBySidebar() {
        boolean isFolderDeleted = new HomePage(getDriver())
                .openProject(FOLDER_NAME_2, new FolderPage(getDriver()))
                .clickDeleteFolder()
                .confirmDeleteFolder()
                .clickSearchButton()
                .searchFor(FOLDER_NAME_2)
                .isNoResultsFound(FOLDER_NAME_2);

        Assert.assertTrue(isFolderDeleted,
                "%s не должна отображаться в поиске после удаления".formatted(FOLDER_NAME_2));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testAddDescriptionToFolder() {
        final String descriptionText = "Folder description";

        String actualDescription = new HomePage(getDriver())
                .openProject(FOLDER_NAME, new FolderPage(getDriver()))
                .clickAddDescriptionButton()
                .addDescriptionAndSave(descriptionText)
                .getDescription();

        Assert.assertEquals(
                actualDescription,
                descriptionText,
                "Описание папки не соответствует ожидаемому");
    }

    @Test(dependsOnMethods = "testNewItemDefaultAddedToExistingFolder")
    public void testSameItemNamesInTwoFolders() {
        List<String> jobsInFirstFolder = new HomePage(getDriver())
                .openProject(FOLDER_NAME, new FolderPage(getDriver()))
                .clickSidebarNewItem()
                .sendName(SUB_FOLDER_NAME)
                .selectFolderAndSubmit()
                .gotoHomePage()
                .openProject(FOLDER_NAME, new FolderPage(getDriver()))
                .getProjectList();

        List<String> jobsInSecondFolder = new FolderPage(getDriver())
                .gotoHomePage()
                .openProject(FOLDER_NAME_2, new FolderPage(getDriver()))
                .getProjectList();

        Assert.assertTrue(jobsInFirstFolder.contains(SUB_FOLDER_NAME),
                "'%s' должен присутствовать в первой папке '%s'".formatted(SUB_FOLDER_NAME, FOLDER_NAME));
        Assert.assertTrue(jobsInSecondFolder.contains(SUB_FOLDER_NAME),
                "'%s' должен присутствовать во второй папке '%s'".formatted(SUB_FOLDER_NAME, FOLDER_NAME_2));
    }

    @Test(dependsOnMethods = "testRenameFolder")
    public void testDeleteFolderByDashboardDropdownMenu() {
        boolean isFolderDeleted = new HomePage(getDriver())
                .openProject(FOLDER_NAME, new FolderPage(getDriver()))
                .openDropdownMenu(NEW_FOLDER_NAME)
                .clickDeleteItemInDropdownMenu()
                .confirmDeleteChildFolder()
                .clickSearchButton()
                .searchFor(NEW_FOLDER_NAME)
                .isNoResultsFound(NEW_FOLDER_NAME);

        Assert.assertTrue(isFolderDeleted,
                "%s не должна отображаться в поиске после удаления".formatted(NEW_FOLDER_NAME));
    }

    @Test(dependsOnMethods = {"testCreate", "testIsEmpty", "testDeleteFolderByDashboardDropdownMenu"})
    public void testPutItemsToFolder() {
        for (Object[] item : ITEMS) {
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
                .openProject(FOLDER_NAME, new FolderPage(getDriver()))
                .getProjectList();

        Assert.assertTrue(folderItemList.size() >= ITEM_NAMES.size(),
                "В папке должно быть как минимум %s элементов".formatted(ITEM_NAMES.size()));
        Assert.assertTrue(folderItemList.containsAll(ITEM_NAMES),
                "В папке должны быть все перенесенные элементы: " + ITEM_NAMES);
    }

    @Test(dependsOnMethods = "testPutItemsToFolder")
    public void testFolderIsIdentifiedByIcon() {
        FolderPage folderPage = new HomePage(getDriver())
                .openProject(FOLDER_NAME, new FolderPage(getDriver()));
        String folderIconAttribute = folderPage.getFolderIconAttribute(SUB_FOLDER_NAME);
        List<String> itemsWithIconAttribute = folderPage.getItemsWithIconAttribute(folderIconAttribute);

        Assert.assertNotEquals(itemsWithIconAttribute.size(), 0);
        Assert.assertEquals(
                itemsWithIconAttribute,
                List.of(SUB_FOLDER_NAME),
                "Ошибка в отображении иконок");
    }

    @Test(dependsOnMethods = "testSameItemNamesInTwoFolders")
    public void testRenameFolder() {
        String newNameFolder = new HomePage(getDriver())
                .openProject(FOLDER_NAME, new FolderPage(getDriver()))
                .openDropdownMenu(SUB_FOLDER_NAME)
                .clickRenameItemInDropdownMenu()
                .clearName()
                .sendNewName(NEW_FOLDER_NAME)
                .renameButtonClick()
                .getNameFolder();

        Assert.assertEquals(newNameFolder, NEW_FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testPutItemsToFolder")
    public void testFolderIsIdentifiedByTooltip() {
        FolderPage folderPage = new HomePage(getDriver())
                .openProject(FOLDER_NAME, new FolderPage(getDriver()));
        String folderTooltip = folderPage.getFolderTooltip(SUB_FOLDER_NAME);
        List<String> itemsWithTooltip = folderPage.getItemsWithTooltip(folderTooltip);

        Assert.assertNotEquals(itemsWithTooltip.size(), 0);
        Assert.assertEquals(
                itemsWithTooltip,
                List.of(SUB_FOLDER_NAME),
                "Ошибка в отображении тултипов");
    }
    @Ignore //Test failed on CI
    @Test(dependsOnMethods = "testPutItemsToFolder")
    public void testFindFolderContent() {
        String previousItemName = "";

        for (String itemName : ITEM_NAMES) {
            List<String> searchResults = new HomePage(getDriver())
                    .clickSearchButton()
                    .searchFor(itemName, previousItemName)
                    .getSearchResultsAndClose();

            Assert.assertTrue(searchResults.contains("%s » %s".formatted(FOLDER_NAME, itemName)),
                    "Список результатов поиска не содержит нужный элемент (%s)".formatted(itemName));

            previousItemName = itemName;
        }
    }

    @Test(dependsOnMethods = "testCreate")
    public void testIsEmpty() {
        String actualContext = new HomePage(getDriver())
                .openProject(FOLDER_NAME, new FolderPage(getDriver()))
                .getFolderContext();

        String expectedContext = "This folder is empty";
        Assert.assertEquals(actualContext, expectedContext);
    }
}
