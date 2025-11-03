package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;

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
        final String expectedParagraphText = "This page is where your Jenkins jobs will be displayed. " +
                "To get started, you can set up distributed builds or start building a software project.";

        WebElement actualParagraph = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("p")));

        Assert.assertEquals(actualParagraph.getText(), expectedParagraphText);
    }

    @Test
    public void testContentBlockLinks() {
        final List<String> expectedUrlEndpoints = List.of(
                "/newJob",
                "/computer/new",
                "/cloud/",
                "/#distributed-builds-architecture"
        );

        List<WebElement> contentBlockLinks = getWait()
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".content-block > a")));

        String originalWindow = getDriver().getWindowHandle();

        for (int i = 0; i < contentBlockLinks.size(); i++) {
            WebElement currentLink = contentBlockLinks.get(i);

            new Actions(getDriver())
                    .keyDown(Keys.CONTROL)
                    .click(currentLink)
                    .perform();

            getWait().until(ExpectedConditions.numberOfWindowsToBe(2));

            Object[] windowHandles = getDriver().getWindowHandles().toArray();
            getDriver().switchTo().window((String) windowHandles[1]);

            String currentUrl = getDriver().getCurrentUrl();
            String expectedUrlEndpoint = expectedUrlEndpoints.get(i);

            Assert.assertTrue(currentUrl.contains(expectedUrlEndpoint));

            getDriver().close();
            getDriver().switchTo().window(originalWindow);
        }
    }
}
