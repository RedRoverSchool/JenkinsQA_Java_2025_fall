package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class Dashboard2Test extends BaseTest {

    @DataProvider(name = "Links")
    Object[][] linkData2() {
        return new Object[][]{
                {"Create a job", "/newJob", "New Item"},
                {"Set up an agent", "/new", "New node"},
                {"Configure a cloud", "/cloud/", "Clouds"},
        };
    }

    @Test(dataProvider = "Links")
    public void testDashboardButtons(String linkText, String expectedUrlSubstring, String expectedHeading) {

        WebElement link = getDriver().findElement(By.xpath(".//a[span[text()='" + linkText + "']]"));
        link.click();

        new WebDriverWait(getDriver(), Duration.ofMillis(2000))
                .until(ExpectedConditions.urlContains(expectedUrlSubstring));

        String actualHeading = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(actualHeading, expectedHeading);
    }
}
