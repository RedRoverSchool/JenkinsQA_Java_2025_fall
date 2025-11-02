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

    private WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(5));
    }

    @Test
    public void testHomePageHeading() {
        WebElement actualHeading = getWait()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".empty-state-block > h1")));

        Assert.assertEquals(actualHeading.getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testHomePageParagraph() {
        final String expectedParagraph = "This page is where your Jenkins jobs will be displayed. " +
                "To get started, you can set up distributed builds or start building a software project.";

        WebElement actualParagraph = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("p")));

        Assert.assertEquals(actualParagraph.getText(), expectedParagraph);
    }
}
