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

public class FreestyleProjectConfigurationTest extends BaseTest {

    @Test
    public void testDisableProjectViaConfigureDropdownMenu() {
        String projectName = "FreestyleProject";
        createFreestyleProject(projectName);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jenkins-header__navigation a"))).click();
        new Actions(getDriver()).moveToElement(getDriver()
                .findElement(By.xpath("//td/a[@href='job/%s/']".formatted(projectName)))).perform();
        moveAndClickWithJS(wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector(".jenkins-menu-dropdown-chevron"))));
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//a[contains(@href,'configure')]"))).click();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#toggle-switch-enable-disable-project"))).click();
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        String disableProjectMessage = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(".warning"))).getText().split("\\R")[0];
        Assert.assertEquals(disableProjectMessage, "This project is currently disabled");
    }

    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
    }

    private void moveAndClickWithJS(WebElement element) {
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", element);
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new Event('click'));", element);
    }

}
