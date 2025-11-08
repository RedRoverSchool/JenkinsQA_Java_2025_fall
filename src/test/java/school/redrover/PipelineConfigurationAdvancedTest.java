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

    private WebDriverWait wait;

    private void createNewPipeline(String newPipelineName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(newPipelineName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void advancedButtonClick() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.xpath(".//button[@data-section-id='advanced']")).click();

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("footer"))));

        WebElement advancedButton = wait.until(ExpectedConditions.elementToBeClickable(By
                .xpath(".//div[@id='advanced']/parent::section/descendant::button[contains(text(),'Advanced')]")));
        new Actions(getDriver()).moveToElement(advancedButton).click().perform();
    }

    @Test
    public void testNavigationToAdvancedByScrollingDown() {
        String newPipelineName = "newPipeline_01";
        createNewPipeline(newPipelineName);

        WebElement actualAdvancedSectionTitle = getDriver().findElement(By.id("advanced"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", actualAdvancedSectionTitle);

        Assert.assertEquals(actualAdvancedSectionTitle.getText(), "Advanced");
    }

    @Test
    public void testNavigationToAdvancedBySideMenu() {
        String newPipelineName = "newPipeline_02";
        createNewPipeline(newPipelineName);

        WebElement actualAdvancedItemMenu = getDriver().findElement(By.xpath(".//button[@data-section-id='advanced']"));
        actualAdvancedItemMenu.click();

        WebElement actualAdvancedSectionTitle = getDriver().findElement(By.id("advanced"));

        Assert.assertEquals(actualAdvancedItemMenu.getText(), "Advanced");
        Assert.assertEquals(actualAdvancedSectionTitle.getText(), "Advanced");
    }

    @Test
    public void testAdvancedSectionQuietPeriodElements() {
        final String newPipelineName = "newPipeline_03";
        createNewPipeline(newPipelineName);
        advancedButtonClick();

        WebElement actualQuietPeriodLabel = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.
                xpath(".//label[text()='Quiet period']")));
        new Actions(getDriver()).moveToElement(actualQuietPeriodLabel).perform();

        WebElement actualQuietPeriodCheckbox = getDriver().findElement(By.id("cb13"));

        Assert.assertEquals(actualQuietPeriodLabel.getText(), "Quiet period");
        Assert.assertFalse(actualQuietPeriodCheckbox.isSelected(), "Default Checkbox should not be selected");
    }

    @Test
    public void testAdvancedSectionDisplayNameFieldElements() {
        String newPipelineName = "newPipeline_04";
        createNewPipeline(newPipelineName);
        advancedButtonClick();

        WebElement displayNameLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.
                xpath(".//div[text()='Display Name']")));
        new Actions(getDriver()).moveToElement(displayNameLabel).perform();

        String actualDisplayNameLabel = displayNameLabel.getText().split("\\n")[0];
        WebElement actualDisplayNameInput = getDriver().findElement(By.name("_.displayNameOrNull"));

        Assert.assertEquals(actualDisplayNameLabel, "Display Name");
        Assert.assertTrue(actualDisplayNameInput.getAttribute("value").isEmpty(), "Default Display Name field should be empty");
    }

    @Test
    public void testAdvancedSectionQuietPeriodElementsAfterSelecting() {
        String newPipelineName = "newPipeline_05";
        createNewPipeline(newPipelineName);
        advancedButtonClick();

        WebElement actualQuietPeriodLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//label[text()='Quiet period']")));
        new Actions(getDriver()).moveToElement(actualQuietPeriodLabel).click().perform();

        WebElement actualQuietPeriodCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .name("hasCustomQuietPeriod")));
        WebElement actualNumberOfSecondsLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//div[text()='Number of seconds']")));
        WebElement actualNumberOfSecondsInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .name("quiet_period")));

        Assert.assertTrue(actualQuietPeriodCheckbox.isSelected(), "Checkbox should be selected");
        Assert.assertEquals(actualNumberOfSecondsLabel.getText(), "Number of seconds");
        Assert.assertTrue(actualNumberOfSecondsInput.isDisplayed(), "'Number of seconds' input should be displayed");
    }

    @Test
    public void testAdvancedSectionAddDisplayName() {
        final String pipelineName = "pipeline_01";
        final String displayName = "PL_01";
        createNewPipeline(pipelineName);
        advancedButtonClick();

        WebElement displayNameInput = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .name("_.displayNameOrNull")));
        new Actions(getDriver()).moveToElement(displayNameInput).perform();
        displayNameInput.sendKeys(displayName);

        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        String actualDisplayNameInStatus = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .tagName("h1"))).getText();
        String actualDisplayNameInBreadcrumbBar = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//a[text()='%s']".formatted(displayName)))).getText();

        Assert.assertEquals(actualDisplayNameInStatus, displayName);
        Assert.assertEquals(actualDisplayNameInBreadcrumbBar, displayName);

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        String actualDisplayNameInHomePage = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .id("job_%s".formatted(pipelineName)))).getText().split("\\n")[0];

        Assert.assertEquals(actualDisplayNameInHomePage, displayName);
    }
}