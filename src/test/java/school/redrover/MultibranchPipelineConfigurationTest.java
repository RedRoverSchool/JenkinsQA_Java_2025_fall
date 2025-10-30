package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.UUID;

public class MultibranchPipelineConfigurationTest extends BaseTest {

    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        }
    }

    private void createMultibranchPipelineProject() {
        final String randomAlphaNumericText = UUID.randomUUID().toString().replaceAll("-", "");

        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']"))).click();

        getDriver().findElement(By.id("name")).sendKeys(randomAlphaNumericText);

        WebElement multiBranchPipelineProject = getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", multiBranchPipelineProject);
        multiBranchPipelineProject.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));
    }

    @Test
    public void testDisableToggle() {
        createMultibranchPipelineProject();

        getDriver().findElement(By.cssSelector("[data-title='Disabled']")).click();

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
        String actualTooltip = toggleElement.getDomAttribute("tooltip");

        Assert.assertEquals(actualTooltip, expectedTooltip);
    }
}
