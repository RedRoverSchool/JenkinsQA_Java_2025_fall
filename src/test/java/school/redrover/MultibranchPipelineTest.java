package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.ErrorPage;
import school.redrover.page.HomePage;
import school.redrover.page.MultibranchPipelineProjectPage;
import school.redrover.page.NewItemPage;

import java.util.List;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchName";
    private static final String RENAMED_MULTIBRANCH_PIPELINE = "RenamedMultibranchName";
    private static final String MULTIBRANCH_JOB_DESCRIPTION = "This is a job description";
    private static final String SECOND_DESCRIPTION = "Second Description";
    private static final String MULTIBRANCH_PIPELINE_DISPLAY_NAME = "Multibranch_Pipeline_Display";

    @Test
    public void testCreateMultibranchPipeline() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .clickSaveButton()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertTrue(projectList.contains(MULTIBRANCH_PIPELINE_NAME));
    }

    @Test
    public void testAddingDescriptionCreatingMultibranch() {
        final String expectedDescription = "AddedDescription";

        String actualDescription = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .sendDescription(expectedDescription)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, expectedDescription, actualDescription + " and " + expectedDescription + " don't match");
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testJobDescriptionPreview() {
        String jobDescriptionPreviewText = new HomePage(getDriver())
                .openPage(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .sendDescription(MULTIBRANCH_JOB_DESCRIPTION)
                .getJobDescriptionPreviewText();

        Assert.assertEquals(jobDescriptionPreviewText, MULTIBRANCH_JOB_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testUpdateJobDescription() {
        final String updatedJobDescription = "This is a new project description";

        String actualJobDescription = new HomePage(getDriver())
                .openPage(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .sendDescription(MULTIBRANCH_JOB_DESCRIPTION)
                .clickSaveButton()
                .clickConfigureLinkInSideMenu()
                .sendDescription(updatedJobDescription)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualJobDescription, updatedJobDescription);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testTryCreateProjectExistName() {
        final String errorMessage = "» A job already exists with the name ‘%s’".formatted(MULTIBRANCH_PIPELINE_NAME);

        String dublicateProject = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .selectMultibranchPipeline()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .getDuplicateOrUnsafeCharacterErrorMessage();

        Assert.assertEquals(dublicateProject, errorMessage, "Incorrect error message");
    }

    @Ignore
    @Test(dependsOnMethods = "testTryCreateProjectExistName")
    public void testDeleteMultibranchPipeline() {

        List<String> projectList = new HomePage(getDriver())
                .openDropdownMenu(MULTIBRANCH_PIPELINE_NAME)
                .clickDeleteItemInDropdownMenu()
                .confirmDelete()
                .gotoHomePage()
                .getProjectList();

        Assert.assertEquals(projectList.size(), 0);
    }

    @Test
    public void testVerifyDisableMessaageOnStatusPage() {
        final String disableText = "This Multibranch Pipeline is currently disabled";

        String actualDisableText = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .clickToggle()
                .clickSaveButton()
                .getDisabledText();

        Assert.assertTrue(actualDisableText.contains(disableText));
    }

    @Test
    public void testVerifyEnableToggleTooltip() {
        final String tooltipText =
                "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        String actualTooltip = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .getToggleTooltipTextOnHover();

        Assert.assertEquals(actualTooltip, tooltipText);
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testDisableToggle() {
        final String expectedToggleState = "Disabled";

        String actualToggleState = new HomePage(getDriver())
                .openPage(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .clickToggle()
                .getToggleState();

        Assert.assertEquals(actualToggleState, expectedToggleState);
    }

    @Test
    public void testVerifyAppearSaveMessage() {
        String actualSavedMessage = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .clickToggle()
                .clickApply()
                .getSavedMessage();

        Assert.assertEquals(actualSavedMessage, "Saved", "Message isn't correct");
    }

    @Test
    public void testCreateItemWithSpecialCharacters() {
        final String[] specialCharacters = {"!", "&", "#", "@", "%", "*", "$", "?", "^", "|", "/", "]", "["};

        NewItemPage newItemPage = new HomePage(getDriver())
                .clickNewItemOnLeftMenu();

        for (String specChar : specialCharacters) {
            String expectedErrorMessage = "» ‘%s’ is an unsafe character".formatted(specChar);

            String actualErrorMessage = newItemPage
                    .clearSendName()
                    .sendName("multib" + specChar + "ranch")
                    .getDuplicateOrUnsafeCharacterErrorMessage();

            Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message isn't displayed");
        }
    }

    @Test
    public void testButtonIsDisplayed() {
        WebElement buttonAddDescription = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .clickSaveButton()
                .getAddDescriptionLink();

        Assert.assertTrue(buttonAddDescription.isDisplayed());
    }

    @Test
    public void testClickAddDescriptionButton() {
        final String projectName = "Multibranch Pipeline (test)";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//a[text()='%s']".formatted(projectName))).click();
        getDriver().findElement(By.id("description-link")).click();
        WebElement descriptionField = getDriver().findElement(By.xpath("//textarea[@name='description']"));

        Assert.assertTrue(descriptionField.isEnabled());
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testDescriptionFieldText() {
        String descriptionFieldText = new HomePage(getDriver())
                .openPage(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickAddDescriptionLink()
                .sendDescription(MULTIBRANCH_JOB_DESCRIPTION)
                .getDescriptionFieldText();

        Assert.assertEquals(descriptionFieldText, MULTIBRANCH_JOB_DESCRIPTION);
    }

    @Test
    public void testRenameViaSidebar() {
        String actualRenamedMultibranchPipeline = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .clickSaveButton()
                .clickRenameLinkInSideMenu()
                .renameMultibranchPipeline(RENAMED_MULTIBRANCH_PIPELINE)
                .getHeading();

        Assert.assertEquals(actualRenamedMultibranchPipeline, RENAMED_MULTIBRANCH_PIPELINE);
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testRenameJobNameUsingDotAtTheEnd() {
        final String expectedErrorMessage = "A name cannot end with ‘.’";

        String actualErrorMessage = new HomePage(getDriver())
                .openPage(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickRenameLinkInSideMenu()
                .renameJob(MULTIBRANCH_PIPELINE_NAME + ".")
                .submitForm(new ErrorPage(getDriver()))
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void testDisplayNameIsSetOnCreation() {
        String name = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .sendDisplayName(MULTIBRANCH_PIPELINE_DISPLAY_NAME)
                .clickSaveButton()
                .gotoHomePage()
                .findItem(MULTIBRANCH_PIPELINE_NAME)
                .getText();

        Assert.assertEquals(name, MULTIBRANCH_PIPELINE_DISPLAY_NAME);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testChangeDescription() {

        String name = new HomePage(getDriver())
                .clickDescription()
                .sendDescriptionText(MULTIBRANCH_JOB_DESCRIPTION)
                .submitDescription()
                .clickDescription()
                .clearTextDescription()
                .sendDescriptionText(SECOND_DESCRIPTION)
                .submitDescription()
                .getDescription();

        Assert.assertEquals(name, SECOND_DESCRIPTION);
    }
}
