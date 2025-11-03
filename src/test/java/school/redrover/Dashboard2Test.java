package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class Dashboard2Test extends BaseTest {

    @Test(dataProvider = "distributedBuildLinks")
    public void testDistributedBuildButtons(String linkText, String expectedUrlSubstring) {

        WebElement link = getDriver().findElement(By.xpath(".//a[span[text()='" + linkText + "']]"));
        link.click();

        Assert.assertTrue(getDriver().getCurrentUrl().endsWith(expectedUrlSubstring));
    }

    @DataProvider(name = "distributedBuildLinks")
    Object[][] linkData() {
        return new Object[][]{
                {"Set up an agent", "/new"},
                {"Configure a cloud", "/cloud/"},
                {"Learn more about distributed builds", "/architecting-for-scale/#distributed-builds-architecture"}
        };
    }
}
