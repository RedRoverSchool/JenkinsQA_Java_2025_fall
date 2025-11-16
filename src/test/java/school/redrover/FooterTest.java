package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.HomePage;

import java.time.Duration;
import java.util.List;

public class FooterTest extends BaseTest {

    @Test
    public void testRestApiLink() {
        String linkText = new HomePage(getDriver())
                .getRestApiLinkText();

        Assert.assertEquals(linkText, "REST API");
    }

    @Test
    public void testApiPageHeading() {
        String actualHeading = new HomePage(getDriver())
                .clickRestApiLink()
                .getHeadingText();

        Assert.assertEquals(actualHeading, "REST API");
    }

    @Test
    public void testApiPageContentLinks() {

        final List<String> expectedLinks = List.of(
                "XML API",
                "JSON API",
                "Python API"
        );

        List<String> actualLinks = new HomePage(getDriver())
                .clickRestApiLink()
                .getXmlJsonPythonApiLinksText();

        Assert.assertEquals(actualLinks, expectedLinks);
    }

    @Test
    public void testRestApiLinkByTabAndEnter(){

        new HomePage(getDriver())
                .pressTabAndEnter(new HomePage(getDriver()).getRestApiLink());

        Assert.assertEquals(getDriver().getTitle(), "Remote API - Jenkins");
    }

    @Test
    public void testRestApiLinkByFocusAndEnter(){

        TestUtils.focusAndEnterByKeyboard(getDriver(), new HomePage(getDriver()).getRestApiLink());

        Assert.assertEquals(getDriver().getTitle(), "Remote API - Jenkins");
    }

    @Test
    public void testJenkinsDropdownMenu() {

        final List<String> expectedDropdownItems = List.of(
                "About Jenkins",
                "Get involved",
                "Website"
        );

        List<String> actualDropdownItems = new HomePage(getDriver())
                .clickJenkinsVersion()
                .getDropdownList();

        Assert.assertEquals(actualDropdownItems, expectedDropdownItems);
    }

    @Test
    public void testJenkinsVersionOptions() {
        getDriver().findElement(By.cssSelector("button.jenkins_ver")).click();
        getDriver().findElement(By.xpath("//a[@href='/manage/about']")).click();
        String breadсrumbName = getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[2]")).getText();

        Assert.assertEquals(breadсrumbName, "About Jenkins");

        WebElement jenkinsLogo = getDriver().findElement(By.className("jenkins-mobile-hide"));
        jenkinsLogo.click();

        getDriver().findElement(By.cssSelector("button.jenkins_ver")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, '/participate')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Participate and Contribute");
        getDriver().navigate().back();

        getDriver().findElement(By.cssSelector("button.jenkins_ver")).click();
        getDriver().findElement(By.xpath("//a[text()='\n" +
                "          Website\n" +
                "                    \n" +
                "          \n" +
                "      ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Jenkins");
    }
}
