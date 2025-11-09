package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchName";
    private static final String RENAMED_MULTIBRANCH_PIPELINE = "RenamedMultibranchName";

    private void createMultibranchPipeline(String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testAddingDescriptionCreatingMultibranch() {
        final String expectedDescription = "AddedDescription";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("MultibranchPipeline");
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("_.description")).sendKeys(expectedDescription);
        getDriver().findElement(By.name("Submit")).click();

        String actualDescription = getDriver().findElement(By.id("view-message")).getText();

        Assert.assertEquals(actualDescription, expectedDescription, actualDescription + " and " + expectedDescription + " don't match");
    }

    @Test
    public void testTryCreateProjectExistName() {
        final String errorMessage = "» A job already exists with the name " + "‘" + MULTIBRANCH_PIPELINE_NAME + "’";

        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions
                .elementToBeClickable(By.cssSelector("span.jenkins-mobile-hide"))).click();

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();

        String actualMessage = getWait2().until(ExpectedConditions
                .visibilityOfElementLocated(By.id("itemname-invalid"))).getText();
        Assert.assertEquals(actualMessage, errorMessage);
    }

    @Test
    public void testVerifyDisableMessaageOnStatusPage() {
        final String disableText = "This Multibranch Pipeline is currently disabled";

        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//a[@href='/job/%s/configure']".formatted(MULTIBRANCH_PIPELINE_NAME))).click();
        getDriver().findElement(By.cssSelector("#toggle-switch-enable-disable-project > label")).click();
        getDriver().findElement(By.name("Submit")).click();

        String actualDisableText = getWait2().until(ExpectedConditions
                .visibilityOfElementLocated(By.id("disabled-message"))).getText();
        Assert.assertTrue(actualDisableText.contains(disableText));
    }

    @Test
    public void testVerifyEnableToogleTooltip() {
        final String tooltipText =
                "(No new builds within this Multibranch Pipeline will be executed until it is re-enabled)";

        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//a[@href='/job/%s/configure']".formatted(MULTIBRANCH_PIPELINE_NAME))).click();
        WebElement toggleTooltip = getDriver().findElement(By.id("toggle-switch-enable-disable-project"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(toggleTooltip).perform();

        String actualTooltip = getWait2().until(ExpectedConditions
                .visibilityOfElementLocated(By.className("tippy-content"))).getText();

        Assert.assertEquals(actualTooltip, tooltipText);
    }

    @Test
    public void testVerifyAppearSaveMessage() {
        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//a[@href='/job/%s/configure']".formatted(MULTIBRANCH_PIPELINE_NAME))).click();
        getDriver().findElement(By.cssSelector("#toggle-switch-enable-disable-project > label")).click();
        getDriver().findElement(By.name("Apply")).click();

        String actualSavedMessage = getWait2().until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath("//span[text() = 'Saved']"))).getText();

        Assert.assertEquals(actualSavedMessage, "Saved");
    }

    @Test
    public void testCreateItemWithSpecialCharacters() {
        final String[] specialCharacters = {"!", "%", "&", "#", "@", "*", "$", "?", "^", "|", "/", "]", "["};

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        WebElement nameField = getDriver().findElement(By.id("name"));

        for (String specChar: specialCharacters){
            String errorMessage = "» ‘" + specChar + "’ is an unsafe character";

            nameField.clear();
            nameField.sendKeys("multi" + specChar + "branch");

            String actualMessage = getWait5().until(ExpectedConditions.
                    visibilityOfElementLocated(By.id("itemname-invalid"))).getText();

            Assert.assertEquals(actualMessage, errorMessage, "Error message isn't displayed");
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
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.cssSelector("[href$='confirm-rename']")).click();

        WebElement renameField = getDriver().findElement(By.name("newName"));
        renameField.clear();
        renameField.sendKeys(RENAMED_MULTIBRANCH_PIPELINE + Keys.ENTER);

        getWait10().until(ExpectedConditions.not(ExpectedConditions.urlContains("confirm-rename")));

        WebElement multibranchPipelineName = getDriver().findElement(By.tagName("h1"));

        Assert.assertEquals(multibranchPipelineName.getText(), RENAMED_MULTIBRANCH_PIPELINE);
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
