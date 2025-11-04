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
    public void clickMethod(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    public void sendKeysMethod(WebElement element, String text){
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
    }
    public void createNewFolderMethod(String folderName){
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='New Item']/.."))
        );
        sendKeysMethod(
                getDriver().findElement(By.id("name")),
                folderName
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Folder']"))
        );
        clickMethod(
                getDriver().findElement(By.id("ok-button"))
        );
        clickMethod(
                getDriver().findElement(By.name("Submit"))
        );
    }

    @Test
    public void testCreateFolder(){
        String folderName = "My Folder Name";
        createNewFolderMethod(folderName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Jenkins']"))
        );

        WebElement newFolder = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName)));
        Assert.assertTrue(newFolder.isDisplayed());
    }
    @Test
    public void testFolderIsEmpty(){
        String folderName = "My Folder Name";
        createNewFolderMethod(folderName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Jenkins']"))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName)))
        );

        String actualContext = getDriver().findElement(By.xpath("//h2[text()='This folder is empty']")).getText();
        String expectedContext = "This folder is empty";
        Assert.assertEquals(actualContext,expectedContext);
    }
    @Test
    public void testCreateJobToFolder(){
        String folderName = "My Folder Name";
        createNewFolderMethod(folderName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Jenkins']"))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName)))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//a[@href='newJob']"))
        );
        String freestyleJob = "new freestyle job";
        sendKeysMethod(
                getDriver().findElement(By.id("name")),freestyleJob
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Freestyle project']"))
        );
        clickMethod(
                getDriver().findElement(By.id("ok-button"))
        );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Jenkins']"))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName)))
        );
        WebElement freestyleJobElement = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(freestyleJob)));
        Assert.assertTrue(freestyleJobElement.isDisplayed());
    }
    @Test
    public void testAddNewItemToFolder(){
        String folderName = "My Folder Name";
        createNewFolderMethod(folderName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Jenkins']"))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName)))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='New Item']/.."))
        );
        String freestyleJob = "new freestyle job";
        sendKeysMethod(
                getDriver().findElement(By.id("name")),
                freestyleJob
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Freestyle project']"))
        );
        clickMethod(
                getDriver().findElement(By.id("ok-button"))
        );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Jenkins']"))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName)))
        );

        WebElement freestyleJobElement = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(freestyleJob)));
        Assert.assertTrue(freestyleJobElement.isDisplayed());
    }
    @Test
    public void testSameNameItemsInDiffFolders(){
        String folderName1 = "Folder1";
        createNewFolderMethod(folderName1);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Jenkins']"))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName1)))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='New Item']/.."))
        );
        String pipeline = "pipeline1";
        sendKeysMethod(
                getDriver().findElement(By.id("name")),
                pipeline
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Pipeline']"))
        );
        clickMethod(
                getDriver().findElement(By.id("ok-button"))
        );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Jenkins']"))
        );

        String folderName2 = "Folder2";
        createNewFolderMethod(folderName2);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Jenkins']"))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName2)))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='New Item']/.."))
        );
        sendKeysMethod(
                getDriver().findElement(By.id("name")),
                pipeline
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Pipeline']"))
        );
        clickMethod(
                getDriver().findElement(By.id("ok-button"))
        );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Jenkins']"))
        );

        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName1)))
        );
        String folder1pipeline = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(pipeline))).getText();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='Jenkins']"))
        );
        clickMethod(
                getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folderName2)))
        );
        String folder2pipeline = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(pipeline))).getText();

        Assert.assertEquals(folder1pipeline,folder2pipeline);
    }
}
