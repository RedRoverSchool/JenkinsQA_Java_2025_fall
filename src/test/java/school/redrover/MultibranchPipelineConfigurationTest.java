package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.HomePage;
import school.redrover.page.MultibranchPipelineJobPage;

public class MultibranchPipelineConfigurationTest extends BaseTest {

    private static final String JOB_NAME = "multibranchProjectName";
    private static final String JOB_DESCRIPTION = "This is a project description";

    private void clickOnTheToggle() {
        getDriver().findElement(By.cssSelector("[data-title='Disabled']")).click();
    }

    private void addJobDescription(String jobDescription) {
        WebElement projectDescriptionField = getDriver().findElement(By.name("_.description"));

        projectDescriptionField.clear();
        projectDescriptionField.sendKeys(jobDescription);
    }

    private void renameJob(String updatedJobName) {
        WebElement newNameField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName")));

        newNameField.clear();
        newNameField.sendKeys(updatedJobName);
    }

    private void submitForm() {
        getDriver().findElement(By.tagName("form")).submit();
    }

    private void openJobRenamePage(String jobName) {
        TestUtils.clickJS(getDriver(), By.cssSelector("td > a[href='job/%s/']".formatted(jobName)));

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href$='/confirm-rename']")))
                .click();
    }

    private void openJobConfigurationPage(String jobName) {
        TestUtils.clickJS(getDriver(), By.cssSelector("td > a[href='job/%s/']".formatted(jobName)));

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='./configure']")))
                .click();
    }

    @Test
    public void testCreateMultibranchPipelineProject() {
        String actualHeadingText = new HomePage(getDriver())
                .clickNewItem()
                .sendName(JOB_NAME)
                .selectMultibranchPipelineAndSubmit()
                .submitForm()
                .getHeadingText();

        Assert.assertEquals(actualHeadingText, JOB_NAME);
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipelineProject")
    public void testDisableToggle() {
        final String expectedToggleState = "Disabled";

        String actualToggleState = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineJobPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickToggle()
                .getToggleState();

        Assert.assertEquals(actualToggleState, expectedToggleState);
    }

    @Test(dependsOnMethods = "testDisableToggle")
    public void testTooltipOnToggleHover() {
        final String expectedTooltip = "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        openJobConfigurationPage(JOB_NAME);

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

        openJobConfigurationPage(JOB_NAME);
        clickOnTheToggle();
        submitForm();

        WebElement actualDisabledMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("disabled-message")));

        Assert.assertEquals(actualDisabledMessage.getText(), expectedDisabledMessage);
    }

    @Test(dependsOnMethods = "testDisabledMessageOnStatusPage")
    public void testProjectDescriptionPreview() {
        openJobConfigurationPage(JOB_NAME);
        addJobDescription(JOB_DESCRIPTION);

        getDriver().findElement(By.className("textarea-show-preview")).click();

        WebElement previewTextarea = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("textarea-preview")));

        Assert.assertEquals(previewTextarea.getText(), JOB_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testProjectDescriptionPreview")
    public void testMultibranchProjectDescription() {
        openJobConfigurationPage(JOB_NAME);
        addJobDescription(JOB_DESCRIPTION);
        submitForm();

        WebElement actualProjectDescription = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(actualProjectDescription.getText(), JOB_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testMultibranchProjectDescription")
    public void testUpdateProjectDescription() {
        final String updatedProjectDescription = "This is a new project description";

        openJobConfigurationPage(JOB_NAME);
        addJobDescription(JOB_DESCRIPTION);
        submitForm();

        getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='./configure']")))
                .click();

        addJobDescription(updatedProjectDescription);
        submitForm();

        WebElement actualProjectDescription = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(actualProjectDescription.getText(), updatedProjectDescription);
    }

    @Test(dependsOnMethods = "testUpdateProjectDescription")
    public void testRenameProjectNameUsingDotAtTheEnd() {
        final String updatedProjectName = JOB_NAME + ".";
        final String expectedErrorMessageText = "A name cannot end with ‘.’";

        openJobRenamePage(JOB_NAME);
        renameJob(updatedProjectName);
        submitForm();

        WebElement actualErrorMessage = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Error']/../p")));

        Assert.assertEquals(actualErrorMessage.getText(), expectedErrorMessageText);
    }

    @Test(dependsOnMethods = "testRenameProjectNameUsingDotAtTheEnd")
    public void testRenameProject() {
        final String updatedProjectName = "updatedProjectName";

        openJobRenamePage(JOB_NAME);
        renameJob(updatedProjectName);
        submitForm();

        WebElement actualHeading = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='view-message']/../h1")));

        Assert.assertEquals(actualHeading.getText(), updatedProjectName);
    }
}
