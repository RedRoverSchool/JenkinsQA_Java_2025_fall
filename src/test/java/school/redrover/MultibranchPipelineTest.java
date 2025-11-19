package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.NewItemPage;

import java.util.List;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchName";
    private static final String RENAMED_MULTIBRANCH_PIPELINE = "RenamedMultibranchName";

    @Test
    public void testAddingDescriptionCreatingMultibranch() {
        final String expectedDescription = "AddedDescription";

        String actualDescription = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .enterDescription(expectedDescription)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, expectedDescription, actualDescription + " and " + expectedDescription + " don't match");
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

    @Test (dependsOnMethods = "testTryCreateProjectExistName")
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
    public void testVerifyEnableToogleTooltip() {
        final String tooltipText =
                "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        String actualTooltip = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndSubmit()
                .getToggleTooltipTextOnHover();

        Assert.assertEquals(actualTooltip, tooltipText);
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
    public void testAddDescription() {
        final String expectedDescription = "This is a test of the possibility of adding a description";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']"))
                .sendKeys("Multibranch Pipeline (test)");
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        WebElement descriptionField = getDriver().findElement(By.xpath("//textarea[@name='_.description']"));
        descriptionField.sendKeys(expectedDescription);
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        WebElement actualDescription = getDriver().findElement(By.xpath("//div[@id='view-message']"));
        Assert.assertEquals(actualDescription.getText(), expectedDescription);
    }

    @Test
    public void testButtonIsDisplayed() {
        final String projectName = "Multibranch Pipeline (test)";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//a[text()='%s']".formatted(projectName))).click();
        WebElement buttonAddDescription = getDriver().findElement(By.id("description-link"));

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

    @Test
    public void testEnterTheDescription() {
        final String description = "This is a test description for Multibranch Pipeline";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']"))
                .sendKeys("Multibranch Pipeline (test)");
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/Multibranch%20Pipeline%20(test)/']")).click();

        WebElement buttonAddDescription = getDriver().findElement(By.id("description-link"));
        buttonAddDescription.click();

        WebElement descriptionField = getDriver().findElement(By.xpath("//textarea[@name='description']"));
        descriptionField.sendKeys(description);

        Assert.assertTrue(descriptionField.isDisplayed());
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

    @Test
    public void testEnterTheDescriptionOfTheMultibranchPipeline() {
        final String projectName = "Multibranch Pipeline (test)";
        final String description = "This is a test description for Multibranch Pipeline";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//a[text()='%s']".formatted(projectName))).click();
        getDriver().findElement(By.id("description-link")).click();
        WebElement descriptionField = getDriver().findElement(By.xpath("//textarea[@name='description']"));
        descriptionField.sendKeys(description);

        Assert.assertTrue(descriptionField.isDisplayed());
    }

    @Test
    public void testSeeTheDescriptionPreview() {
        final String projectName = "Multibranch Pipeline (test)";
        final String description = "This is a test description for Multibranch Pipeline";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//a[text()='%s']".formatted(projectName))).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);

        getDriver().findElement(By.xpath("//a[@class='textarea-show-preview']")).click();
        WebElement textPreview = getDriver().findElement(By.xpath("//div[text()='%s']".formatted(description)));

        Assert.assertTrue(textPreview.isDisplayed());
    }

    @Ignore
    @Test
    public void testSeeAddedDescriptionBelowHeading() {
        final String projectName = "Multibranch Pipeline (test)";
        final String description = "This is a test description for Multibranch Pipeline";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//a[text()='%s']".formatted(projectName))).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();
        WebElement savedDescription = getDriver().findElement(By.id("description-content"));

        Assert.assertFalse(savedDescription.isDisplayed(),
                "Bug: description is not saved below the Multibranch Pipeline heading");
    }
}
