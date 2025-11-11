package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FreestyleProjectConfigurationTest extends BaseTest {
    private static final String PROJECT_NAME = "FreestyleProject";

    @Test
    public void testDisableProjectViaConfigureDropdownMenu() {
        createFreestyleProject(PROJECT_NAME);

        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jenkins-header__navigation a"))).click();
        new Actions(getDriver()).moveToElement(getDriver()
                .findElement(By.xpath("//td/a[@href='job/%s/']".formatted(PROJECT_NAME)))).perform();
        moveAndClickWithJS(getWait5().until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector(".jenkins-menu-dropdown-chevron"))));
        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//a[contains(@href,'configure')]"))).click();
        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#toggle-switch-enable-disable-project"))).click();
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        String disableProjectMessage = getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(".warning"))).getText().split("\\R")[0];
        Assert.assertEquals(disableProjectMessage, "This project is currently disabled");
    }

    @Test
    public void testEnableProjectViaMainMenuConfigure() {
        createFreestyleProject(PROJECT_NAME);

        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#toggle-switch-enable-disable-project"))).click();
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        getWait5().until(ExpectedConditions
                .elementToBeClickable(By.cssSelector(".jenkins-header__navigation a"))).click();

        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//td/a[@href='job/%s/']".formatted(PROJECT_NAME)))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='/job/%s/configure']".formatted(PROJECT_NAME)))).click();

        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#toggle-switch-enable-disable-project"))).click();
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        getWait5().until(ExpectedConditions
                .elementToBeClickable(By.cssSelector(".jenkins-header__navigation a"))).click();

        WebElement buildButtonWhenProjectEnabled = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//a[@title='Schedule a Build for %s'])[1]".formatted(PROJECT_NAME))));
        Assert.assertTrue(buildButtonWhenProjectEnabled.isDisplayed());
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
