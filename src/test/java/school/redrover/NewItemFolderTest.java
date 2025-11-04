package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class NewItemFolderTest extends BaseTest {

    public WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
    @Test
    public void testCreateFolder(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        WebElement inputField = getDriver().findElement(By.id("name"));
        String folderName = "My Folder Name";
        wait.until(ExpectedConditions.visibilityOf(inputField));
        inputField.sendKeys(folderName);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();

        WebElement newFolder = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName)));
        Assert.assertTrue(newFolder.isDisplayed());
    }
    @Test
    public void testFolderIsEmpty(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        WebElement inputField = getDriver().findElement(By.id("name"));
        String folderName = "My Folder Name";
        wait.until(ExpectedConditions.visibilityOf(inputField));
        inputField.sendKeys(folderName);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName))).click();

        String actualContext = getDriver().findElement(By.xpath("//h2[text()='This folder is empty']")).getText();
        String expectedContext = "This folder is empty";
        Assert.assertEquals(actualContext,expectedContext);
    }
    @Test
    public void testCreateJobToFolder(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        WebElement inputField = getDriver().findElement(By.id("name"));
        String folderName = "My Folder Name";
        wait.until(ExpectedConditions.visibilityOf(inputField));
        inputField.sendKeys(folderName);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();

        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName))).click();
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        inputField = getDriver().findElement(By.id("name"));
        String freestyleJob = "new freestyle job";
        wait.until(ExpectedConditions.visibilityOf(inputField));
        inputField.sendKeys(freestyleJob);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName))).click();
        WebElement freestyleJobElement = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(freestyleJob)));
        Assert.assertTrue(freestyleJobElement.isDisplayed());
    }
    @Test
    public void testAddNewItemToFolder(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        WebElement inputField = getDriver().findElement(By.id("name"));
        String folderName = "My Folder Name";
        wait.until(ExpectedConditions.visibilityOf(inputField));
        inputField.sendKeys(folderName);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();

        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName))).click();
        getDriver().findElement(By.xpath("//span[text()='New Item']/..")).click();
        inputField = getDriver().findElement(By.id("name"));
        String freestyleJob = "new freestyle job";
        wait.until(ExpectedConditions.visibilityOf(inputField));
        inputField.sendKeys(freestyleJob);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName))).click();
        WebElement freestyleJobElement = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(freestyleJob)));
        Assert.assertTrue(freestyleJobElement.isDisplayed());
    }
    @Test
    public void testSameNameItemsInDiffFolders(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        WebElement inputField = getDriver().findElement(By.id("name"));
        String folderName1 = "Folder1";
        wait.until(ExpectedConditions.visibilityOf(inputField));
        inputField.sendKeys(folderName1);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName1))).click();
        getDriver().findElement(By.xpath("//span[text()='New Item']/..")).click();
        inputField = getDriver().findElement(By.id("name"));
        String pipeline = "pipeline1";
        wait.until(ExpectedConditions.visibilityOf(inputField));
        inputField.sendKeys(pipeline);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();

        getDriver().findElement(By.xpath("//span[text()='New Item']/..")).click();
        inputField = getDriver().findElement(By.id("name"));
        String folderName2 = "Folder2";
        wait.until(ExpectedConditions.visibilityOf(inputField));
        inputField.sendKeys(folderName2);
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName2))).click();
        getDriver().findElement(By.xpath("//span[text()='New Item']/..")).click();
        inputField = getDriver().findElement(By.id("name"));
        wait.until(ExpectedConditions.visibilityOf(inputField));
        inputField.sendKeys(pipeline);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();

        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName1))).click();
        String folder1pipeline = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(pipeline))).getText();

        getDriver().findElement(By.xpath("//span[@class='jenkins-mobile-hide']")).click();
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName2))).click();
        String folder2pipeline = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(pipeline))).getText();

        Assert.assertEquals(folder1pipeline,folder2pipeline);
    }
}
