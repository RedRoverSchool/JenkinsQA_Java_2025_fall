package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class ConfigureAppearanceTest extends BaseTest {

    @Ignore
    @Test
    public void testChangeTheme() throws InterruptedException {

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='appearance']")).click();
        getDriver().findElement(By.xpath("//label[contains(., 'Do not allow users to select a different theme')]")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.cssSelector("label:has(> div[data-theme='dark'])")).click();
        getDriver().findElement(By.cssSelector("button.jenkins-submit-button")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("html[data-theme='dark']")).isDisplayed());
    }
}
