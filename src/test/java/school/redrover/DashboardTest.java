package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class DashboardTest extends BaseTest {

    private WebElement getVisibleHomePageHeading() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".empty-state-block > h1")));
    }

    @Test
    public void testHomePageHeading() {
        WebElement actualHeading = getVisibleHomePageHeading();

        Assert.assertTrue(actualHeading.isDisplayed());
        Assert.assertEquals(actualHeading.getText(), "Welcome to Jenkins!");
    }
}
