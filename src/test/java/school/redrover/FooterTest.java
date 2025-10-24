package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class FooterTest extends BaseTest {

    @Test
    public void testCheckRestApi() {
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        String actualTitle = getDriver().getTitle();

        Assert.assertEquals(actualTitle, "Remote API - Jenkins");
    }

    @Test
    public void testRestApiPageHeadings() {
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        String actualHeading = getDriver().findElement(By.tagName("h1")).getText();

        List<String> expectedSubHeadings = List.of(
                "Controlling the amount of data you fetch",
                "Create Job",
                "Copy Job",
                "Create View",
                "Copy View",
                "Build Queue",
                "Load Statistics",
                "Restarting Jenkins"
        );

        List<String> actualSubHeadings = getDriver()
                .findElements(By.tagName("h2"))
                .stream()
                .map(subHeading -> subHeading.getText())
                .toList();

        Assert.assertEquals(actualHeading, "REST API");
        Assert.assertEquals(actualSubHeadings, expectedSubHeadings);
    }
}
