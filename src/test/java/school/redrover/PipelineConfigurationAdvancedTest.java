package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import school.redrover.page.HomePage;

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
                .ScrollDownToAdvancedSection()
                .getAdvancedTitleText();

        Assert.assertEquals(actualAdvancedSectionTitle, "Advanced");
    }

    @Test
    public void testNavigationToAdvancedBySideMenu() {
        final String newPipelineName = "newPipeline_02";
        createNewPipeline(newPipelineName);

        WebElement actualAdvancedItemMenu = getDriver().findElement(By.xpath(".//button[@data-section-id='advanced']"));
        actualAdvancedItemMenu.click();

        WebElement actualAdvancedSectionTitle = getDriver().findElement(By.id("advanced"));

        Assert.assertEquals(actualAdvancedItemMenu.getText(), "Advanced");
        Assert.assertEquals(actualAdvancedSectionTitle.getText(), "Advanced");
    }

    @Test
    public void testAdvancedSectionQuietPeriodElements() {
        final String newPipelineName = "newPipeline_03";
        createNewPipeline(newPipelineName);
        advancedButtonClick();

        WebElement actualQuietPeriodLabel = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.
                xpath(".//label[text()='Quiet period']")));
        new Actions(getDriver()).moveToElement(actualQuietPeriodLabel).perform();

        WebElement actualQuietPeriodCheckbox = getDriver().findElement(By.id("cb13"));

        Assert.assertEquals(actualQuietPeriodLabel.getText(), "Quiet period");
        Assert.assertFalse(actualQuietPeriodCheckbox.isSelected(), "Default Checkbox should not be selected");
    }

    @Test
    public void testAdvancedSectionDisplayNameFieldElements() {
        final String newPipelineName = "newPipeline_04";
        createNewPipeline(newPipelineName);
        advancedButtonClick();

        WebElement displayNameLabel = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.
                xpath(".//div[text()='Display Name']")));
        new Actions(getDriver()).moveToElement(displayNameLabel).perform();

        String actualDisplayNameLabel = displayNameLabel.getText().split("\\n")[0];
        WebElement actualDisplayNameInput = getDriver().findElement(By.name("_.displayNameOrNull"));

        Assert.assertEquals(actualDisplayNameLabel, "Display Name");
        Assert.assertTrue(actualDisplayNameInput.getAttribute("value").isEmpty(), "Default Display Name field should be empty");
    }

    @Test
    public void testAdvancedSectionQuietPeriodElementsAfterSelecting() {
        final String newPipelineName = "newPipeline_05";
        createNewPipeline(newPipelineName);
        advancedButtonClick();

        WebElement actualQuietPeriodLabel = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//label[text()='Quiet period']")));
        new Actions(getDriver()).moveToElement(actualQuietPeriodLabel).click().perform();

        WebElement actualQuietPeriodCheckbox = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .name("hasCustomQuietPeriod")));
        WebElement actualNumberOfSecondsLabel = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//div[text()='Number of seconds']")));
        WebElement actualNumberOfSecondsInput = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .name("quiet_period")));

        Assert.assertTrue(actualQuietPeriodCheckbox.isSelected(), "Checkbox should be selected");
        Assert.assertEquals(actualNumberOfSecondsLabel.getText(), "Number of seconds");
        Assert.assertTrue(actualNumberOfSecondsInput.isDisplayed(), "'Number of seconds' input should be displayed");
    }

    @Test
    public void testAdvancedSectionAddDisplayName() {
        final String pipelineName = "pipeline_01";
        final String displayName = "PL_01";
        createNewPipeline(pipelineName);
        advancedButtonClick();

        WebElement displayNameInput = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .name("_.displayNameOrNull")));
        new Actions(getDriver()).moveToElement(displayNameInput).perform();
        displayNameInput.sendKeys(displayName);

        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        String actualDisplayNameInStatus = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .tagName("h1"))).getText();
        String actualDisplayNameInBreadcrumbBar = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath(".//a[text()='%s']".formatted(displayName)))).getText();

        Assert.assertEquals(actualDisplayNameInStatus, displayName);
        Assert.assertEquals(actualDisplayNameInBreadcrumbBar, displayName);

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        String actualDisplayNameInHomePage = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By
                .id("job_%s".formatted(pipelineName)))).getText().split("\\n")[0];

        Assert.assertEquals(actualDisplayNameInHomePage, displayName);
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