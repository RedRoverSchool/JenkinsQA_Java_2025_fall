package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class MultibranchPipeline3Test extends BaseTest {

    @Test
    public void testCreate() throws InterruptedException {
        final String itemName = "NewMultibranch";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(itemName);
        getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        Thread.sleep(2000);

        String actualName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(actualName, itemName);
    }
}
