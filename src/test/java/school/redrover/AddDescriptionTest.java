package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class AddDescriptionTest extends BaseTest {

    @Test
    public void checkElementTest() throws InterruptedException {
        String text = "Test text";

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(text);
        getDriver().findElement(By.xpath("//button[@formNoValidate='formNoValidate']")).click();
        Thread.sleep(50);

        String confirmElement2 = getDriver().findElement(By.id("description-content")).getText();
        Assert.assertEquals(confirmElement2, text);
    }
}
