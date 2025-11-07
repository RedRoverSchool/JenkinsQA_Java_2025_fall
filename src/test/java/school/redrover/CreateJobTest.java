package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateJobTest extends BaseTest {
    private final String jobName = "Job test";

    @BeforeMethod
    public void preconditions() {
        getDriver().findElement(By.linkText("Create a job")).click();
    }

    @Test
    public void testSuccessfulFreeStyleJobCreation() {
        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement createdJobHeader = getDriver().findElement(By.className("job-index-headline"));

        Assert.assertEquals(createdJobHeader.getText(), jobName);
    }
}
