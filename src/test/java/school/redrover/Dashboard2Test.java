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

    @Test
    public void testWelcomeMessageAndParagraghText() {
        String welcomeMessage = getDriver().findElement(By.xpath("//div[@id='view-message']/following::h1"))
                .getText();
        String paragraghText = getDriver().findElement(By.xpath("//div[@id='view-message']/following::p"))
                .getText();

        Assert.assertEquals(welcomeMessage, "Welcome to Jenkins!");
        Assert.assertEquals(paragraghText, "This page is where your Jenkins jobs will be displayed. To get started, " +
                "you can set up distributed builds or start building a software project.");
    }

    @DataProvider(name = "Links")
    Object[][] linkData() {
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

    @Test
    public void testLearnMoreAboutDistributedBuildsButton() {

        getDriver().findElement(By.xpath(".//a[span[text()='Learn more about distributed builds']]"))
                .click();
        Object[] windowHandles = getDriver().getWindowHandles().toArray();
        getDriver().switchTo().window((String) windowHandles[1]);

        new WebDriverWait(getDriver(), Duration.ofMillis(2000))
                .until(ExpectedConditions.urlContains("architecting-for-scale"));

        String actualHeading = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(actualHeading, "Architecting for Scale");
    }
}
