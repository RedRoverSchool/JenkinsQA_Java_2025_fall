package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.HomePage;
import school.redrover.page.MultibranchPipelineJobPage;

public class MultibranchPipelineConfigurationTest extends BaseTest {

    private static final String JOB_NAME = "multibranchJobName";
    private static final String JOB_DESCRIPTION = "This is a job description";

    private void clickOnTheToggle() {
        getDriver().findElement(By.cssSelector("[data-title='Disabled']")).click();
    }

    private void addJobDescription(String jobDescription) {
        WebElement jobDescriptionField = getDriver().findElement(By.name("_.description"));

        jobDescriptionField.clear();
        jobDescriptionField.sendKeys(jobDescription);
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

    private void openMultibranchPipelineConfigurationPage(String jobName) {
        TestUtils.clickJS(getDriver(), By.cssSelector("td > a[href='job/%s/']".formatted(jobName)));

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='./configure']")))
                .click();
    }

    @Test
    public void testCreateMultibranchPipelineJob() {
        String actualHeadingText = new HomePage(getDriver())
                .clickNewItem()
                .sendName(JOB_NAME)
                .selectMultibranchPipelineAndSubmit()
                .submitForm()
                .getHeadingText();

        Assert.assertEquals(actualHeadingText, JOB_NAME);
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipelineJob")
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
        final String expectedTooltipText = "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        String actualTooltipText = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineJobPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .getToggleTooltipText();

        Assert.assertEquals(actualTooltipText, expectedTooltipText);
    }

    @Test(dependsOnMethods = "testTooltipOnToggleHover")
    public void testDisabledMessageOnStatusPage() {
        final String expectedDisabledMessage = "This Multibranch Pipeline is currently disabled";

        openMultibranchPipelineConfigurationPage(JOB_NAME);
        clickOnTheToggle();
        submitForm();

        WebElement actualDisabledMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("disabled-message")));

        Assert.assertEquals(actualDisabledMessage.getText(), expectedDisabledMessage);
    }

    @Test(dependsOnMethods = "testDisabledMessageOnStatusPage")
    public void testJobDescriptionPreview() {
        openMultibranchPipelineConfigurationPage(JOB_NAME);
        addJobDescription(JOB_DESCRIPTION);

        getDriver().findElement(By.className("textarea-show-preview")).click();

        WebElement previewTextarea = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("textarea-preview")));

        Assert.assertEquals(previewTextarea.getText(), JOB_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testJobDescriptionPreview")
    public void testMultibranchJobDescription() {
        openMultibranchPipelineConfigurationPage(JOB_NAME);
        addJobDescription(JOB_DESCRIPTION);
        submitForm();

        WebElement actualJobDescription = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(actualJobDescription.getText(), JOB_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testMultibranchJobDescription")
    public void testUpdateJobDescription() {
        final String updatedJobDescription = "This is a new project description";

        openMultibranchPipelineConfigurationPage(JOB_NAME);
        addJobDescription(JOB_DESCRIPTION);
        submitForm();

        getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='./configure']")))
                .click();

        addJobDescription(updatedJobDescription);
        submitForm();

        WebElement actualJobDescription = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(actualJobDescription.getText(), updatedJobDescription);
    }

    @Test(dependsOnMethods = "testUpdateJobDescription")
    public void testRenameJobNameUsingDotAtTheEnd() {
        final String updatedJobName = JOB_NAME + ".";
        final String expectedErrorMessageText = "A name cannot end with ‘.’";

        openJobRenamePage(JOB_NAME);
        renameJob(updatedJobName);
        submitForm();

        WebElement actualErrorMessage = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Error']/../p")));

        Assert.assertEquals(actualErrorMessage.getText(), expectedErrorMessageText);
    }

    @Test(dependsOnMethods = "testRenameJobNameUsingDotAtTheEnd")
    public void testRenameJob() {
        final String updatedJobName = "updatedProjectName";

        openJobRenamePage(JOB_NAME);
        renameJob(updatedJobName);
        submitForm();

        WebElement actualHeading = getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='view-message']/../h1")));

        Assert.assertEquals(actualHeading.getText(), updatedJobName);
    }
}
