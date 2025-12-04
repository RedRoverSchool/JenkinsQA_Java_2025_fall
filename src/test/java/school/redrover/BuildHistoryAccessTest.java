package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.BuildHistoryOfJenkinsPage;
import school.redrover.page.HomePage;

import java.util.Arrays;
import java.util.List;

public class BuildHistoryAccessTest extends BaseTest {

    @Test
    public void testQuickAccessBuildHistory() {
        String actualHeading = new HomePage(getDriver())
                .openPage("Build History", new BuildHistoryOfJenkinsPage(getDriver()))
                .getHeadingText();

        Assert.assertEquals(actualHeading, "Build History of Jenkins");
    }

    @Test
    public void testValidateBuildHistoryEmptyTable() {
        final List<String> expectedHeaders = Arrays.asList("S", "Build", "Time Since", "Status");

        BuildHistoryOfJenkinsPage buildHistoryOfJenkinsPage = new HomePage(getDriver())
                .openPage("Build History", new BuildHistoryOfJenkinsPage(getDriver()));

        Assert.assertTrue(buildHistoryOfJenkinsPage.isBuildHistoryEmpty());
        Assert.assertEquals(buildHistoryOfJenkinsPage.getTableHeadersText(), expectedHeaders);
    }
}
