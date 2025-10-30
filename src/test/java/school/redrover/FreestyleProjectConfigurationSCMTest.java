package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class FreestyleProjectConfigurationSCMTest extends BaseTest {

    private WebDriverWait wait;
    private static final String SCM_TITLE_EXPECTED = "Source Code Management";
    final String freestyleProjectName = "FreestyleProject2025";

    private void createFreestyleProject(String freestyleProjectName) {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']"))).click();
        getDriver().findElement(By.id("name")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));
    }

    @Test
    public void testAccessSCMInNewJob() {
        // 02.003.01
        createFreestyleProject(freestyleProjectName);

        WebElement scmTitle = getDriver().findElement(By.xpath("//div[@id='source-code-management']"));
        wait.until(ExpectedConditions.visibilityOf(scmTitle));

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }
}
