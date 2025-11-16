package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;


public class Folder4Test extends BaseTest {

    @Test
    public void testCreateFolder() {
        String folder = "Test Folder";

        getDriver().findElement(By.xpath("//a[@href='newJob']"))
                .click();
        getDriver().findElement(By.id("name"))
                .sendKeys("FolderOne");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder"))
                .click();
        WebElement organizationButton = getDriver().findElement(By.id("ok-button"));
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", organizationButton);
        organizationButton.click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'General']")));
        getDriver().findElement(By.xpath("//input[@type='text']"))
                .sendKeys(folder);
        getDriver().findElement(By.name("Submit"))
                .click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class = 'content-block__link']")));
        WebElement message = getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li/a"));

        Assert.assertEquals(message.getText(), folder);
    }

}
