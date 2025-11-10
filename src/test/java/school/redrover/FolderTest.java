package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    @Test
    public void testAddNewFolder(){

        String folder = "NewFolder";

        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1)")).click();
        getDriver().findElement((By.id("name"))).sendKeys(folder);
        getDriver().findElement(By.cssSelector("#j-add-item-type-nested-projects > ul > :nth-child(1)")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.cssSelector("#page-header > div.jenkins-header__main > div > a > span")));
        getDriver().findElement(By.cssSelector("#page-header > div.jenkins-header__main > div > a > span")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#job_NewFolder > td:nth-child(3) > a > span")).getText(), folder);
    }
}
