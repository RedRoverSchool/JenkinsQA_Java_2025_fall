package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class NotificationApplyThemeTest extends BaseTest {

    @Test
    public void testChangeTheme() throws InterruptedException {
        getDriver().findElement(By.id("root-action-UserAction")).click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[5]/span/a/span[1]")).click();
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/section/div[4]/div[1]/div/div[2]/div[1]/div/label")).click();
        getDriver().findElement(By.className("apply-button")).click();
        WebElement result = getDriver().findElement(By.className("jenkins-notification--success"));
        Assert.assertEquals(result.getText(), "Saved");
    }

}