package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.UUID;

public class MultibranchPipelineConfigurationTest extends BaseTest {

    private final String projectName = getRandomAlphaNumericText();

    private void createMultibranchPipelineProject(String projectName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].click();",
                getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']"))
        );

        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("general")));
    }

    private void clickOnTheToggle() {
        getDriver().findElement(By.cssSelector("[data-title='Disabled']")).click();
    }

    private String getRandomAlphaNumericText() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private void addProjectDescription(String projectDescription) {
        WebElement projectDescriptionField = getDriver().findElement(By.name("_.description"));

        projectDescriptionField.clear();
        projectDescriptionField.sendKeys(projectDescription);
    }

    private void renameProjectName(String updatedProjectName) {
        WebElement newNameField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName")));

        newNameField.clear();
        newNameField.sendKeys(updatedProjectName);
    }

    private void submitForm() {
        getDriver().findElement(By.tagName("form")).submit();
    }

    @Test
    public void testDisableToggle() {
        createMultibranchPipelineProject(projectName);
        clickOnTheToggle();

        WebElement disabledTitle = getDriver().findElement(By.cssSelector("[class$='unchecked-title'"));
        getWait5().until(ExpectedConditions.textToBePresentInElement(disabledTitle, "Disabled"));

        Assert.assertTrue(disabledTitle.isDisplayed());
    }

    @Test
    public void testTooltipOnToggleHover() {
        final String expectedTooltip = "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        createMultibranchPipelineProject(projectName);

        WebElement toggleElement = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("toggle-switch-enable-disable-project")));

        new Actions(getDriver()).moveToElement(toggleElement).perform();

        String actualTooltip = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("tippy-content")))
                .getText();

        Assert.assertEquals(actualTooltip, expectedTooltip);
    }

    @Test
    public void testDisabledMessageOnStatusPage() {
        final String expectedDisabledMessage = "This Multibranch Pipeline is currently disabled";

        createMultibranchPipelineProject(projectName);
        clickOnTheToggle();
        submitForm();

        WebElement actualDisabledMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("disabled-message")));

        Assert.assertEquals(actualDisabledMessage.getText(), expectedDisabledMessage);
    }

    @Test
    public void testProjectDescriptionPreview() {
        final String projectDescription = getRandomAlphaNumericText();

        createMultibranchPipelineProject(projectName);
        addProjectDescription(projectDescription);

        getDriver().findElement(By.className("textarea-show-preview")).click();

        WebElement previewTextarea = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("textarea-preview")));

        Assert.assertEquals(previewTextarea.getText(), projectDescription);
    }

    @Test
    public void testMultibranchProjectDescription() {
        final String projectDescriptionText = getRandomAlphaNumericText();

        createMultibranchPipelineProject(projectName);
        addProjectDescription(projectDescriptionText);
        submitForm();

        WebElement actualProjectDescription = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(actualProjectDescription.getText(), projectDescriptionText);
    }

    @Test
    public void testUpdateProjectDescription() {
        final String initialProjectDescription = getRandomAlphaNumericText();
        final String updatedProjectDescription = getRandomAlphaNumericText();

        createMultibranchPipelineProject(projectName);
        addProjectDescription(initialProjectDescription);
        submitForm();

        getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='./configure']")))
                .click();

        addProjectDescription(updatedProjectDescription);
        submitForm();

        WebElement actualProjectDescription = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(actualProjectDescription.getText(), updatedProjectDescription);
    }

    @Test
    public void testUpdateProjectName() {
        final String updatedProjectName = getRandomAlphaNumericText();

        createMultibranchPipelineProject(projectName);
        submitForm();

        getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href$='/confirm-rename']")))
                .click();

        WebElement newNameField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName")));
        newNameField.clear();
        newNameField.sendKeys(updatedProjectName);
        submitForm();

        getWait5().until(ExpectedConditions.urlContains("/job"));

        WebElement actualHeading = getDriver().findElement(By.tagName("h1"));

        Assert.assertEquals(actualHeading.getText(), updatedProjectName);
    }

    @Test
    public void testRenameProjectNameUsingDotAtTheEnd() {
        final String updatedProjectName = getRandomAlphaNumericText() + ".";
        final String expectedErrorMessageText = "A name cannot end with ‘.’";

        createMultibranchPipelineProject(projectName);
        submitForm();

        getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href$='/confirm-rename']")))
                .click();

        renameProjectName(updatedProjectName);
        submitForm();

        WebElement actualErrorMessage = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Error']/../p")));

        Assert.assertEquals(actualErrorMessage.getText(), expectedErrorMessageText);
    }
}
