package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class MultibranchPipelineConfigurationChangeDescription extends BaseTest {

    @Test
    public void testChangeDescription() {

        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("New Multibranch Pipeline");
        getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//textarea")).sendKeys("New test pipeline");
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.cssSelector("a[href='./configure']")).click();
        getDriver().findElement(By.xpath("//textarea")).clear();
        getDriver().findElement(By.xpath("//textarea")).sendKeys("Change description - New test pipeline 2");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(), "Change description - New test pipeline 2");

    }
}

