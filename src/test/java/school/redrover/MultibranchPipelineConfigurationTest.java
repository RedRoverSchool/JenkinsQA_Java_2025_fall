package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;

public class MultibranchPipelineConfigurationTest extends BaseTest {

    private static final String PROJECT_NAME = "multibranchProjectName";
    private static final String PROJECT_DESCRIPTION = "This is a project description";

    private void clickOnTheToggle() {
        getDriver().findElement(By.cssSelector("[data-title='Disabled']")).click();
    }

    private void addProjectDescription(String projectDescription) {
        WebElement projectDescriptionField = getDriver().findElement(By.name("_.description"));

        projectDescriptionField.clear();
        projectDescriptionField.sendKeys(projectDescription);
    }

    private void renameProject(String updatedProjectName) {
        WebElement newNameField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName")));

        newNameField.clear();
        newNameField.sendKeys(updatedProjectName);
    }

    private void submitForm() {
        getDriver().findElement(By.tagName("form")).submit();
    }

    private void openProjectRenamePage(String projectName) {
        TestUtils.clickJS(getDriver(), By.cssSelector("td > a[href='job/%s/']".formatted(projectName)));

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href$='/confirm-rename']")))
                .click();
    }

    private void openProjectConfigurationPage(String projectName) {
        TestUtils.clickJS(getDriver(), By.cssSelector("td > a[href='job/%s/']".formatted(projectName)));

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='./configure']")))
                .click();
    }

    @Test
    public void testCreateMultibranchPipelineProject() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);

        TestUtils.clickJS(getDriver(), By.cssSelector("[class$='MultiBranchProject']"));
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        getWait5().until(ExpectedConditions.urlContains("/configure"));
        submitForm();

        WebElement actualHeading = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#main-panel > h1")));
        Assert.assertEquals(actualHeading.getText(), PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipelineProject")
    public void testDisableToggle() {
        openProjectConfigurationPage(PROJECT_NAME);
        clickOnTheToggle();

        WebElement disabledTitle = getDriver().findElement(By.cssSelector("[class$='unchecked-title'"));
        getWait5().until(ExpectedConditions.textToBePresentInElement(disabledTitle, "Disabled"));

        Assert.assertTrue(disabledTitle.isDisplayed());
    }

    @Test(dependsOnMethods = "testDisableToggle")
    public void testTooltipOnToggleHover() {
        final String expectedTooltip = "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        openProjectConfigurationPage(PROJECT_NAME);

        WebElement toggleElement = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("toggle-switch-enable-disable-project")));

        new Actions(getDriver()).moveToElement(toggleElement).perform();

        String actualTooltip = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("tippy-content")))
                .getText();

        Assert.assertEquals(actualTooltip, expectedTooltip);
    }

    @Test(dependsOnMethods = "testTooltipOnToggleHover")
    public void testDisabledMessageOnStatusPage() {
        final String expectedDisabledMessage = "This Multibranch Pipeline is currently disabled";

        openProjectConfigurationPage(PROJECT_NAME);
        clickOnTheToggle();
        submitForm();

        WebElement actualDisabledMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("disabled-message")));

        Assert.assertEquals(actualDisabledMessage.getText(), expectedDisabledMessage);
    }

    @Test(dependsOnMethods = "testDisabledMessageOnStatusPage")
    public void testProjectDescriptionPreview() {
        openProjectConfigurationPage(PROJECT_NAME);
        addProjectDescription(PROJECT_DESCRIPTION);

        getDriver().findElement(By.className("textarea-show-preview")).click();

        WebElement previewTextarea = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("textarea-preview")));

        Assert.assertEquals(previewTextarea.getText(), PROJECT_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testProjectDescriptionPreview")
    public void testMultibranchProjectDescription() {
        openProjectConfigurationPage(PROJECT_NAME);
        addProjectDescription(PROJECT_DESCRIPTION);
        submitForm();

        WebElement actualProjectDescription = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(actualProjectDescription.getText(), PROJECT_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testMultibranchProjectDescription")
    public void testUpdateProjectDescription() {
        final String updatedProjectDescription = "This is a new project description";

        openProjectConfigurationPage(PROJECT_NAME);
        addProjectDescription(PROJECT_DESCRIPTION);
        submitForm();

        getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='./configure']")))
                .click();

        addProjectDescription(updatedProjectDescription);
        submitForm();

        WebElement actualProjectDescription = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(actualProjectDescription.getText(), updatedProjectDescription);
    }

    @Test(dependsOnMethods = "testUpdateProjectDescription")
    public void testRenameProjectNameUsingDotAtTheEnd() {
        final String updatedProjectName = PROJECT_NAME + ".";
        final String expectedErrorMessageText = "A name cannot end with ‘.’";

        openProjectRenamePage(PROJECT_NAME);
        renameProject(updatedProjectName);
        submitForm();

        WebElement actualErrorMessage = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Error']/../p")));

        Assert.assertEquals(actualErrorMessage.getText(), expectedErrorMessageText);
    }

    @Test(dependsOnMethods = "testRenameProjectNameUsingDotAtTheEnd")
    public void testRenameProject() {
        final String updatedProjectName = "updatedProjectName";

        openProjectRenamePage(PROJECT_NAME);
        renameProject(updatedProjectName);
        submitForm();

        WebElement actualHeading = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#main-panel h1")));

        Assert.assertEquals(actualHeading.getText(), updatedProjectName);
    }
}
