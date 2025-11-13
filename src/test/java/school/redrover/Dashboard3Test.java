package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
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
                .elementToBeClickable(By.id("jenkins-head-icon"))).click();
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

        Assert.assertFalse(actualJobs.isEmpty(), "Item's list is empty!");
        Assert.assertEquals(actualJobs, createdJobsName);
    }

    @Ignore
    @Test
    public void testCheckDeleteViewOnDashboard(){
        final String viewName = "myview";
        createFreestyle("FreestyleName");

        getDriver().findElement(By.className("addTab")).click();
        getDriver().findElement(By.id("name")).sendKeys(viewName);
        getDriver().findElement(By.xpath("(//*[contains(@class, 'jenkins-radio__label')])[2]")).click();
        getDriver().findElement(By.id("ok")).click();

        getDriver().findElement(By.cssSelector("a[data-title='Delete View']")).click();
        getWait5().until(ExpectedConditions
                .elementToBeClickable(By.cssSelector("button[data-id='ok']"))).click();

        String viewPanelElements = getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.className("tabBarFrame"))).getText();
        Assert.assertFalse(viewPanelElements.contains(viewName));
    }
}
