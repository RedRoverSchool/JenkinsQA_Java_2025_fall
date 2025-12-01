package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.HomePage;
import java.util.List;


public class FooterTest extends BaseTest {

 final String namePage = "REST API";

    @Test
    public void testRestApiLink() {
        String linkText = new HomePage(getDriver())
                .getRestApiLinkText();

        Assert.assertEquals(
                linkText,
                namePage);
    }

    @Test
    public void testApiPageHeading() {
        String actualHeading = new HomePage(getDriver())
                .clickRestApiLink()
                .getHeadingText();

        Assert.assertEquals(
                actualHeading,
                namePage);
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

        Assert.assertEquals(
                actualLinks,
                expectedLinks);
    }

    @Test
    public void testRestApiLinkByTabAndEnter() {
        new HomePage(getDriver())
                .pressTabAndEnter(new HomePage(getDriver()).getRestApiLink());

        Assert.assertEquals(
                getDriver().getTitle(),
                "Remote API - Jenkins");
    }

    @Test
    public void testRestApiLinkByFocusAndEnter() {
        TestUtils.focusAndEnterByKeyboard(getDriver(), new HomePage(getDriver()).getRestApiLink());

        Assert.assertEquals(
                getDriver().getTitle(),
                "Remote API - Jenkins");
    }

    @Test
    public void testJenkinsVersion() {
        String version = new HomePage(getDriver())
                .getJenkinsVersion();

        Assert.assertEquals(
                version,
                "Jenkins 2.516.3");
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

        Assert.assertEquals(
                actualDropdownItems,
                expectedDropdownItems);
    }

    @Test
    public void testRestApiUserPage() {
        String actualHeading = new HomePage(getDriver())
                .clickUserAccountIcon()
                .clickRestApiLink()
                .getNamePage();

       Assert.assertEquals(
                actualHeading,
                namePage);
    }
@Ignore
    @Test
    public void testRestApiNewItemPage() {
        String actualHeading = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .clickRestApiLink()
                .getNamePage();

        Assert.assertEquals(
                actualHeading,
                namePage);
    }

    @Test
    public void testRestApiNewNodesPage() {
        String actualHeading = new HomePage(getDriver())
                .clickSetUpAnAgent()
                .clickRestApiLink()
                .getNamePage();

        Assert.assertEquals(
                actualHeading,
                namePage);
    }

    @Test
    public void testRestApiNodesPage() {
        String actualHeading = new HomePage(getDriver())
               .clickBuildExecutorStatus()
               .clickRestApiLink()
               .getNamePage();

        Assert.assertEquals(
                actualHeading,
                namePage);
    }

    @Test
    public void testRestApiBuildHistoryOfJenkinsPage() {
        String actualHeading = new HomePage(getDriver())
                .clickBuildHistory()
                .clickRestApiLink()
                .getNamePage();

        Assert.assertEquals(
                actualHeading,
                namePage);
    }
}
