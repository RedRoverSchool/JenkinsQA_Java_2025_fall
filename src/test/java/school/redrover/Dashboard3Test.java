package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.util.List;

public class Dashboard3Test extends BaseTest {

    private void createFreestyle(String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getWait5().until(ExpectedConditions
                .elementToBeClickable(By.cssSelector("span.jenkins-mobile-hide"))).click();
    }

    @Test
    public void testCheckCreatedJobsOnDashboard(){

        final List<String> createdJobsName = List.of(
                "FreestyleName1",
                "FreestyleName2",
                "FreestyleName3",
                "FreestyleName4",
                "FreestyleName5"
        );

        for (int i = 0; i < createdJobsName.size(); i++) {
            createFreestyle(createdJobsName.get(i));
        }

        List<String> actualJobs = getDriver()
                .findElements(By.cssSelector(".jenkins-table__link >span:first-child"))
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(actualJobs, createdJobsName);
    }
}
