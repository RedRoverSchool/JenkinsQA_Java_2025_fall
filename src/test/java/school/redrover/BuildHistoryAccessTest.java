package school.redrover;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.Arrays;
import java.util.List;

public class BuildHistoryAccessTest extends BaseTest {

    @Test
    public void testQuickAccessBuildHistory() {
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[2]/span/a")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Build History of Jenkins");
    }

    @Test
    public void testValidateBuildHistoryEmptyTable() {
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[2]/span/a")).click();

        WebElement emptyTable = getDriver().findElement(By.xpath("//*[@id=\"projectStatus\"]"));
        Assert.assertTrue(emptyTable.isDisplayed(), "The Build History table is not displayed.");

        List<WebElement> headers = emptyTable.findElements(By.cssSelector("thead th a.sortheader"));
        List<String> expectedHeaders = Arrays.asList("S", "Build", "Time Since", "Status");

        Assert.assertEquals(headers.size(), expectedHeaders.size(), "The number of columns does not match");

        for (int i = 0; i < headers.size(); i++) {
            String actual = headers.get(i).getText()
                    .replaceAll("[↓↑\\s\\u00A0]+$", "")
                    .trim();
            Assert.assertEquals(actual, expectedHeaders.get(i), "Invalid column name: " + actual);
        }
    }
}
