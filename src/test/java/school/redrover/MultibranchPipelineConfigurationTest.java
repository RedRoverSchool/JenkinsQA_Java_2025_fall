package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.UUID;

public class MultibranchPipelineConfigurationTest extends BaseTest {

    @Test
    public void testDisableToggle() {
        final String randomAlphaNumericText = UUID.randomUUID().toString().replaceAll("-", "");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']"))).click();

        getDriver().findElement(By.id("name")).sendKeys(randomAlphaNumericText);

        WebElement multiBranchPipelineProject = getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", multiBranchPipelineProject);
        multiBranchPipelineProject.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));

        getDriver().findElement(By.cssSelector("[data-title='Disabled']")).click();

        WebElement disabledTitle = getDriver().findElement(By.cssSelector("[class$='unchecked-title'"));
        wait.until(ExpectedConditions.textToBePresentInElement(disabledTitle,"Disabled"));

        Assert.assertTrue(disabledTitle.isDisplayed());
    }
}
