package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;

public class HomePageTest extends BaseTest {

    @Test
    public void testHomePageHeadings() {
        WebElement actualHeading = new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));

        List<String> expectedSubHeadings = List.of("Start building your software project", "Set up a distributed build");

        List<String> actualSubHeadings = getDriver()
                .findElements(By.cssSelector("h2.h4"))
                .stream()
                .map(subHeading -> subHeading.getText())
                .toList();

        Assert.assertEquals(actualHeading.getText(), "Welcome to Jenkins!");
        Assert.assertEquals(actualSubHeadings, expectedSubHeadings);
    }
}
