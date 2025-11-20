package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class JenkinsVersionTest extends BaseTest {

    final String requiredlVersion = "Jenkins 2.516.3";

    @Ignore
    @Test
    public void testJenkinsVersion() {
        String actualVersion = getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).getText();

        Assert.assertEquals(actualVersion, "Jenkins 2.516.3");
    }

    @Test
    public void testVersion() {
        String actualVersion = new HomePage(getDriver()).getJenkinsVersion();
        Assert.assertEquals(actualVersion, requiredlVersion);
    }
}
