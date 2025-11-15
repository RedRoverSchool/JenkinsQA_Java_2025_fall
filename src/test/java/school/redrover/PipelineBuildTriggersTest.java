package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class PipelineBuildTriggersTest extends BaseTest {
    private static final String pipelineName = "pipeline_name";

    @Test
    public void testSelectTriggers() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[2]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();



        String[] checkboxesXpaths = {
                "//*[@id='main-panel']/form/div[1]/section[1]/section/div[4]/div[1]/div/span",
                "//*[@id='main-panel']/form/div[1]/section[1]/section/div[5]/div[1]/div/span",
                "//*[@id='main-panel']/form/div[1]/section[1]/section/div[6]/div[1]/div/span",
                "//*[@id='main-panel']/form/div[1]/section[1]/section/div[7]/div[1]/div/span",
                "//*[@id='main-panel']/form/div[1]/div[5]/div[1]/div/span"
        };

        for (String xpath : checkboxesXpaths) {
            WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
            wait.until(ExpectedConditions.elementToBeClickable(checkbox));
            checkbox.click();

            Assert.assertTrue(checkbox.isEnabled());
        }

        getDriver().findElement(By.name("Submit")).click();

    }
}
