package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;


public class MultibranchPipeline2Test extends BaseTest {

    @Test
    public void testCreateMultibranchPipelineByNew() throws InterruptedException {
        final String multibranchName = "MultibranchName";

        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(multibranchName);

        WebElement multibranchPipline = getDriver().findElement(By.cssSelector("[class*='MultiBranchProject']"));
        js.executeScript("arguments[0].scrollIntoView(true);", multibranchPipline);
        multibranchPipline.click();

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//a[@href='/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//a[@href='job/%s/']".formatted(multibranchName))).getText(),
                multibranchName);
    }
}
