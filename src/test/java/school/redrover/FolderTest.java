package school.redrover;

import org.openqa.selenium.By;
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
                .openJobPage(CHILD_FOLDER_NAME, new FolderPage(getDriver()))
                .clickDeleteFolder()
                .confirmDelete(new FolderPage(getDriver()))
                .gotoHomePage()
                .clickSearchButton()
                .searchFor(CHILD_FOLDER_NAME)
                .isNoResultsFound(CHILD_FOLDER_NAME);

        Assert.assertTrue(isFolderDeleted,
                "%s не должна отображаться в поиске после удаления".formatted(CHILD_FOLDER_NAME));
    }

    @Test(testName = "Добавление описания к Folder")
    public void testAddDescriptionToFolder() throws InterruptedException {
        getDriver().findElement(By.cssSelector("a[href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).
                sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']"))
                .click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@class='jenkins-input   ']")).
                sendKeys(FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Thread.sleep(1500);

        Assert.assertEquals(getDriver().findElement(By.id("description-content")).getText(), FOLDER_NAME);
    }
}
