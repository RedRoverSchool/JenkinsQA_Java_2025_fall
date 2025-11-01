package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class PipelineConfigurationAdvancedTest extends BaseTest {

    private void createNewPipeline() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("pipeline_01");
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test(testName = "AT_03.005.01")
    public void testNavigationToAdvancedByScrollingDown() {
        createNewPipeline();

        WebElement actualAdvancedSectionTitle = getDriver().findElement(By.id("advanced"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", actualAdvancedSectionTitle);

        Assert.assertEquals(actualAdvancedSectionTitle.getText(), "Advanced");
    }
}
