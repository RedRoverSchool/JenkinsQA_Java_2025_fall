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

    private void createNewPipeline() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("pipeline_01");
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void advancedButtonClick() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("footer")));
        new Actions(getDriver()).scrollToElement(footer).perform();

        WebElement advancedButton = wait.until(ExpectedConditions.elementToBeClickable(By
                .xpath(".//div[@id='advanced']/parent::section/descendant::button[contains(text(),'Advanced')]")));
        new Actions(getDriver()).moveToElement(advancedButton).click().perform();
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

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("footer")));
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", footer);

        WebElement advancedButton = wait.until(ExpectedConditions.elementToBeClickable(By
                .xpath(".//div[@id='advanced']/parent::section/descendant::button[contains(text(),'Advanced')]")));
        new Actions(getDriver()).moveToElement(advancedButton).click().perform();

        WebElement actualQuietPeriodLabel = getDriver().findElement(By.xpath(".//label[text()='Quiet period']"));
        new Actions(getDriver()).moveToElement(actualQuietPeriodLabel).perform();

        WebElement actualQuietPeriodCheckbox = getDriver().findElement(By.id("cb13"));

        Assert.assertEquals(actualQuietPeriodLabel.getText(), "Quiet period");
        Assert.assertFalse(actualQuietPeriodCheckbox.isSelected(), "Default Checkbox should not be selected");
    }

    @Test     //AT_03.005.04
    public void testAdvancedSectionDisplayNameFieldElements() {
        createNewPipeline();
        advancedButtonClick();

        WebElement displayNameLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.
                xpath(".//div[text()='Display Name']")));
        new Actions(getDriver()).moveToElement(displayNameLabel).perform();

        String actualDisplayNameLabel = displayNameLabel.getText().split("\\n")[0];
        WebElement actualDisplayNameInput = getDriver().findElement(By.name("_.displayNameOrNull"));

        Assert.assertEquals(actualDisplayNameLabel, "Display Name");
        Assert.assertTrue(actualDisplayNameInput.getAttribute("value").isEmpty(), "Default Display Name field should be empty");
    }

    @Test     //AT_03.005.05
    public void testAdvancedSectionQuietPeriodElementsAfterSelecting() {
        createNewPipeline();
        advancedButtonClick();

        WebElement actualQuietPeriodCheckbox = getDriver().findElement(By.name("hasCustomQuietPeriod"));
        WebElement actualQuietPeriodLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//label[text()='Quiet period']")));
        new Actions(getDriver()).moveToElement(actualQuietPeriodLabel).click().perform();

        WebElement actualNumberOfSecondsLabel = getDriver().findElement(By.xpath(".//div[text()='Number of seconds']"));
        WebElement actualNumberOfSecondsInput = getDriver().findElement(By.name("quiet_period"));
        String defaultNumberOfSeconds = actualNumberOfSecondsInput.getAttribute("value");

        Assert.assertTrue(actualQuietPeriodCheckbox.isSelected(), "Checkbox should be selected");
        Assert.assertEquals(actualNumberOfSecondsLabel.getText(), "Number of seconds");
        Assert.assertEquals(defaultNumberOfSeconds, "5");
    }

    @Test     //AT_03.005.06
    public void testAdvancedSectionAddDisplayName() {
        String displayName = "PL_01";
        createNewPipeline();
        advancedButtonClick();

        WebElement displayNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .name("_.displayNameOrNull")));
        new Actions(getDriver()).moveToElement(displayNameInput).perform();
        displayNameInput.sendKeys(displayName);

        wait.until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        String actualDisplayNameInStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .tagName("h1"))).getText();
        String actualDisplayNameInBreadcrumbBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//a[text()='%s']".formatted(displayName)))).getText();

        Assert.assertEquals(actualDisplayNameInStatus, displayName);
        Assert.assertEquals(actualDisplayNameInBreadcrumbBar, displayName);

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        String actualDisplayNameInHomePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .id("job_%s".formatted("pipeline_01")))).getText().split("\\n")[0];

        Assert.assertEquals(actualDisplayNameInHomePage, displayName);
    }
}