package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CorrectVersionJenkinsTest extends BaseTest {

    @Test
    public void testJenkinsCorrectVersion() {
        String jenkinsVersionNumber = getDriver().findElement(By.cssSelector("button.jenkins-button.jenkins-button--tertiary.jenkins_ver"))
                        .getText();

        Assert.assertEquals(jenkinsVersionNumber, "Jenkins 2.516.3");

    }
}
