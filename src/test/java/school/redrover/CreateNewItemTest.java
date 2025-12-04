package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import school.redrover.common.BaseTest;
import school.redrover.page.FreestyleProjectPage;
import school.redrover.page.HomePage;
import school.redrover.page.NewItemPage;

import java.time.Duration;
import java.util.List;

public class CreateNewItemTest extends BaseTest {

    private static final String PROJECT_NAME = "New Project";

    @Test
    public void testNewItemPageByClickingCreateAJobLink() {
        String result = new HomePage(getDriver())
                .clickCreateJob()
                .getHeadingText();

        Assert.assertEquals(result, "New Item");
    }

    @Test
    public void testNewItemPageByClickingNewItemLink() {
        String result = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .getHeadingText();

        Assert.assertEquals(result, "New Item");
    }

    @Test
    public void testEnterAnItemNameIsDisplayedOkButtonIdDisabled() {
          Boolean result = new HomePage(getDriver())
                  .clickCreateJob()
                  .isOkButtonEnabled();

          Assert.assertFalse(result);
    }

    // === Добавлено из CreateNewItem4Test ===
    @Test
    public void testItemNameInput() {
        new HomePage(getDriver()).clickCreateJob();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);

        List<WebElement> validationMessages = getDriver().findElements(By.className("input-validation-message"));

        boolean allValidationMessagesDisabled = validationMessages.stream()
                .allMatch(msg -> msg.getAttribute("class").contains("input-message-disabled"));

        Assert.assertTrue(allValidationMessagesDisabled,
                "All validation messages should be disabled for valid input");
    }

    @Test
    public void testInvalidItemNameField() {

        final String invalidChars = " !@#$%^&*[]|\\;:<>?/";

        NewItemPage newItemPage = new HomePage(getDriver()).clickCreateJob();

        for (char ch : invalidChars.toCharArray()) {
            newItemPage
                    .clearSendName()
                    .sendName(String.valueOf(ch));

            String dataValid = newItemPage.getNameDataValid();

            Assert.assertEquals(dataValid, "false", "Character '" + ch + "' should not be allowed");
        }
    }

    @Test
    public void testPipelineTypeCanBeSelected() {
        boolean isSelected = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectPipeline()
                .isPipelineSelected();

        Assert.assertTrue(isSelected);
    }

    @Test
    public void testPipelineTypeHighlightAndOkButtonEnables() {
        NewItemPage newItemPage = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectPipeline();

        Assert.assertTrue(newItemPage.isPipelineHighlighted());
        Assert.assertTrue(newItemPage.isOkButtonEnabled());
    }

    @Test
    public void testErrorMessageForDuplicateItemNames() {
        String errorMessage = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .gotoHomePage()
                .clickNewItemOnLeftMenu()
                .sendName(PROJECT_NAME)
                .selectFolder()
                .getDuplicateOrUnsafeCharacterErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘New Project’");
    }

    @Test
    public void testAcceptsAlphanumericAndUnderscores() {

        List<String> projectList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectPipelineAndSubmit()
                .gotoHomePage()
                .getProjectList();

        Assert.assertNotEquals(projectList.size(), 0);
        Assert.assertEquals(projectList.get(0), PROJECT_NAME);
    }

    // === Добавлено из удаленного CreateNewItem1Test ===
    @Test
    public void testConfigurationPageIsVisible() {
        new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit();

        getWait2().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//span[text()='Configuration']"), "Configuration"));

        String heading = getDriver().findElement(By.xpath("//span[text()='Configuration']")).getText();
        Assert.assertEquals(heading, "Configuration");
    }
    @Test
    public void testBuildStepsFilterNames() {
        new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit();

        WebElement addBuildStep = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Add build')]"))
        );

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", addBuildStep);
        addBuildStep.click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[normalize-space()='Execute Windows batch command']")
        ));

        String[] testSteps = {"Execute Windows batch command", "Execute shell", "Invoke Ant"};

        for (String step : testSteps) {
            WebElement filter = getDriver().findElement(By.xpath("//input[@type='search' and @placeholder='Filter']"));
            filter.clear();
            filter.sendKeys(step.substring(0, Math.min(5, step.length())));

            WebElement actualStep = new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//button[contains(@style,'inline-flex') and normalize-space()='%s']".formatted(step))
                    ));

            Assert.assertEquals(actualStep.getText(), step, "Filter didn't match expected build step");
        }
    }

    // === Добавлено из CreateNewItem7Test ===
    @Test
    public void createNewFreeStyleProjectTest() {
        new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickSave();

        getWait2().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h1"), PROJECT_NAME));

        WebElement projectTitle = getDriver().findElement(By.xpath("//h1"));
        Assert.assertEquals(projectTitle.getText(), PROJECT_NAME);
    }

    // === Добавлено из CreateNewItem8Test ===
    @Test
    public void createMultibranchPipelineProjectItemTest() {
        final String jobName = "NEW_TEST_JOB";

        String newProject = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(jobName)
                .selectMultibranchPipelineAndSubmit()
                .gotoHomePage()
                .getProjectList()
                .get(0);

        Assert.assertEquals(jobName, newProject);
    }

    @Test
    public void testNewItemByCopyingFromExistingItem() {
        final String originalName = "originalProject";
        final String newName = "newProject";
        final String descriptionText = "RedRover is the best";
        final String daysToKeepText = "10";
        final String maxOfBuildsText = "20";
        final String gitHubUrl = "git.com/testUrl";
        final String buildsUrl = "example.com/test";

        List <String> originalItemSettingsList =
                List.of(descriptionText, daysToKeepText, maxOfBuildsText, gitHubUrl, buildsUrl);

        List<String> copiedItemSettingsList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(originalName)
                .selectFreestyleProjectAndSubmit()
                .setDescription(descriptionText)
                .setCheckBoxDiscardAndSetDaysNum(daysToKeepText, maxOfBuildsText)
                .setCheckBoxGitHubAndSendUrl(gitHubUrl)
                .setCheckBoxTriggerBuildsAndSendUrl(buildsUrl)
                .clickSave()
                .gotoHomePage()
                .clickNewItemOnLeftMenu()
                .sendName(newName)
                .sendNameToCopyFromAndSubmit(originalName)
                .gotoHomePage()
                .openProject(newName, new FreestyleProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .getSettingsToList();

        Assert.assertEquals(originalItemSettingsList, copiedItemSettingsList);
    }
    @Test
    public void testErrorDisplayedForEmptyItemName(){
        String errorMessage = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .clickOkButton()
                .getErrorDisplayedForEmptyItemName();

        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");


    }
}

