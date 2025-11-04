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
import java.util.UUID;

public class MultibranchPipelineConfigurationTest extends BaseTest {

    private WebDriverWait wait;

    private void createMultibranchPipelineProject() {
        final String projectName = getRandomAlphaNumericText();

        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();

        getDriver().findElement(By.id("name")).sendKeys(projectName);

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].click();",
                getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']"))
        );

        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));
    }

    private void clickOnTheToggle() {
        getDriver().findElement(By.cssSelector("[data-title='Disabled']")).click();
    }

    private String getRandomAlphaNumericText() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private void addProjectDescription(String projectDescription) {
        getDriver().findElement(By.name("_.description")).sendKeys(projectDescription);
    }

    private void clickSaveButton() {
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testDisableToggle() {
        createMultibranchPipelineProject();
        clickOnTheToggle();

        WebElement disabledTitle = getDriver().findElement(By.cssSelector("[class$='unchecked-title'"));
        wait.until(ExpectedConditions.textToBePresentInElement(disabledTitle, "Disabled"));

        Assert.assertTrue(disabledTitle.isDisplayed());
    }

    @Test
    public void testTooltipOnToggleHover() {
        final String expectedTooltip = "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        createMultibranchPipelineProject();

        WebElement toggleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("toggle-switch-enable-disable-project")
        ));

        new Actions(getDriver()).moveToElement(toggleElement).perform();

        String actualTooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("tippy-content")))
                .getText();

        Assert.assertEquals(actualTooltip, expectedTooltip);
    }

    @Test
    public void testDisabledMessageOnStatusPage() {
        final String expectedDisabledMessage = "This Multibranch Pipeline is currently disabled";

        createMultibranchPipelineProject();
        clickOnTheToggle();
        clickSaveButton();

        WebElement actualDisabledMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("disabled-message")));

        Assert.assertEquals(actualDisabledMessage.getText(), expectedDisabledMessage);
    }

    @Test
    public void testProjectDescriptionPreview() {
        final String projectDescription = getRandomAlphaNumericText();

        createMultibranchPipelineProject();
        addProjectDescription(projectDescription);

        getDriver().findElement(By.className("textarea-show-preview")).click();

        WebElement previewTextarea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("textarea-preview")));

        Assert.assertEquals(previewTextarea.getText(), projectDescription);
    }

    @Test
    public void testMultibranchProjectDescription() {
        final String projectDescriptionText = getRandomAlphaNumericText();

        createMultibranchPipelineProject();
        addProjectDescription(projectDescriptionText);
        clickSaveButton();

        WebElement actualProjectDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(actualProjectDescription.getText(), projectDescriptionText);
    }

    @Test
    public void testUpdateProjectDescription() {
        final String initialProjectDescription = getRandomAlphaNumericText();
        final String updatedProjectDescription = getRandomAlphaNumericText();

        createMultibranchPipelineProject();
        addProjectDescription(initialProjectDescription);
        clickSaveButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='./configure']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("_.description"))).clear();

        addProjectDescription(updatedProjectDescription);
        clickSaveButton();

        WebElement actualProjectDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(actualProjectDescription.getText(), updatedProjectDescription);
    }
}
