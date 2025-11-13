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

    @Test
    public void testCreate() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertEquals(projectList.get(0), FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testNewFolderDefaultAddedToExistingFolder() {
        final String childFolderName = "ChildFolder";

        FolderPage childFolderPage = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickNewItem()
                .sendName(childFolderName)
                .selectFolderAndSubmit()
                .clickSave();

        Assert.assertEquals(
                childFolderPage.getBreadcrumbTexts(),
                List.of(FOLDER_NAME, childFolderName),
                "Путь хлебных крошек не соответствует ожидаемому");
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
