package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FolderTest extends BaseTest {
    private static final String FOLDER_NAME = "best folder in the world";

    @Test(testName = "Создание Folder")
    public void testCreateFolder() throws InterruptedException {
        WebElement createJobButton = getDriver().findElement(By.cssSelector("a[href='newJob']"));
        createJobButton.click();

        WebElement inputName = getDriver().findElement(By.cssSelector("input#name"));
        inputName.sendKeys(FOLDER_NAME);

        WebElement folder = getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder"));
        folder.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.cssSelector("button[value='Save']"));
        saveButton.click();

        Thread.sleep(1500);

        WebElement logo = getDriver().findElement(By.className("app-jenkins-logo"));
        logo.click();

        Thread.sleep(1500);

        WebElement createdFolder = getDriver().findElement(By.xpath("//span[text()='" + FOLDER_NAME + "']"));
        Assert.assertNotNull(createdFolder);
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

        Assert.assertEquals(getDriver().findElement(By.id("description-content")).getText(),FOLDER_NAME);
    }
}
