package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class PipelineConfigurationTest extends BaseTest {

    @Test
    public void testEnableProject() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("ChangeStatusEnabledTest");
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();

        WebElement enableDisableToggle = getDriver().findElement(By.xpath("//span[@id='toggle-switch-enable-disable-project']"));

        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        wait.until(d -> enableDisableToggle.isDisplayed());

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//span[@class='jenkins-toggle-switch__label__checked-title']"))
                .getText(), "Enabled");
    }

    @Test
    public void testDisableProject() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("ChangeStatusDisabledTest");
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();

        WebElement enableDisableToggle = getDriver().findElement(By.xpath("//span[@id='toggle-switch-enable-disable-project']"));

        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        wait.until(d -> enableDisableToggle.isDisplayed());
        enableDisableToggle.click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//span[@class='jenkins-toggle-switch__label__unchecked-title']"))
                .getText(), "Disabled");
    }
}
