package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class ExampleTest extends BaseTest {

    @Test
    public void testHome() {
        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("span.jenkins-mobile-hide")).getText(),
                "Jenkins");
    }

    @Test
    public void testCreateFreeStyleProject() throws InterruptedException {
        final String jobName = "my project";

        WebElement createJobButton = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        createJobButton.click();

        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Thread.sleep(2000);

        getDriver().findElement(By.cssSelector("span.jenkins-mobile-hide")).click();

        WebElement jobRecordCell = getDriver().findElement(By.cssSelector(".jenkins-table__link >span:first-child"));
        Assert.assertEquals(jobRecordCell.getText(), jobName);
    }
}
