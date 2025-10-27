package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchName";

    private void createMultibranchPipline() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testAddingDescriptionCreatingMultibranch() {
        final String expectedDescription = "AddedDescription";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("MultibranchPipeline");
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("_.description")).sendKeys(expectedDescription);
        getDriver().findElement(By.name("Submit")).click();

        String actualDescription = getDriver().findElement(By.id("view-message")).getText();

        Assert.assertEquals(actualDescription, expectedDescription, actualDescription + " and " + expectedDescription + " don't match");
    }

    @Test
    public void testTryCreateProjectExistName() throws InterruptedException {
        final String errorMessage = "» A job already exists with the name " + "‘" + MULTIBRANCH_PIPELINE_NAME + "’";

        createMultibranchPipline();

        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//a[@href='/']/img")).click();

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);

        Thread.sleep(3000);
        String actualMessage = getDriver().findElement(By.xpath("//*[@id='itemname-invalid']")).getText();
        Assert.assertEquals(actualMessage, errorMessage);
    }

    @Test
    public void testVerifyStatusToSwitchingEnableMultibranchPipeline() throws InterruptedException {
        final String disableText = "This Multibranch Pipeline is currently disabled";

        createMultibranchPipline();
        getDriver().findElement(By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/configure']")).click();
        getDriver().findElement(By.cssSelector("#toggle-switch-enable-disable-project > label")).click();
        getDriver().findElement(By.name("Submit")).click();

        Thread.sleep(2000);
        String actualDisableText = getDriver().findElement(By.id("disabled-message")).getText();
        Assert.assertTrue(actualDisableText.contains(disableText));
    }
}
