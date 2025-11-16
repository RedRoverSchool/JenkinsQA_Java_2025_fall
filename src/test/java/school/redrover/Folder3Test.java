package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class Folder3Test extends BaseTest {

    @Test
    public void testCreateFolder() {
        final String folderName = "Test Jenkins";
        final String descriptionText = "My first simple test";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.name("_.description")))
                .sendKeys(descriptionText);
        getDriver().findElement(By.xpath("//button[@name='Submit']"))
                .click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class = 'job-index-headline page-headline']")));

        getWait5()
                .until(ExpectedConditions.elementToBeClickable(By.className("app-jenkins-logo")))
                .click();

        WebElement jobNameCell = getDriver().findElement(By.xpath("//*[contains(@class, 'jenkins-table__link')]/span[1]"));
        Assert.assertEquals(jobNameCell.getText(), folderName);
    }
}
