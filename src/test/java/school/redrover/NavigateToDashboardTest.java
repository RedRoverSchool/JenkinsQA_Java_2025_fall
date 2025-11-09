package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class NavigateToDashboardTest extends BaseTest {
    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
    By systemConfBtn = By.id("root-action-ManageJenkinsAction");
    By headerNavigation = By.className("app-jenkins-logo");

    @Test
    public void testCheckAccessDashboardFromLogo(){
        clickElement(systemConfBtn);
        clickElement(headerNavigation);

        Assert.assertTrue(getDriver().getTitle().toLowerCase().contains("dashboard"));
    }

    void clickElement(By element){
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(element))).click();
    }
}
