package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateNewItemFolderTest extends BaseTest {

    @Test
    public void testCreateNewFolder() {
     getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
     getDriver().findElement(By.id("name")).sendKeys("NewFolder");
     getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
     getDriver().findElement(By.id("ok-button")).click();
     getDriver().findElement(By.name("Submit")).click();

     WebElement headerPage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
     Assert.assertEquals(headerPage.getText(), "NewFolder");
    }
}
