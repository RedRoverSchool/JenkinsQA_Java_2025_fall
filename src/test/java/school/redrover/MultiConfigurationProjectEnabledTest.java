package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class MultiConfigurationProjectEnabledTest extends BaseTest {
    private static final String PROJECT_NAME = "NewMulti-ConfigurationProject";

    @Test
    void testDisableAfterCreation() throws InterruptedException {
        createMCProject(PROJECT_NAME);
        Thread.sleep(500);

        getDriver().findElement(By.xpath("//label[@class='jenkins-toggle-switch__label ']")).click();
        Thread.sleep(500);

        Assert.assertTrue(getDriver().findElement(By.
                        xpath("//span[@class='jenkins-toggle-switch__label__unchecked-title']")).isDisplayed(),
                "'Disabled' must be shown");
        Assert.assertFalse(getDriver().findElement(By.
                        xpath("//label[@class='jenkins-toggle-switch__label ']")).isSelected(),
                "Toggle Button should be Disabled");
    }

    @Test
    void testDisableFromHomePage() throws InterruptedException {
        createMCProject(PROJECT_NAME);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/']"))).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + PROJECT_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, '/configure')]")).click();
        getDriver().findElement(By.xpath("//label[@class='jenkins-toggle-switch__label ']")).click();
        Thread.sleep(500);

        Assert.assertTrue(getDriver().findElement(By.
                        xpath("//span[@class='jenkins-toggle-switch__label__unchecked-title']")).isDisplayed(),
                "'Disabled' must be shown");
        Assert.assertFalse(getDriver().findElement(By.
                        xpath("//label[@class='jenkins-toggle-switch__label ']")).isSelected(),
                "Toggle Button should be Disabled");
    }

    @Test
    void testWarningWhenDisable() throws InterruptedException {
        createMCProject(PROJECT_NAME);
        Thread.sleep(500);

        getDriver().findElement(By.xpath("//label[@class='jenkins-toggle-switch__label ']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        WebElement warning = getDriver().findElement(By.xpath("//*[@id='enable-project']"));
        Thread.sleep(500);

        Assert.assertTrue(warning.getText().contains("This project is currently disabled"),
                "Warning text should contain 'This project is currently disabled'");
    }

    private void createMCProject(String name) {
        getDriver().findElement(By.xpath("//div[@class='task ']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }
}
