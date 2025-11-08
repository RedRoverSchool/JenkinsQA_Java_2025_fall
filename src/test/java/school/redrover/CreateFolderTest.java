package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateFolderTest extends BaseTest {

    @Test
    public void testCreateFolder() {
        final String folder = "Test Folder";

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

        WebElement presenceElement = getWait2().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@name='_.displayNameOrNull']")));
        presenceElement.sendKeys(folder);
        getDriver().findElement(By.name("Submit"))
                .click();

        WebElement message = getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#main-panel > div.jenkins-app-bar > div > h1")));

        Assert.assertEquals(message.getText(), folder);
    }

}