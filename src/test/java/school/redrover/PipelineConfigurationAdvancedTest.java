package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.ConfigurationPipelinePage;
import school.redrover.page.HomePage;
import school.redrover.page.PipelinePage;

import java.util.List;

public class PipelineConfigurationAdvancedTest extends BaseTest {

    private static final String PIPELINE_NAME = "newPipeline";

    private void createNewPipeline(String newPipelineName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(newPipelineName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void advancedButtonClick() {
        getDriver().findElement(By.xpath(".//button[@data-section-id='advanced']")).click();

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("footer"))));

        WebElement advancedButton = getWait10().until(ExpectedConditions.elementToBeClickable(By
                .xpath(".//div[@id='advanced']/parent::section/descendant::button[contains(text(),'Advanced')]")));
        new Actions(getDriver()).moveToElement(advancedButton).click().perform();
    }

    @Test
    public void testNavigationToAdvancedByScrollingDown() {
        String actualAdvancedSectionTitle = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PIPELINE_NAME)
                .selectPipelineAndSubmit()
                .scrollDownToAdvancedSection()
                .getAdvancedTitleText();

        Assert.assertEquals(actualAdvancedSectionTitle, "Advanced");
    }

    @Test(dependsOnMethods = "testNavigationToAdvancedByScrollingDown")
    public void testNavigationToAdvancedBySideMenu() {
        String actualAdvancedSectionTitle = new HomePage(getDriver())
                .openJobPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureInSideMenu(PIPELINE_NAME)
                .clickAdvancedLinkInSideMenu()
                .getAdvancedTitleText();

        Assert.assertEquals(actualAdvancedSectionTitle, "Advanced");
    }

    @Test(dependsOnMethods = "testNavigationToAdvancedBySideMenu")
    public void testAdvancedSectionQuietPeriodElements() {
        String actualQuietPeriodLabel = new HomePage(getDriver())
                .openJobPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureInSideMenu(PIPELINE_NAME)
                .clickAdvancedButton()
                .getQuietPeriodLabelText();

        Assert.assertEquals(actualQuietPeriodLabel, "Quiet period");
        Assert.assertFalse(new ConfigurationPipelinePage(getDriver())
                .quietPeriodCheckboxIsSelected(), "Default Checkbox should not be selected");
    }

    @Ignore
    @Test(dependsOnMethods = "testNavigationToAdvancedBySideMenu")
    public void testAdvancedSectionDisplayNameFieldElements() {
        String actualDisplayNameLabel = new HomePage(getDriver())
                .openJobPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureInSideMenu(PIPELINE_NAME)
                .clickAdvancedButton()
                .getDisplayNameLabelText();

        Assert.assertEquals(actualDisplayNameLabel, "Display Name");
        Assert.assertTrue(new ConfigurationPipelinePage(getDriver())
                .displayNameInput().getAttribute("value").isEmpty(),
                "Default Display Name field should be empty");
    }

    @Test(dependsOnMethods = "testAdvancedSectionQuietPeriodElements")
    public void testAdvancedSectionQuietPeriodElementsAfterSelecting() {
        String actualNumberOfSecondsLabel = new HomePage(getDriver())
                .openJobPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureInSideMenu(PIPELINE_NAME)
                .clickAdvancedButton()
                .clickQuitePeriod()
                .getNumberOfSecondsLabelText();

        Assert.assertEquals(actualNumberOfSecondsLabel, "Number of seconds");
        Assert.assertTrue(new ConfigurationPipelinePage(getDriver())
                .quietPeriodCheckboxIsSelected(), "Checkbox should be selected");
        Assert.assertTrue(new ConfigurationPipelinePage(getDriver())
                .getNumberOfSecondsInput().isDisplayed());
    }

    @Test(dependsOnMethods = "testAdvancedSectionQuietPeriodElementsAfterSelecting")
    public void testAdvancedSectionSetDisplayName() {
        final String displayName = "PL_01";

        String actualDisplayNameInStatus = new HomePage(getDriver())
                .openJobPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickConfigureInSideMenu(PIPELINE_NAME)
                .clickAdvancedButton()
                .setDisplayName(displayName)
                .clickSaveButton()
                .getDisplayNameInStatus();

        Assert.assertEquals(actualDisplayNameInStatus, displayName);
        Assert.assertEquals(new PipelinePage(getDriver()).
                getDisplayNameInBreadcrumbBar(displayName), displayName);

        List<String> actualProjectList = new PipelinePage(getDriver())
                .gotoHomePage()
                .getProjectList();
        Assert.assertTrue(actualProjectList.contains(displayName),
                String.format("Project with Display Name '%s' not found in Project List", displayName));
    }

    @Test
    public void testAdvancedSectionVerifyTooltips() {
        final String newPipelineName = "newPipeline_07";
        final List<String> expectedTooltipList = List.of(
                "Help for feature: Quiet period",
                "Help for feature: Display Name"
        );

        createNewPipeline(newPipelineName);
        advancedButtonClick();

        List<String> actualTooltipList = getDriver()
                .findElements(By.xpath(".//div[@id='advanced']/parent::section/descendant::a[@tooltip]"))
                .stream()
                .map(webElement -> webElement.getAttribute("title"))
                .toList();

        Assert.assertEquals(actualTooltipList, expectedTooltipList);
    }

    @Test(dependsOnMethods = {"testAdvancedSectionVerifyTooltips"})
    public void testAdvancedSectionHelpAreaIsDisplayed() {
        final String newPipelineName = "newPipeline_07";

        TestUtils.clickJS(getDriver(), By.xpath(".//td/a[@href='job/%s/']".formatted(newPipelineName)));
        getDriver().findElement(By
                .xpath(".//a[@href='/job/%s/configure']".formatted(newPipelineName))).click();
        advancedButtonClick();

        List<WebElement> actualTooltipList = getDriver().findElements(By
                .xpath(".//div[@id='advanced']/parent::section/descendant::a[@tooltip]"));
        for (WebElement webElement : actualTooltipList) {
            new Actions(getDriver())
                    .moveToElement(webElement)
                    .click()
                    .perform();

            Assert.assertTrue((getDriver().findElement(By
                    .xpath(".//div[@id='advanced']/parent::section/descendant::div[@class = 'help']")))
                    .isDisplayed());
        }
    }
}