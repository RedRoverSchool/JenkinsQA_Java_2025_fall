package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class NewJobPageTest extends BaseTest {

    @Test
    public  void testItemNameEmpty(){

        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1) a")).click();
        getDriver().findElement((By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]"))).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(), "» This field cannot be empty, please enter a valid name");
        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());

    }
}
