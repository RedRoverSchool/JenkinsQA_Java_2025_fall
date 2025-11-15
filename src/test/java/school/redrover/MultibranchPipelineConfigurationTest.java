package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.ErrorPage;
import school.redrover.page.HomePage;
import school.redrover.page.MultibranchPipelineJobPage;

public class MultibranchPipelineConfigurationTest extends BaseTest {

    private static final String JOB_NAME = "multibranchJobName";
    private static final String JOB_DESCRIPTION = "This is a job description";

    private void renameJob(String updatedJobName) {
        WebElement newNameField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName")));

        newNameField.clear();
        newNameField.sendKeys(updatedJobName);
    }

    private void submitForm() {
        getDriver().findElement(By.tagName("form")).submit();
    }

    private void openJobRenamePage(String jobName) {
        TestUtils.clickJS(getDriver(), By.xpath("//span[text()='%s']".formatted(jobName.trim())));

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href$='/confirm-rename']")))
                .click();
    }

    @Test
    public void testCreateMultibranchPipelineJob() {
        String actualHeadingText = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(JOB_NAME)
                .selectMultibranchPipelineWithJsAndSubmit()
                .clickSaveButton()
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
                .getToggleTooltipTextOnHover();

        Assert.assertEquals(actualTooltipText, expectedTooltipText);
    }

    @Test(dependsOnMethods = "testTooltipOnToggleHover")
    public void testDisabledMessageOnStatusPage() {
        final String expectedDisabledMessage = "This Multibranch Pipeline is currently disabled";

        String actualDisabledMessage = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineJobPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickToggle()
                .clickSaveButton()
                .getDisabledText();

        Assert.assertEquals(actualDisabledMessage, expectedDisabledMessage);
    }

    @Test(dependsOnMethods = "testDisabledMessageOnStatusPage")
    public void testJobDescriptionPreview() {
        String jobDescriptionPreviewText = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineJobPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .enterDescription(JOB_DESCRIPTION)
                .getJobDescriptionPreviewText();

        Assert.assertEquals(jobDescriptionPreviewText, JOB_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testJobDescriptionPreview")
    public void testMultibranchJobDescription() {
        String actualJobDescription = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineJobPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .enterDescription(JOB_DESCRIPTION)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualJobDescription, JOB_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testMultibranchJobDescription")
    public void testUpdateJobDescription() {
        final String updatedJobDescription = "This is a new project description";

        String actualJobDescription = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineJobPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .enterDescription(JOB_DESCRIPTION)
                .clickSaveButton()
                .clickConfigureLinkInSideMenu()
                .updateJobDescription(updatedJobDescription)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualJobDescription, updatedJobDescription);
    }

    @Test(dependsOnMethods = "testUpdateJobDescription")
    public void testRenameJobNameUsingDotAtTheEnd() {
        final String expectedErrorMessage = "A name cannot end with ‘.’";

        String actualErrorMessage = new HomePage(getDriver())
                .openJobPage(JOB_NAME, new MultibranchPipelineJobPage(getDriver()))
                .clickRenameLinkInSideMenu()
                .renameJob(JOB_NAME + ".")
                .submitForm(new ErrorPage(getDriver()))
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
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
