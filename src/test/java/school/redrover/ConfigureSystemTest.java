package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.Arrays;
import java.util.Collections;
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

        String executorsLine = getDriver().findElement(By.className("executors-collapsed")).getText();
        List<String> lineElements = Arrays.stream(executorsLine.split(" ")).skip(1).toList();
        int frequency = frequency(lineElements, numberOfExecutors);

        Assert.assertEquals(frequency, 1);
    }

    @Test
    public void testUserOptionTooltipCheck() {

        getSystemConfigurePage();

        int numberOfHelpDisplayBlockBeforeClick = getDriver()
                .findElements(By.cssSelector("div .help[style='display: block;']")).size();

        getDriver().findElement(By.cssSelector("a[tooltip= 'Help for feature: Usage']")).click();

        int numberOfHelpDisplayBlockAfterClick = getDriver()
                .findElements(By.cssSelector("div .help[style='display: block;']")).size();

        Assert.assertEquals(
                numberOfHelpDisplayBlockBeforeClick,
                numberOfHelpDisplayBlockAfterClick);
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
        final String bagInterval = "61";
        final By intervalInputSelector = By.cssSelector("input[name = '_.computerRetentionCheckInterval']");

        getSystemConfigurePage();

        WebElement intervalInput = getDriver().findElement(intervalInputSelector);
        String oldValue = intervalInput.getAttribute("value");
        intervalInput.clear();
        intervalInput.sendKeys(bagInterval);
        getDriver().findElement(By.name("Submit")).click();

        getSystemConfigurePage();

        String actualInterval = getDriver().findElement(intervalInputSelector).getAttribute("value");
        Assert.assertEquals(actualInterval, oldValue);
    }

    @Test
    public void testHintOfComputerRetentionCheckInterval() {

        getSystemConfigurePage();

        WebElement intervalInput = getDriver().findElement(By.cssSelector("input[name = '_.computerRetentionCheckInterval']"));
        intervalInput.clear();
        intervalInput.sendKeys("61");
        intervalInput.sendKeys(Keys.TAB);

        WebElement hint = getDriver().findElement(
                By.xpath("//div[text()='This value should be between 1 and 60']/.."));
        Assert.assertTrue(hint.getAttribute("class").contains("--visible"));
    }



}
