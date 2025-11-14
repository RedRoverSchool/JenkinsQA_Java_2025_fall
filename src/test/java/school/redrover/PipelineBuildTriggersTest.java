package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class PipelineBuildTriggersTest extends BaseTest {
    private static final String pipelineName = "pipeline_name";

    @Test
    public void testSelectTriggers() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[2]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();

        Actions actions = new Actions(getDriver());
        actions.sendKeys(Keys.PAGE_DOWN).build().perform();

        WebElement[] checkboxes = new WebElement[5];

        String[] checkboxesXpaths = {
                "//*[@id='main-panel']/form/div[1]/section[1]/section/div[4]/div[1]/div/span",
                "//*[@id='main-panel']/form/div[1]/section[1]/section/div[5]/div[1]/div/span",
                "//*[@id='main-panel']/form/div[1]/section[1]/section/div[6]/div[1]/div/span",
                "//*[@id='main-panel']/form/div[1]/section[1]/section/div[7]/div[1]/div/span",
                "//*[@id='main-panel']/form/div[1]/div[5]/div[1]/div/span"
        };

        for (int i = 0; i < 5; i++){
            try {
                checkboxes[i] = getDriver().findElement(By.xpath(checkboxesXpaths[i]));
                checkboxes[i].click();
            }
            catch (ElementClickInterceptedException e) {
                actions.sendKeys(Keys.PAGE_DOWN).build().perform();
                checkboxes[i] = getDriver().findElement(By.xpath(checkboxesXpaths[i]));
                checkboxes[i].click();
            }

            Assert.assertTrue(checkboxes[i].isEnabled());
        }

        getDriver().findElement(By.name("Submit")).click();

    }
}
