package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
    public void testTryCreateProjectExistName() throws InterruptedException {
        final String errorMessage = "» A job already exists with the name " + "‘" + MULTIBRANCH_PIPELINE_NAME + "’";

        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);

        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//a[@href='/']/img")).click();

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();

        Thread.sleep(2000);
        String actualMessage = getDriver().findElement(By.id("itemname-invalid")).getText();
        Assert.assertEquals(actualMessage, errorMessage);
    }

    @Test
    public void testVerifyStatusToSwitchingEnableMultibranchPipeline() throws InterruptedException {
        final String disableText = "This Multibranch Pipeline is currently disabled";

        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);
        getDriver().findElement(By.xpath("//a[@href='/job/" + MULTIBRANCH_PIPELINE_NAME + "/configure']")).click();
        getDriver().findElement(By.cssSelector("#toggle-switch-enable-disable-project > label")).click();
        getDriver().findElement(By.name("Submit")).click();

        Thread.sleep(2000);
        String actualDisableText = getDriver().findElement(By.id("disabled-message")).getText();
        Assert.assertTrue(actualDisableText.contains(disableText));
    }

    @Ignore
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
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']"))
                .sendKeys("Multibranch Pipeline (test)");
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/Multibranch%20Pipeline%20(test)/']")).click();

        WebElement buttonAddDescription = getDriver().findElement(By.id("description-link"));

        Assert.assertTrue(buttonAddDescription.isDisplayed());
    }

    @Test
    public void testClickAddDescriptionButton() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']"))
                .sendKeys("Multibranch Pipeline (test)");
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/Multibranch%20Pipeline%20(test)/']")).click();

        WebElement buttonAddDescription = getDriver().findElement(By.id("description-link"));
        buttonAddDescription.click();

        WebElement descriptionField = getDriver().findElement(By.xpath("//textarea[@name='description']"));

        Assert.assertTrue(descriptionField.isDisplayed());
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

    @Ignore
    @Test
    public void testRenameViaSidebar() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));

        createMultibranchPipeline(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.cssSelector("[href$='confirm-rename']")).click();

        WebElement renameField = getDriver().findElement(By.name("newName"));
        renameField.clear();
        renameField.sendKeys(RENAMED_MULTIBRANCH_PIPELINE);

        getDriver().findElement(By.name("Submit")).click();

        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("confirm-rename")));

        WebElement multibranchPipelineName = getDriver().findElement(By.tagName("h1"));

        Assert.assertEquals(multibranchPipelineName.getText(), RENAMED_MULTIBRANCH_PIPELINE);
    }
}
