package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class PipelineConfigurationAdvancedTest extends BaseTest {

    private void createNewPipeline() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("pipeline_01");
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test(testName = "AT_03.005.01")
    public void testNavigationToAdvancedByScrollingDown() {
        createNewPipeline();

        WebElement actualAdvancedSectionTitle = getDriver().findElement(By.id("advanced"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", actualAdvancedSectionTitle);

        Assert.assertEquals(actualAdvancedSectionTitle.getText(), "Advanced");
    }

    @Test     //AT_03.005.02
    public void testNavigationToAdvancedBySideMenu() {
        createNewPipeline();

        WebElement actualAdvancedItemMenu = getDriver().findElement(By.xpath(".//button[@data-section-id='advanced']"));
        actualAdvancedItemMenu.click();

        WebElement actualAdvancedSectionTitle = getDriver().findElement(By.id("advanced"));

        Assert.assertEquals(actualAdvancedItemMenu.getText(), "Advanced");
        Assert.assertEquals(actualAdvancedSectionTitle.getText(), "Advanced");
    }

    @Test     //AT_03.005.03
    public void testAdvancedSectionQuietPeriodElements() {
        createNewPipeline();

        WebElement footer = getDriver().findElement(By.id("footer"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", footer);

        WebElement advancedButton = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By
                        .xpath(".//div[@id='advanced']/parent::section/descendant::button[contains(text(),'Advanced')]")));

        new Actions(getDriver()).moveToElement(advancedButton).click().perform();

        WebElement actualQuietPeriodLabel = getDriver().findElement(By.xpath(".//label[text()='Quiet period']"));
        WebElement actualQuietPeriodCheckbox = getDriver().findElement(By.id("cb13"));

        Assert.assertEquals(actualQuietPeriodLabel.getText(), "Quiet period");
        Assert.assertFalse(actualQuietPeriodCheckbox.isSelected(), "Default Checkbox should not be selected");
    }
}