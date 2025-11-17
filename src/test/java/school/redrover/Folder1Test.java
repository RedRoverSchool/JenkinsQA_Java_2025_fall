package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class Folder1Test extends BaseTest {

    @Test
    public void testCreateFolder() {
        final String folderName = "my folder";

        WebElement createJobButton = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        createJobButton.click();

        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'General']")));

        getDriver().findElement(By.cssSelector("span.jenkins-mobile-hide")).click();

        WebElement jobRecordCell = getDriver().findElement(By.cssSelector(".jenkins-table__link >span:first-child"));
        Assert.assertEquals(jobRecordCell.getText(), folderName);
    }

    @Test
    public void testPutFolderToFolder() {
        final String folder1Name = "folder1";
        final String folder2Name = "folder2";

        // create folder1
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folder1Name);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'General']")));
        getDriver().findElement(By.cssSelector("span.jenkins-mobile-hide")).click();

        // create folder2
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folder2Name);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'General']")));
        getDriver().findElement(By.cssSelector("span.jenkins-mobile-hide")).click();

        getDriver().findElement(By.xpath("//a[@href='job/%s/']".formatted(folder2Name))).click();
        getDriver().findElement(By.xpath("//a[@href='/job/%s/move']".formatted(folder2Name))).click();

        Select selectObject = new Select(getDriver().findElement(By.className("jenkins-select__input")));
        selectObject.selectByVisibleText("Jenkins » %s".formatted(folder1Name));

        getDriver().findElement(By.name("Submit")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class = 'content-block__link']")));

        List<WebElement> list = getDriver().findElements(By.cssSelector(".jenkins-breadcrumbs__list-item > a"));
        Assert.assertEquals(list.get(0).getText(), folder1Name);
        Assert.assertEquals(list.get(1).getText(), folder2Name);
    }
}
