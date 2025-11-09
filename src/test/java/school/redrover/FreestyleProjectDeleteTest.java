package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FreestyleProjectDeleteTest extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";
    private Actions actions;

    @Test
    public void testDeleteFreestyleProject() {
        final String expectedText = "Welcome to Jenkins!";
        createFreestyleProject();

        getWait5().until(ExpectedConditions
                .elementToBeClickable(By.cssSelector("span.jenkins-mobile-hide"))).click();

        getActions().moveToElement(getDriver().findElement(
                By.xpath("//a[@class='jenkins-table__link model-link inside']")))
                .perform();

       moveAndClickWithJS(getWait5().until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector(".jenkins-menu-dropdown-chevron"))));

        getDriver().findElement(By.xpath("//button[@href='/job/FreestyleProject/doDelete']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), expectedText);
    }

    public void createFreestyleProject() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void moveAndClickWithJS(WebElement element) {
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", element);
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new Event('click'));", element);
    }

    private Actions getActions() {
        if (actions == null) {
            actions = new Actions(getDriver());
        }
        return actions;
    }
}
