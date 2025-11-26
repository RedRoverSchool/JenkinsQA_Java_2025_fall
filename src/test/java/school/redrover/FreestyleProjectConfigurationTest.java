package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.FreestyleProjectConfigurationPage;
import school.redrover.page.FreestyleProjectPage;
import school.redrover.page.HomePage;

import java.time.Duration;
import java.util.List;

public class FreestyleProjectConfigurationTest extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";
    private static final String PROJECT_DESCRIPTION_EXPECTED = "This is a description for Freestyle Project";
    private static final String SCM_TITLE_EXPECTED = "Source Code Management";

    private static final List<String> BUILD_STEPS = List.of(
            "Execute Windows batch command",
            "Execute shell",
            "Invoke Ant",
            "Invoke Gradle script",
            "Invoke top-level Maven targets",
            "Run with timeout",
            "Set build status to \"pending\" on GitHub commit"
    );

    @Test
    public void testAddDescription() {
        String actualDescriptionText = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .setDescription(PROJECT_DESCRIPTION_EXPECTED)
                .clickSave()
                .getDescription();
        Assert.assertEquals(actualDescriptionText, PROJECT_DESCRIPTION_EXPECTED);
    }

    @Test
    public void testDisableProjectViaConfigureDropdownMenu() {
        createFreestyleProject(PROJECT_NAME);

        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jenkins-header__navigation a"))).click();
        new Actions(getDriver()).moveToElement(getDriver()
                .findElement(By.xpath("//td/a[@href='job/%s/']".formatted(PROJECT_NAME)))).perform();
        moveAndClickWithJS(getWait5().until(ExpectedConditions.
                visibilityOfElementLocated(By.cssSelector(".jenkins-menu-dropdown-chevron"))));
        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//a[contains(@href,'configure')]"))).click();
        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#toggle-switch-enable-disable-project"))).click();
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        String disableProjectMessage = getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(".warning"))).getText().split("\\R")[0];
        Assert.assertEquals(disableProjectMessage, "This project is currently disabled");
    }

    @Test
    public void testBuildStepsFilterNames() {

        FreestyleProjectConfigurationPage configPage = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickBuildStepMenuOption();

        for (String buildStep : BUILD_STEPS) {

            configPage
                    .typeIntoFilterBuildStep(buildStep.substring(0, Math.min(5, buildStep.length())));

            WebElement visibleStep = configPage.verifySentNameIsInFilter(buildStep);

            Assert.assertEquals(
                    visibleStep.getText(),
                    buildStep,
                    "Filter didn't match expected build step");
        }
    }
    @Test
    public void testEnableProjectViaMainMenuConfigure() {
        createFreestyleProject(PROJECT_NAME);

        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#toggle-switch-enable-disable-project"))).click();
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.className("app-jenkins-logo"))).click();

        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//td/a[@href='job/%s/']".formatted(PROJECT_NAME)))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='/job/%s/configure']".formatted(PROJECT_NAME)))).click();

        getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#toggle-switch-enable-disable-project"))).click();
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.className("app-jenkins-logo"))).click();

        WebElement buildButtonWhenProjectEnabled = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//a[@title='Schedule a Build for %s'])[1]".formatted(PROJECT_NAME))));
        Assert.assertTrue(buildButtonWhenProjectEnabled.isDisplayed());
    }

    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
    }

    private void moveAndClickWithJS(WebElement element) {
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", element);
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new Event('click'));", element);
    }

    @Test
    public void testBuildSteps() {

        new HomePage(getDriver())
                .clickCreateJob()
                .sendName("TestProject")
                .selectFreestyleProjectAndSubmit()
                .clickBuildStepMenuOption();

        for (String step : BUILD_STEPS) {
            WebElement buildStep = getDriver().findElement(By.xpath("//button[contains(text(),'%s')]".formatted(step)));
            Assert.assertEquals(buildStep.getText(), step);
        }
    }

    @Test
    public void testAccessSCMInNewJob() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testSCMSectionElements() {
        FreestyleProjectConfigurationPage configPage = new HomePage(getDriver()).clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit();

        Assert.assertTrue(configPage.getScmDescription().isDisplayed(),"SCM Description is not displayed or the description text doesn't match");
        Assert.assertEquals(configPage.getSelectedRadioLabel(), "None","Radio button 'None' should be selected by default");
        Assert.assertTrue(configPage.isGitOptionDisplayed(),"Radio button 'Git' should be displayed");
        Assert.assertEquals(configPage.getGitTooltipText(),"Help for feature: Git","Tooltip text should match expected value");
    }

    @Test
    public void testAccessSCMInExistingJob() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .gotoHomePage()
                .openPage(PROJECT_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigureLinkInSideMenu()
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToSCMViaMenu() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickSourceCodeManagementMenuOption()
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToSCMByScrollingDown() {
        WebElement scmTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .scrollToSourceCodeManagementWithJS()
                .verifySCMTitleIsVisible();

        Assert.assertEquals(scmTitle.getText(), SCM_TITLE_EXPECTED);
    }

    @Test
    public void testNavigationToTriggersBySideMenu () {
        String triggerTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickTriggerLinkInSideMenu()
                .getTriggerTitleText();

        Assert.assertEquals(triggerTitle, "Triggers");
    }

    @Test
    public void testSaveButtonIsVisibleAndClickable() {

        WebElement saveButton = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .getSaveButton();

        Assert.assertTrue(saveButton.isDisplayed());
    }
}
