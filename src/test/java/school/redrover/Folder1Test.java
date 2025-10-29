package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class Folder1Test extends BaseTest {

    @Test
    public void testCreateFolder() throws InterruptedException {
        final String folderName = "my folder";

        WebElement createJobButton = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        createJobButton.click();

        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Thread.sleep(2000);

        getDriver().findElement(By.cssSelector("span.jenkins-mobile-hide")).click();

        WebElement jobRecordCell = getDriver().findElement(By.cssSelector(".jenkins-table__link >span:first-child"));
        Assert.assertEquals(jobRecordCell.getText(), folderName);
    }
}
