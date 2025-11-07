package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import javax.swing.*;
import java.time.Duration;

public class PipelineConfigurationTest extends BaseTest {
    private final By toggleSwitch = By.xpath("//span[@id='toggle-switch-enable-disable-project']");

    private void createFreestyleProject(String name) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
    }

    @Test
    public void testEnableProject() {
        createFreestyleProject("ChangeStatusEnabledTest");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(toggleSwitch));

        String enabledText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("jenkins-toggle-switch__label__checked-title")
                )).getText();
        Assert.assertEquals(enabledText, "Enabled");
    }

    @Test
    public void testDisableProject() {
        createFreestyleProject("ChangeStatusDisabledTest");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement enableDisableToggle = wait.until(ExpectedConditions.visibilityOfElementLocated(toggleSwitch));
        enableDisableToggle.click();

        String disabledText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("jenkins-toggle-switch__label__unchecked-title")
                )).getText();
        Assert.assertEquals(disabledText, "Disabled");
    }

    @Test
    public void testActivityStatusProject() {
        final String projectName = "Disable Project";
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createFreestyleProject(projectName);

        getDriver().findElement(toggleSwitch).click();
        getDriver().findElement(By.name("Submit")).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        WebElement status = getDriver().findElement(By.xpath("//*[@id='job_%s']/td[1]/div".formatted(projectName)));

        Actions actions = new Actions(getDriver());
        actions
                .moveToElement(status)
                .perform();

        String actualStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("tippy-11"))).getText();

        Assert.assertEquals(actualStatus, "Disabled");
    }
}
