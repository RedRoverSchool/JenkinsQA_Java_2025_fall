package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class MultibranchPipeline1Test extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "Multibranch_Pipeline";
    private static final String MULTIBRANCH_PIPELINE_DISPLAY_NAME = "Multibranch_Pipeline_Display";

    @Test
    public void testCreateMultibranchPipelineClickCreateJob() {

        getDriver().findElement(By.xpath("//section[1]//li/a")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("#items .category:nth-child(2) li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(".empty-state-section h2")));

        getDriver().findElement(By.xpath("//a[@href='/']/img")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//a[@href='job/" + MULTIBRANCH_PIPELINE_NAME + "/']")).getText(),
                MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testCreateMultibranchPipelineClickNewItem() {

        getDriver().findElement(By.cssSelector(".task:first-child a")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("#items .category:nth-child(2) li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//input[@name='_.displayNameOrNull']"))))
                .sendKeys(MULTIBRANCH_PIPELINE_DISPLAY_NAME);
        getDriver().findElement(By.name("Submit")).click();

        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//h1[.='" + MULTIBRANCH_PIPELINE_DISPLAY_NAME + "']")));

        getDriver().findElement(By.xpath("//a[@href='/']/img")).click();

        Assert.assertEquals(getDriver().findElement(
                        By.xpath("//a[@href='job/" + MULTIBRANCH_PIPELINE_NAME + "/']")).getText(),
                MULTIBRANCH_PIPELINE_DISPLAY_NAME);
    }
}
