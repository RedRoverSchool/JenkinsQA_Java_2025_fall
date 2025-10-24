package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class DescriptionTest extends BaseTest {

    @Test
    public void testCreateDescription() throws InterruptedException {
        final String description = "Test description";

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        Thread.sleep(2000);

        String actualDescription = getDriver().findElement(By.id("description-content")).getText();
        Assert.assertEquals(actualDescription, description);
    }

}
