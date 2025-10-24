package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class JenkinsVersionTest extends BaseTest {

    @Test
    public void testJenkinsVersion() {
        String actualVersion = getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).getText();

        Assert.assertEquals(actualVersion, "Jenkins 2.516.3");
    }
}
