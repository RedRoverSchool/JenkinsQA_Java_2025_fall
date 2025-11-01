package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class MultiConfigurationProjectEnabledTest extends BaseTest {
    private final String projectName = "NewMulti-ConfigurationProject";

    @Test
    void disableFromHomePageTest() throws InterruptedException {
        createMCProject(projectName);
        Thread.sleep(500);

        WebElement arrow = getDriver().findElement(By.xpath("//a[@href='job/" + projectName + "/']"));
        new Actions(getDriver()).moveToElement(arrow).perform();
        getDriver().findElement(By.xpath("//a[@href='job/" + projectName + "/']/button[@class='jenkins-menu-dropdown-chevron']")).click();
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

    private void createMCProject(String name) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        getDriver().findElement(By.xpath("//div[@class='task ']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/']"))).click();
    }
}
