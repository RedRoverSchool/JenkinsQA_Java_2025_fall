package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.FolderPage;
import school.redrover.page.HomePage;
import java.util.Arrays;
import java.util.List;

public class FolderConfigurationTest extends BaseTest {

   private static final String FOLDER_NAME = "my folder";

    @Test
    public void testHealthMetricLinkIsDisplayed(){
        String linkDisplayed = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(FOLDER_NAME)
                .selectFolderAndSubmit()
                .getHealthMetricsSidebarLink();

        Assert.assertEquals(linkDisplayed, "Health metrics");
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testHealthMetricButtonIsDisplayed(){
        String buttonDisplayed = new HomePage(getDriver())
                .openPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .getHealthMetricsButton();

        Assert.assertEquals(buttonDisplayed,"Health metrics");
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testHealthMetricSectionNavigation(){
        String sectionName = new HomePage(getDriver())
                .openPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .clickHealthMetricsSidebarLink()
                .getSectionName();

        Assert.assertEquals(sectionName,"Health metrics");
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testVerifyMetricTypeList(){

        final  List<String> expectedMetricTypes= Arrays.asList("Child item with the given name","Child item with worst health");

        List<String> actualMetricTypes = new HomePage(getDriver())
                .openPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .clickHealthMetricsSidebarLink()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .getAllMetricTypeNames();

        Assert.assertEquals(actualMetricTypes, expectedMetricTypes,
                "The list of displayed metric types in the dropdown did not match the expected list.");
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testAddWorstHealth() {

        final String expectedMetric = "Child item with worst health";

        String actualMetricAdded = new HomePage(getDriver())
                .openPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .clickWorstHealthButton()
                .getMetricRowName();

        Assert.assertEquals(actualMetricAdded, expectedMetric);
    }

    @Test(dependsOnMethods = "testHealthMetricLinkIsDisplayed")
    public void testAddGivenName() {

        final String expectedMetric = "Child item with the given name";

        String actualMetricAdded = new HomePage(getDriver())
                .openPage(FOLDER_NAME, new FolderPage(getDriver()))
                .clickConfigure()
                .clickHealthMetricsButton()
                .clickAddMetricButton()
                .clickGivenNameButton()
                .getMetricRowName2();

        Assert.assertEquals(actualMetricAdded, expectedMetric);
    }
}
