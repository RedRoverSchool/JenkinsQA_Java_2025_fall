package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class VerifyNoResultsMessageTest extends BaseTest {

    @Test
    public void testNoResultSearch() {
        final String testSearch = "t7a";

        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getDriver().findElement(By.id("command-bar")).sendKeys(testSearch);
        WebElement SearchMessage = getDriver().findElement(By.cssSelector("#search-results > p > span"));

        Assert.assertEquals(SearchMessage.getText(), "No results for");
    }



}
