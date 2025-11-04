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

    @Test
    public void testJenkinsVersionFromAboutInfo() {

        final String version = "Version 2.516.3";
        getDriver().findElement(By.cssSelector(".page-footer__links > :nth-child(2)")).click();
        getDriver().findElement(By.cssSelector(".jenkins-dropdown > :nth-child(1)")).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".app-about-version")).getText(), version);
    }

    @Test
    public void testVersion() {

       Assert.assertEquals(getDriver().findElement(By.cssSelector("#jenkins > footer > div > div.page-footer__links > button")).getText(), "Jenkins 2.516.3");
    }

}
