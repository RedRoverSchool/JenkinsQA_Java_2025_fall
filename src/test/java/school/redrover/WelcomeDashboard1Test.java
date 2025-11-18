package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;

public class WelcomeDashboard1Test extends BaseTest {

    @Test
    public void testSetUtAgent() {

        getDriver().findElement(By.xpath("//a[@href='computer/new']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1")).getText(),
                "New node",
                "New node is not visible"
        );

        Assert.assertTrue(
                getDriver().findElement(By.xpath("//form")).isDisplayed(),
                "New Node form is not visible"
        );
    }

    @Test
    public void testConfigureCloudIntegration() {

        getDriver().findElement(By.xpath("//a[@href='cloud/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1")).getText(),
                "Clouds",
                "Clouds is not visible"
        );

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//p")).getText(),
                "There is no plugin installed that supports clouds.",
                "Message 'There is no plugin installed that supports clouds.' is not visible"
        );
    }

    @Test
    public void testCreateMultiConfigurationProject() {

        final String projectName = "Test Project";

        List<String> projectList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(projectName)
                .selectMultiConfigurationAndSubmit()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertEquals(projectList.get(0), projectName);
    }

}
