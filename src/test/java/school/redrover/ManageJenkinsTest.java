package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class ManageJenkinsTest extends BaseTest {

    private static void openGlobalProperties(WebDriver driver, Actions actions) {
        driver.findElement(By.id("root-action-ManageJenkinsAction")).click();
        driver.findElement(By.xpath("//a[@href='configure']")).click();

        actions
                .scrollByAmount(0, 1500)
                .perform();
    }

    @Test
    public void testDeferredWipeout() {
        final String expectedTooltipText =
                "During the workspace cleanup disable improved deferred wipeout method. " +
                        "By default deferred wipeout is used if desired.";

        Actions actions = new Actions(getDriver());

        openGlobalProperties(getDriver(), actions);

        actions
                .moveToElement(getDriver().findElement(By.xpath("//a[contains(@tooltip,'Disable deferred wipeout on this node')]")))
                .perform();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@aria-describedby='tippy-27']")));

        getDriver().findElement(By.xpath("//a[contains(@tooltip,'Disable deferred wipeout on this node')]")).click();
        String actualTooltipText = getDriver()
                .findElement(By.xpath("//div[@nameref='cb3']//div[@class='help']/div[1]"))
                .getText();

        WebElement checkbox = getDriver().findElement(By.id("cb3"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].checked = true;", checkbox);

        getDriver().findElement(By.name("Submit")).click();

        openGlobalProperties(getDriver(), actions);

        Assert.assertEquals(actualTooltipText, expectedTooltipText, "Unexpected tooltip");
        Assert.assertTrue(getDriver().findElement(By.xpath("//input[@id='cb3']")).isSelected(), "Checkbox is not selected");
    }
}
