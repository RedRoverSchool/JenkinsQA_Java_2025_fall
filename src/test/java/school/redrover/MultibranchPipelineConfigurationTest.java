package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.UUID;

public class MultibranchPipelineConfigurationTest extends BaseTest {

    private WebDriverWait wait;

    private void createMultibranchPipelineProject() {
        final String randomAlphaNumericText = UUID.randomUUID().toString().replaceAll("-", "");

        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();

        getDriver().findElement(By.id("name")).sendKeys(randomAlphaNumericText);

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].click();",
                getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']"))
        );

        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));
    }

    private void clickOnTheToggle() {
        getDriver().findElement(By.cssSelector("[data-title='Disabled']")).click();
    }

    @Test
    public void testDisableToggle() {
        createMultibranchPipelineProject();
        clickOnTheToggle();

        WebElement disabledTitle = getDriver().findElement(By.cssSelector("[class$='unchecked-title'"));
        wait.until(ExpectedConditions.textToBePresentInElement(disabledTitle, "Disabled"));

        Assert.assertTrue(disabledTitle.isDisplayed());
    }

    @Test
    public void testTooltipOnToggleHover() {
        final String expectedTooltip = "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        createMultibranchPipelineProject();

        WebElement toggleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("toggle-switch-enable-disable-project")
        ));

        new Actions(getDriver()).moveToElement(toggleElement).perform();

        String actualTooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("tippy-content")))
                .getText();

        Assert.assertEquals(actualTooltip, expectedTooltip);
    }

    @Test
    public void testDisabledMessageOnStatusPage() {
        final String expectedDisabledMessage = "This Multibranch Pipeline is currently disabled";

        createMultibranchPipelineProject();
        clickOnTheToggle();

        getDriver().findElement(By.name("Submit")).click();

        WebElement actualDisabledMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("disabled-message")));

        Assert.assertEquals(actualDisabledMessage.getText(), expectedDisabledMessage);
    }
}
