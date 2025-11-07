package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateNewitem3Test extends BaseTest {
@Ignore
    @Test
    public void createNewitem() throws InterruptedException {
        final String item = "Test project";

        getDriver().findElement(By.xpath("//a[@href='newJob']"))
                .click();
        getDriver().findElement(By.id("name"))
                .sendKeys("Test project");
        getDriver().findElement(By.id("j-add-item-type-standalone-projects"))
                .click();
        WebElement organization = getDriver().findElement(By.id("ok-button"));
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", organization);
        organization.click();
        getDriver().findElement(By.id("jenkins-head-icon"))
                .click();

        Thread.sleep(3000);
        WebElement message = getDriver().findElement(By.xpath("//*[@id='job_Test project']/td[3]/a"));

        Assert.assertEquals(message.getText(), item);
    }

}
