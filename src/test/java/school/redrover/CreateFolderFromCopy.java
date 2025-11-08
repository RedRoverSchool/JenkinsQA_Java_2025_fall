package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateFolderFromCopy extends BaseTest {

    final String folderName = "MyFirstFolder";
    final String displayName = "Name for Autotest attempt";
    final String description = "Description for Autotest attempt";

    @Test
    public void createCopy() {

        createFolder();
        boolean fieldExists = true;

        WebElement newItemButton = getDriver().
                findElement(By.xpath("//a[@href='/job/MyFirstFolder/newJob']"));
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
                .sendKeys(displayName);
        getDriver()
                .findElement(By.xpath("//div[@class='setting-main']//textarea"))
                .sendKeys(description);
        WebElement submitButton = getDriver().findElement(By.className("jenkins-submit-button"));
        submitButton.click();
    }
}

