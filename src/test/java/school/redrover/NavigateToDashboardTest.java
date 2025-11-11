package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class NavigateToDashboardTest extends BaseTest {

    @Test
    public void testCheckAccessDashboardFromLogo(){
        getWait5().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.id("root-action-ManageJenkinsAction")))).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.className("app-jenkins-logo")))).click();

        Assert.assertTrue(getDriver().getTitle().toLowerCase().contains("dashboard"));
    }
}