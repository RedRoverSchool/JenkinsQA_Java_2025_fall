package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class ConfigureAppearanceTest extends BaseTest {

    @Ignore
    @Test
    public void testChangeTheme() {

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='appearance']")).click();
        getDriver().findElement(By.cssSelector("label[for=\"radio-block-0\"]")).click();
        getDriver().findElement(By.cssSelector("button.jenkins-submit-button")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("html[data-theme='dark']")).isDisplayed());
    }
}
