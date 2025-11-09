package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateFolderFromCopy extends BaseTest {

    final private static String folderName = "MY_FIRST_FOLDER";

    @Test
    public void createCopy() {
        createFolder();
        boolean fieldExists = true;

        WebElement newItemButton = getDriver().
                findElement(By.xpath("//a[@href='/job/MY_FIRST_FOLDER/newJob']"));
        newItemButton.click();
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");

        try {
            getDriver().findElement(By.id("from")).sendKeys(folderName);
        } catch (Exception e) {
            fieldExists = false;
        }

        Assert.assertTrue(fieldExists);
        Assert.assertEquals(
                getDriver().findElement(By.className("add-item-copy")).getText(), "Copy from");
    }

    private void createFolder() {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", okButton);
        okButton.click();

        getDriver()
                .findElement(By.xpath("//div[@class='setting-main']//input"))
                .sendKeys("Name for Autotest attempt");
        getDriver()
                .findElement(By.xpath("//div[@class='setting-main']//textarea"))
                .sendKeys("Description for Autotest attempt");
        WebElement submitButton = getDriver().findElement(By.className("jenkins-submit-button"));
        submitButton.click();
    }
}

