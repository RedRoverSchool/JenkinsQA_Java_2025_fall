package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class LogoTest extends BaseTest {

    @Test
    public void testLogo() {

        WebElement logo = getDriver().findElement(By.className("app-jenkins-logo"));

        String logoText = logo.findElement(By.xpath(".//span[@class='jenkins-mobile-hide']")).getText();

        logo.click();

        Assert.assertEquals(logoText, "Jenkins", "Надпись рядом с логотипом должна быть 'Jenkins'");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("localhost"), "URL должен содержать 'localhost'");
    }
}
