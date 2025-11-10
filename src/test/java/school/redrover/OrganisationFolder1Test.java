package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class OrganisationFolder1Test extends BaseTest {

    @Test
    public void testCreateOrganisationFolder() {
        final String folderName = "Organization Folder";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);

        WebElement organizationFolder = getDriver().
                findElement(By.xpath("//li[@class='jenkins_branch_OrganizationFolder']"));
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", organizationFolder);
        organizationFolder.click();

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        WebElement OrganizationFolderName = getDriver().
                findElement(By.xpath("//h1[contains(text(), 'Organization Folder')]"));

        Assert.assertEquals(OrganizationFolderName.getText(), folderName);
    }
}
