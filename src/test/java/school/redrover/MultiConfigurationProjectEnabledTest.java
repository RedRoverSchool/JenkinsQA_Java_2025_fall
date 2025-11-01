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
    private final String projectName = "NewMulti-ConfigurationProject";

    @Test
    void disableAfterCreationTest() throws InterruptedException {
        //ac 1: User can Disable project use default toggle "Enabled" after created MC project on General page Configure settings.
        createMCProject(projectName);
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
    void warningWhenDisableTest() throws InterruptedException {
        //ac:4.	When project is Disabled user can see the message “This project is currently disabled” on project's Status page .
        createMCProject(projectName);
        Thread.sleep(500);

        getDriver().findElement(By.xpath("//label[@class='jenkins-toggle-switch__label ']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        WebElement warning = getDriver().findElement(By.xpath("//*[@id='enable-project']"));

        Assert.assertTrue(warning.getText().contains("This project is currently disabled"),
                "Warning text should contain 'This project is currently disabled'");
    }

    @Test
    void iconWhenDisableTest() throws InterruptedException {
        //ac:5.	When project is Disabled user can see icon with tooltip "Disabled" created project on Home page Jenkins.
        createMCProject(projectName);
        Thread.sleep(500);

        getDriver().findElement(By.xpath("//label[@class='jenkins-toggle-switch__label ']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/']"))).click();

        WebElement disabledIcon = new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//td[@data='8']")
                ));
        Assert.assertTrue(disabledIcon.isDisplayed());
    }

    @Test
    void disableFromHomePageTest() throws InterruptedException {
        //ac 2:	User can Disable created Project opened it from projects list on Home page Jenkins and going to Configure settings switch toggle "Enabled".
        createMCProject(projectName);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/']"))).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + projectName + "/']")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, '/configure')]")).click();
        getDriver().findElement(By.xpath("//label[@class='jenkins-toggle-switch__label ']")).click();

        Assert.assertTrue(getDriver().findElement(By.
                        xpath("//span[@class='jenkins-toggle-switch__label__unchecked-title']")).isDisplayed(),
                "'Disabled' must be shown");
        Assert.assertFalse(getDriver().findElement(By.
                        xpath("//label[@class='jenkins-toggle-switch__label ']")).isSelected(),
                "Toggle Button should be Disabled");
    }

    private void createMCProject(String name) {
        getDriver().findElement(By.xpath("//div[@class='task ']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }
}

