package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import static java.lang.Thread.sleep;

public class Folder3Test extends BaseTest {

    @Test
    public void testCreateFolder() throws InterruptedException {
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
        sleep(3000);
        getDriver().findElement(By.xpath("//input[@type='text']"))
                .sendKeys(folder);
        getDriver().findElement(By.name("Submit"))
                        .click();

        sleep(5500);
        WebElement message = getDriver().findElement(By.className("page-headline"));

        Assert.assertEquals(message.getText(), folder);
    }

}
