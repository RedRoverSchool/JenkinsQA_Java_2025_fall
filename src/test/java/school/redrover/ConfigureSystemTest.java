package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.frequency;

public class ConfigureSystemTest extends BaseTest {

    private void getSystemConfigurePage() {
        getDriver().findElement(By.cssSelector("a[href$='manage']")).click();
        getDriver().findElement(By.cssSelector("a[href$='configure']")).click();
    }

    @Test
    public void testCreateSystemMessage() {

        final String systemMessage = "Hello redRover School!";

        getSystemConfigurePage();

        WebElement systemMessageTextArea = getDriver().findElement(By.name("system_message"));
        systemMessageTextArea.clear();
        systemMessageTextArea.sendKeys(systemMessage);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("systemmessage")).getText(),
                systemMessage);
    }

    @Test
    public void testChangeSystemMessage() {

        final String firstPartSystemMessage = "Hello";
        final String secondPartSystemMessage = " redRover School!";

        getSystemConfigurePage();

        WebElement systemMessageTextArea = getDriver().findElement(By.name("system_message"));
        systemMessageTextArea.clear();
        systemMessageTextArea.sendKeys(firstPartSystemMessage);
        getDriver().findElement(By.name("Submit")).click();

        getSystemConfigurePage();

        getDriver().findElement(By.name("system_message")).sendKeys(secondPartSystemMessage);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("systemmessage")).getText(),
                firstPartSystemMessage + secondPartSystemMessage);
    }

    @Test
    public void testSystemMessagePreview() {

        final String systemMessage = "Hello redRover School!";

        getSystemConfigurePage();

        WebElement systemMessageTextArea = getDriver().findElement(By.name("system_message"));
        systemMessageTextArea.clear();
        systemMessageTextArea.sendKeys(systemMessage);

        getDriver().findElement(By.cssSelector("a[previewendpoint]")).click();

        Assert.assertEquals(
                getDriver().findElement(By.className("textarea-preview")).getText(),
                systemMessage);
    }

    @Test
    public void testChangeNumberOfExecutors() {

        final String numberOfExecutors = "12";

        getSystemConfigurePage();

        WebElement systemMessageTextArea = getDriver().findElement(By.name("_.numExecutors"));
        systemMessageTextArea.clear();
        systemMessageTextArea.sendKeys(numberOfExecutors);
        getDriver().findElement(By.name("Submit")).click();

        WebElement executors = getDriver().findElement(By.cssSelector("div#executors"));

        String executorsLine;
        if (executors.getAttribute("class").contains("expanded")) {
            executorsLine = getDriver().findElement(By.cssSelector("span[tooltip*='executors busy']")).getAttribute("tooltip");
        } else {
            executorsLine = getDriver().findElement(By.className("executors-collapsed")).getText();
        }

        List<String> lineElements = Arrays.stream(executorsLine.split(" ")).skip(1).toList();
        int frequency = frequency(lineElements, numberOfExecutors);

        Assert.assertEquals(frequency, 1);
    }

    @Test
    public void testTooltipOfUsageOption() {

        getSystemConfigurePage();

        int numberOfHelpDisplayBlockBeforeClick = getDriver()
                .findElements(By.cssSelector("div .help[style='display: block;']")).size();

        getDriver().findElement(By.cssSelector("a[tooltip= 'Help for feature: Usage']")).click();

        int numberOfHelpDisplayBlockAfterClick = getDriver()
                .findElements(By.cssSelector("div .help[style='display: block;']")).size();

        Assert.assertEquals(
                numberOfHelpDisplayBlockBeforeClick,
                numberOfHelpDisplayBlockAfterClick - 1);
    }

    @Test
    public void testUserOptionSelectCheck() {

        final List<String> expectedUsageOptions = List.of("Use this node as much as possible",
                "Only build jobs with label expressions matching this node");

        getSystemConfigurePage();

        Select usageOptionSelect = new Select(getDriver().findElement(By.cssSelector("select[name = 'builtin.mode']")));
        List<String> actualUsageOptions = usageOptionSelect.getOptions().stream()
                .map(WebElement::getText).toList();

        Assert.assertEquals(actualUsageOptions, expectedUsageOptions);
        Assert.assertEquals(
                usageOptionSelect.getFirstSelectedOption().getText(),
                expectedUsageOptions.get(0));
    }

    @Test
    public void testChangeComputerRetentionCheckIntervalPositive() {
        final String testInterval = "59";
        final By intervalInputSelector = By.cssSelector("input[name = '_.computerRetentionCheckInterval']");

        getSystemConfigurePage();

        WebElement intervalInput = getDriver().findElement(intervalInputSelector);
        intervalInput.clear();
        intervalInput.sendKeys(testInterval);
        getDriver().findElement(By.name("Submit")).click();

        getSystemConfigurePage();

        String actualInterval = getDriver().findElement(intervalInputSelector).getAttribute("value");
        Assert.assertEquals(actualInterval, testInterval);
    }

    @Test
    public void testChangeComputerRetentionCheckIntervalNegative() {
        final String incorrectInterval = "61";
        final By intervalInputSelector = By.cssSelector("input[name = '_.computerRetentionCheckInterval']");

        getSystemConfigurePage();

        WebElement intervalInput = getDriver().findElement(intervalInputSelector);
        String oldValue = intervalInput.getAttribute("value");
        intervalInput.clear();
        intervalInput.sendKeys(incorrectInterval);
        getDriver().findElement(By.name("Submit")).click();

        getSystemConfigurePage();

        String actualInterval = getDriver().findElement(intervalInputSelector).getAttribute("value");
        Assert.assertEquals(actualInterval, oldValue);
    }

    @Test
    public void testHintOfComputerRetentionCheckInterval() {
        final String incorrectInterval = "61";

        getSystemConfigurePage();

        WebElement intervalInput = getDriver().findElement(By.cssSelector("input[name = '_.computerRetentionCheckInterval']"));
        intervalInput.clear();
        intervalInput.sendKeys(incorrectInterval);
        intervalInput.sendKeys(Keys.TAB);

        WebElement hint = getDriver().findElement(
                By.xpath("//div[text()='This value should be between 1 and 60']/.."));
        Assert.assertTrue(hint.getAttribute("class").contains("--visible"));
    }

    @Test
    public void testTooltipOfQuietPeriod() {

        getSystemConfigurePage();

        int numberOfHelpDisplayBlockBeforeClick = getDriver()
                .findElements(By.cssSelector("div .help[style='display: block;']")).size();

        WebElement tooltipOfQuietPeriod = getDriver().findElement(By.cssSelector("a[tooltip= 'Help for feature: Quiet period']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", tooltipOfQuietPeriod);
        tooltipOfQuietPeriod.click();

        int numberOfHelpDisplayBlockAfterClick = getDriver()
                .findElements(By.cssSelector("div .help[style='display: block;']")).size();

        Assert.assertEquals(
                numberOfHelpDisplayBlockBeforeClick,
                numberOfHelpDisplayBlockAfterClick - 1);
    }


    @Test
    public void testHintOfQuietPeriod() {
        final String incorrectQuietPeriod = "-2";

        getSystemConfigurePage();

        WebElement intervalInput = getDriver().findElement(By.cssSelector("input[name = '_.quietPeriod']"));
        intervalInput.clear();
        intervalInput.sendKeys(incorrectQuietPeriod);
        intervalInput.sendKeys(Keys.TAB);

        WebElement hint = getDriver().findElement(
                By.xpath("//div[text()='This value should be larger than 0']/.."));
        Assert.assertTrue(hint.getAttribute("class").contains("--visible"));
    }

    @Test
    public void testChangeQuietPeriodPositive() {
        final String testQuietPeriod = "10";
        final By quietPeriodInputSelector = By.cssSelector("input[name = '_.quietPeriod']");

        getSystemConfigurePage();

        WebElement quietPeriodInput = getDriver().findElement(quietPeriodInputSelector);
        quietPeriodInput.clear();
        quietPeriodInput.sendKeys(testQuietPeriod);
        getDriver().findElement(By.name("Submit")).click();

        getSystemConfigurePage();

        String actualQuietPeriod = getDriver().findElement(quietPeriodInputSelector).getAttribute("value");
        Assert.assertEquals(actualQuietPeriod, testQuietPeriod);
    }

    @Test
    public void testGlobalProperties() {
        final List<String> expectedGlobalProperties = List.of(
                "Disable deferred wipeout on this node",
                "Disk Space Monitoring Thresholds",
                "Environment variables",
                "Tool Locations");

        getSystemConfigurePage();

        List<String> actualGlobalProperties = getDriver().findElements(By.xpath("//div[@id='global-properties']/..//label"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertEquals(expectedGlobalProperties, actualGlobalProperties);
    }

    @Test
    public void testTooltipOfDisableDeferredWipeoutOnThisNode() {

        getSystemConfigurePage();

        int numberOfHelpDisplayBlockBeforeClick = getDriver()
                .findElements(By.cssSelector("div .help[style='display: block;']")).size();

        WebElement disableDeferredWipeoutOnThisNode = getDriver()
                .findElement(By.cssSelector("a[tooltip= 'Help for feature: Disable deferred wipeout on this node']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", disableDeferredWipeoutOnThisNode);
        disableDeferredWipeoutOnThisNode.click();

        int numberOfHelpDisplayBlockAfterClick = getDriver()
                .findElements(By.cssSelector("div .help[style='display: block;']")).size();

        Assert.assertEquals(
                numberOfHelpDisplayBlockBeforeClick,
                numberOfHelpDisplayBlockAfterClick - 1);
    }

    @Test
    public void testDiskSpaceMonitoringThresholds() {
        String testSpace = "1GiB";

        getSystemConfigurePage();

        getDriver().findElement(By.xpath("//input[contains(@name, 'DiskSpaceMonitorNodeProperty')]")).click();

        WebElement freeDiskSpaceThreshold = getDriver().findElement(By.name("_.freeDiskSpaceThreshold"));
        freeDiskSpaceThreshold.clear();
        freeDiskSpaceThreshold.sendKeys(testSpace);

        WebElement freeDiskSpaceWarningThreshold = getDriver().findElement(By.name("_.freeDiskSpaceThreshold"));
        freeDiskSpaceWarningThreshold.clear();
        freeDiskSpaceWarningThreshold.sendKeys(testSpace);

        getSystemConfigurePage();

        Assert.assertEquals(expectedGlobalProperties, actualGlobalProperties);
    }





}
