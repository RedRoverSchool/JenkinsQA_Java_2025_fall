package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class FreestyleProjectGeneralSettingsTest extends BaseTest {

    public void createFreestyleProject(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver()
                .findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys("testProject");
        getDriver()
                .findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']"))
                .click();
    }
    @Test
    public void testGeneralSectionAvailableAfterOKbutton(){
        createFreestyleProject();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        boolean generalIsDisplayed = getDriver().findElement(By.xpath("//h2[@id='general']"))
                                                .isDisplayed();
        Assert.assertTrue(generalIsDisplayed);
    }
}
