package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.time.Duration;


public class MultiConfigurationProjectTest extends BaseTest {
    private static final String PROJECT_NAME = "Multiconfiguration project name";
    private static final String RENAMED_PROJECT = "Renamed multiconfiguration project";
    private static final String PROJECT_DESCRIPTION = "Project description...";

    @Test
    public void testCreateProject() {
        String actualProjectName = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndSubmit()
                .clickSubmit()
                .getHeading();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Test()
    public void testAddDescriptionToProject() {
        String actualDescription = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndSubmit()
                .clickSubmit()
                .clearDescriptionField()
                .sendDescription(PROJECT_DESCRIPTION)
                .getDescription();

        Assert.assertEquals(actualDescription, PROJECT_DESCRIPTION);
    }

    @Test
    public void testRenameViaSidebar() {
        String actualProjectName = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndSubmit()
                .clickSubmit()
                .clickRenameLinkInSideMenu()
                .clearNameField()
                .setNewProjectName(RENAMED_PROJECT)
                .getHeading();

        Assert.assertEquals(actualProjectName, RENAMED_PROJECT);
    }

    @Test
    public void testRenameViaDashboardDropdownMenu() {
        String actualProjectName = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndSubmit()
                .clickSubmit()
                .clickRenameViaDashboardDropDownMenu()
                .clearNameField()
                .setNewProjectName(RENAMED_PROJECT)
                .getHeading();

        Assert.assertEquals(actualProjectName, RENAMED_PROJECT);
    }
}
