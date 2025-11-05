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

public class ManageJenkinsTest extends BaseTest {

    private void openGlobalProperties() {
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        new Actions(getDriver())
                .scrollByAmount(0, 1500)
                .perform();
    }

    @Test
    public void testDeferredWipeoutSettingIsSaved() {
        openGlobalProperties();

        WebElement checkbox = getDriver().findElement(By.cssSelector("[id='cb3'] + label"));
        checkbox.click();

        getDriver().findElement(By.name("Submit")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Welcome to Jenkins!"));

        openGlobalProperties();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("[id='cb3']")).isSelected(), "Checkbox is not selected");

        checkbox = getDriver().findElement(By.cssSelector("[id='cb3'] + label"));
        checkbox.click();
        getDriver().findElement(By.name("Apply")).click();
    }
}
