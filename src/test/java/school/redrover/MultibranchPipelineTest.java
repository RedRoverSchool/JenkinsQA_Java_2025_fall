package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class MultibranchPipelineTest extends BaseTest {

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
}
