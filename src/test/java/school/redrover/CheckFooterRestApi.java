package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CheckFooterRestApi extends BaseTest {

    @Test
    public void testCheckFooterRestApi(){

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();

        WebElement footer = getWait2().until(ExpectedConditions.presenceOfElementLocated
                (By.xpath("//*[@id='jenkins']/footer/div/div[2]/a")));

        Assert.assertTrue(footer.isDisplayed(), "REST API not found in footer");
    }
}
