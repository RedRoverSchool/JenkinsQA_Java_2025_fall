package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.FolderPage;
import school.redrover.page.HomePage;

public class FolderConfigurationTest extends BaseTest {

   private static final String FOLDER_NAME = "my folder";

    @Test
    public void testHealthMetricLinkIsDisplayed(){
        final String linkDisplayed = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit()
                .getHealthMetricsSidebarLink();

        Assert.assertEquals(linkDisplayed, "Health metrics");
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testHealthMetricButtonIsDisplayed(){
        final String buttonDisplayed = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .getHealthMetricsButton();

        Assert.assertEquals(buttonDisplayed,"Health metrics");
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testHealthMetricSectionNavigation(){
        final String sectionName = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .clickHealthMetricsSidebarLink()
                .getSectionName();

        Assert.assertEquals(sectionName,"Health metrics");
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testVerifyMetricTypeList(){
        var configPage = new HomePage(getDriver())
                .openJobPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .clickHealthMetricsButton()
                .clickAddMetricButton();

        final String metricType1 = configPage.getMetricType1();
        final String metricType2 = configPage.getMetricType2();

        Assert.assertEquals(metricType1, "Child item with the given name");
        Assert.assertEquals(metricType2, "Child item with worst health");
    }
}
