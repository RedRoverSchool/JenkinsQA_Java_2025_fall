package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class MultibranchPipeline1Test extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "Multibranch_Pipeline";
    private static final String MULTIBRANCH_PIPELINE_DISPLAY_NAME = "Multibranch_Pipeline_Display";

    @Ignore
    @Test
    public void testCreateMultibranchPipelineClickCreateJob() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().findElement(By.xpath("//section[1]//li/a")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("#items .category:nth-child(2) li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@href='/']/img")))).click();

        Assert.assertEquals(
                wait.until(ExpectedConditions.elementToBeClickable(
                        (By.xpath("//a[@href='job/" + MULTIBRANCH_PIPELINE_NAME + "/']")))).getText(),
                MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testCreateMultibranchPipelineClickNewItem() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().findElement(By.cssSelector(".task:first-child a")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("#items .category:nth-child(2) li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//input[@name='_.displayNameOrNull']"))))
                .sendKeys(MULTIBRANCH_PIPELINE_DISPLAY_NAME);
        getDriver().findElement(By.name("Submit")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("span.jenkins-mobile-hide")))).click();

        Assert.assertEquals(
                wait.until(ExpectedConditions.elementToBeClickable(
                        (By.xpath("//a[@href='job/" + MULTIBRANCH_PIPELINE_NAME + "/']")))).getText(),
                MULTIBRANCH_PIPELINE_DISPLAY_NAME);
    }
}
