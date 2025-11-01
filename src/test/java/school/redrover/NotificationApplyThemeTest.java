package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class NotificationApplyThemeTest extends BaseTest {

    @Test
    public void testChangeTheme() throws InterruptedException {
        WebElement profile = getDriver().findElement(By.id("root-action-UserAction"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(profile).perform();
        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//*[@id=\"tippy-1\"]/div/div/div/a[4]")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/section/div[4]/div[1]/div/div[2]/div[1]/div/label")).click();
        getDriver().findElement(By.className("apply-button")).click();
        Thread.sleep(1000);
        WebElement result = getDriver().findElement(By.className("jenkins-notification--success"));
        Assert.assertEquals(result.getText(), "Saved");
    }

}