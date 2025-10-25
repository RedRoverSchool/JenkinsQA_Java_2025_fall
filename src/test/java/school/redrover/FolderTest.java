package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FolderTest extends BaseTest {

    @Test(testName = "Создание Folder")
    public void testCreateFolder() throws InterruptedException {
        final String folderName = "best folder in the world";

        WebElement createJobButton = getDriver().findElement(By.cssSelector("a[href='newJob']"));
        createJobButton.click();

        WebElement inputName = getDriver().findElement(By.cssSelector("input#name"));
        inputName.sendKeys(folderName);

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

        WebElement createdFolder = getDriver().findElement(By.xpath("//span[text()='" + folderName + "']"));
        Assert.assertNotNull(createdFolder);
    }
}
