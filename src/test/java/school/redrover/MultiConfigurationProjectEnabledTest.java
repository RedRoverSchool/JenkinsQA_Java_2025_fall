package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class MultiConfigurationProjectEnabledTest extends BaseTest {
    private static final String PROJECT_NAME = "NewMulti-ConfigurationProject";

    @Test
    void testDisableAfterCreation() {
        createMCProject(PROJECT_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@class='jenkins-toggle-switch__label ']"))).click();
        WebElement toggle = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.
                xpath("//span[@class='jenkins-toggle-switch__label__unchecked-title']")));

        Assert.assertTrue(toggle.isDisplayed(), "'Disabled' must be shown");
        Assert.assertFalse(getDriver().findElement(By.
                        xpath("//label[@class='jenkins-toggle-switch__label ']")).isSelected(),
                "Toggle Button should be Disabled");
    }

    @Test
    void testDisableFromHomePage() {
        createMCProject(PROJECT_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/']"))).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + PROJECT_NAME + "/']")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, '/configure')]")).click();
        getDriver().findElement(By.xpath("//label[@class='jenkins-toggle-switch__label ']")).click();
        WebElement toggle = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.
                xpath("//span[@class='jenkins-toggle-switch__label__unchecked-title']")));

        Assert.assertTrue(toggle.isDisplayed(), "'Disabled' must be shown");
        Assert.assertFalse(getDriver().findElement(By.
                        xpath("//label[@class='jenkins-toggle-switch__label ']")).isSelected(),
                "Toggle Button should be Disabled");
    }

    @Test
    void testWarningWhenDisable() {
        createMCProject(PROJECT_NAME);

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@class='jenkins-toggle-switch__label ']"))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        WebElement warning = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='enable-project']")));

        Assert.assertTrue(warning.getText().contains("This project is currently disabled"),
                "Warning text should contain 'This project is currently disabled'");
    }

    @Test
    void testIconWhenDisable() {
        createMCProject(PROJECT_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@class='jenkins-toggle-switch__label ']"))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Jenkins']")));
        getDriver().findElement(By.xpath("//span[text()='Jenkins']")).click();
        WebElement disabledIcon = getWait2().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//td[@data='8']")));

        Assert.assertTrue(disabledIcon.isDisplayed());
    }

    private void createMCProject(String name) {
        getDriver().findElement(By.xpath("//div[@class='task ']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }
}
