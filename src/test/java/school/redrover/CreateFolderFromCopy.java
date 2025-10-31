package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateFolderFromCopy extends BaseTest {

    @Test
    public void createFolderTest() {

        final String fileName = "MyFirstFolder";
        final String displayName = "Name for Autotest attempt";
        final String description = "Description for Autotest attempt";
        final String textCopyFrom = "Copy from";

        //First create the folder

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(fileName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", okButton);
        okButton.click();

        getDriver().findElement(By.xpath("//div[@class='setting-main']//input")).sendKeys(displayName);
        getDriver().findElement(By.xpath("//div[@class='setting-main']//textarea")).sendKeys(description);
        WebElement submitButton = getDriver().findElement(By.className("jenkins-submit-button"));
        submitButton.click();

        //Create a copy of the existing folder

        WebElement newItemButton = getDriver().findElement(By.xpath("//a[@href='/job/MyFirstFolder/newJob']"));
        newItemButton.click();

        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
       Assert.assertEquals(getDriver().findElement(By.className("add-item-copy")).getText(), textCopyFrom);


    }

}
