package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class NewItemListTest extends BaseTest {
    @Test
    public void testNewItemTypeList(){
        getDriver().findElement(By.xpath("//span[text()='New Item']")).click();
        String actualResult = getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).getText();
        Assert.assertEquals(
                actualResult,
                "Freestyle project");
    }
}
