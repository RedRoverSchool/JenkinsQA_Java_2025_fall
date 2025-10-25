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

public class FooterTest extends BaseTest {

    @Test
    public void testRestApiPage() {
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        String actualTitle = getDriver().getTitle();

        Assert.assertEquals(actualTitle, "Remote API - Jenkins");
    }

    @Test
    public void testRestApiPageHeadings() {
        final String expectedHeading = "REST API";
        final List<String> expectedSubHeadings = List.of(
                "Controlling the amount of data you fetch",
                "Create Job",
                "Copy Job",
                "Create View",
                "Copy View",
                "Build Queue",
                "Load Statistics",
                "Restarting Jenkins"
        );

        new WebDriverWait(getDriver(), Duration.ofMillis(2000))
                .until(ExpectedConditions.elementToBeClickable
                        (By.xpath("//a[@href='api/']")))
                .click();

        new WebDriverWait(getDriver(), Duration.ofMillis(2000))
                .until(ExpectedConditions.textToBe(By.tagName("h1"), expectedHeading));

        String actualHeading = getDriver().findElement(By.tagName("h1")).getText();

        List<String> actualSubHeadings = getDriver()
                .findElements(By.tagName("h2"))
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(actualHeading, expectedHeading);
        Assert.assertEquals(actualSubHeadings, expectedSubHeadings);
    }

    @Test
    public void testJenkinsDropdown() {
        getDriver().findElement(By.cssSelector("button.jenkins_ver")).click();

        List<String> expectedDropdownItems = List.of(
                "About Jenkins",
                "Get involved",
                "Website"
        );

        List<WebElement> dropdownItems = new WebDriverWait(getDriver(), Duration.ofMillis(2000))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'jenkins-dropdown__item')]")));

        List<String> actualDropdownItems = dropdownItems
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(actualDropdownItems, expectedDropdownItems);
    }
}
