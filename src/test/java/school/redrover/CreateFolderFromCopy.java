package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateFolderFromCopy extends BaseTest {

    @Test
    public void createFolderAndCopyItem() {

        //First create the folder

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("MyFirstFolder");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", okButton);
        okButton.click();

        getDriver().findElement(By.xpath("//div[@class='setting-main']//input")).sendKeys("Name for Autotest attempt");
        getDriver().findElement(By.xpath("//div[@class='setting-main']//textarea")).sendKeys("Description for Autotest attempt");
        WebElement submitButton = getDriver().findElement(By.className("jenkins-submit-button"));
        submitButton.click();

        //Create a copy of the existing folder

        WebElement newItemButton = getDriver().findElement(By.xpath("//a[@href='/job/MyFirstFolder/newJob']"));
        newItemButton.click();

        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
       Assert.assertEquals(getDriver().findElement(By.className("add-item-copy")).getText(), "Copy from");

    }
}
