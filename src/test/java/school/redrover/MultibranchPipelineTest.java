package school.redrover;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
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


    @DataProvider
    public Object[][] providerSpecialCharacters() {
        return new String[][]{
                {"!"}, {"&"}, {"#"}, {"@"}, {"%"},{"*"},
                {"$"},{"?"},{"^"},{"|"},{"/"},{"]"},{"["}
        };
    }

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
                .openProject(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .sendDescription(MULTIBRANCH_JOB_DESCRIPTION)
                .getJobDescriptionPreviewText();

        Assert.assertEquals(jobDescriptionPreviewText, MULTIBRANCH_JOB_DESCRIPTION);
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testUpdateJobDescription() {
        final String updatedJobDescription = "This is a new project description";

        String actualJobDescription = new HomePage(getDriver())
                .openProject(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .sendDescription(MULTIBRANCH_JOB_DESCRIPTION)
                .clickSaveButton()
                .clickConfigureLinkInSideMenu()
                .sendDescription(updatedJobDescription)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualJobDescription, updatedJobDescription);
    }

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
                .openProject(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
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

    @Test(dataProvider = "providerSpecialCharacters")
    public void testCreateItemWithSpecialCharacters(String specialCharacters) {
        String actualErrorMessage = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .clearSendName()
                .sendName("multib" + specialCharacters + "ranch")
                .getDuplicateOrUnsafeCharacterErrorMessage();

        Assert.assertEquals(actualErrorMessage,
                "» ‘%s’ is an unsafe character".formatted(specialCharacters),
                "Error message isn't displayed");
    }

    @Test
    public void testAddDescriptionLinkIsEnabled() {
      boolean isAddDescriptionLinkEnabled = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .clickSaveButton()
                .isAddDescriptionLinkEnabled();

      Assert.assertTrue(isAddDescriptionLinkEnabled);
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testDescriptionField() {
        String descriptionFieldText = new HomePage(getDriver())
                .openProject(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
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
                .getHeadingText();

        Assert.assertEquals(actualRenamedMultibranchPipeline, RENAMED_MULTIBRANCH_PIPELINE);
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipeline")
    public void testRenameJobNameUsingDotAtTheEnd() {
        final String expectedErrorMessage = "A name cannot end with ‘.’";

        String actualErrorMessage = new HomePage(getDriver())
                .openProject(MULTIBRANCH_PIPELINE_NAME, new MultibranchPipelineProjectPage(getDriver()))
                .clickRenameLinkInSideMenu()
                .renameJob(MULTIBRANCH_PIPELINE_NAME + ".")
                .submitForm()
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
