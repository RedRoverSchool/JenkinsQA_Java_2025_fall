package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class ConfigureAppearanceTest extends BaseTest {

    @Test
    public void testChangeTheme() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='appearance']")).click();
        getDriver().findElement(By.cssSelector("label:has(> div[data-theme='dark'])")).click();

        if (!getDriver().findElement(By.cssSelector("input[name='_.disableUserThemes']")).isSelected()) {
            getDriver().findElement(
                    By.xpath("//label[contains(., 'Do not allow users to select a different theme')]")
            ).click();
        }

        getDriver().findElement(By.cssSelector("button.jenkins-submit-button")).click();

        wait.until(driver -> {
            try {
                WebElement html = driver.findElement(By.cssSelector("html"));
                return "dark".equals(html.getAttribute("data-theme"));
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });


    }

    @Test
    public void testChangeThemeOld() throws InterruptedException {

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='appearance']")).click();
        getDriver().findElement(By.xpath("//label[contains(., 'Do not allow users to select a different theme')]")).click();
        Thread.sleep(2000);
        getWait2().until(ExpectedConditions.elementToBeClickable(By.cssSelector("label:has(> div[data-theme='dark'])")));

        getDriver().findElement(By.cssSelector("label:has(> div[data-theme='dark'])")).click();
        getDriver().findElement(By.xpath("//label[contains(., 'Do not allow users to select a different theme')]")).click();
        getDriver().findElement(By.cssSelector("button.jenkins-submit-button")).click();
        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("html")).getAttribute("data-theme"),
                "dark");
    }
}

