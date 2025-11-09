package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FreestylePrjConfigSaveAndApplyTest extends BaseTest {
    final String nameFreestyleProjectItem = "My FreeStyleProject";

    @Test
    public void testSaveButtonIsVisible() {
        createFreeStyleProject();
        goToHomePage();
        goToConfigurationPage();
        scrollToBottomOfPage();
        WebElement saveButton = getWait2()
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@value='Save']")));
        Assert.assertTrue(saveButton.isDisplayed());
    }

    private void goToHomePage() {
        getDriver().findElement(By.xpath("//span[text()='Jenkins']/parent::a")).click();
    }

    private void createFreeStyleProject() {
        getDriver().findElement(By.xpath("//span[text()='New Item']/parent::a")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(nameFreestyleProjectItem);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
    }

    private void goToConfigurationPage() {
        getDriver().findElement(By.xpath("//span[text()='" + nameFreestyleProjectItem + "']")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();
    }

    private void scrollToBottomOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }
}
