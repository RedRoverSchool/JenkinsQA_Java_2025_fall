package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;

public class ConfigureSystemTest extends BaseTest {

    private final String SYSTEM_MESSAGE = "Hello redRover School!";

    @Test
    public void testCreateSystemMessage() {

        new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickConfigurationSystem()
                .clearSystemMessage()
                .setSystemMessage(SYSTEM_MESSAGE)
                .clickSave();

        String actualSystemMessage = new HomePage(getDriver())
                .gotoHomePage()
                .getSystemMessage();

        Assert.assertEquals(
                actualSystemMessage,
                SYSTEM_MESSAGE);
    }

    @Test(dependsOnMethods = "testCreateSystemMessage")
    public void testSystemMessagePreview() {

        final String addToPreviewMessage = " This is the best project!";

        String actualPreviewMessage = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickConfigurationSystem()
                .setSystemMessage(addToPreviewMessage)
                .getPreviewSystemMessage();

        Assert.assertEquals(SYSTEM_MESSAGE + addToPreviewMessage,  actualPreviewMessage);
    }

    @Test(dependsOnMethods = "testSystemMessagePreview")
    public void testChangeSystemMessage() {

        final String addToSystemMessage = "!";

        new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickConfigurationSystem()
                .setSystemMessage(addToSystemMessage)
                .clickSave();

        String actualSystemMessage = new HomePage(getDriver())
                .gotoHomePage()
                .getSystemMessage();

        Assert.assertEquals(
                actualSystemMessage, SYSTEM_MESSAGE + addToSystemMessage);
    }

    private void moveToSystem() {
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();
    }

    @Test
    public void testUsageVariants() {
        final List<String> expectedVariants = List.of("Use this node as much as possible",
                "Only build jobs with label expressions matching this node");

        moveToSystem();

        Select select = new Select(getDriver().findElement(By.name("builtin.mode")));
        List<String> variants = select.getOptions()
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(variants, expectedVariants);

    }

    @Test
    @Ignore
    public void testIntervalDefaultValue() {
        final String defaultValue = "60";
        moveToSystem();

        WebElement input = getDriver().findElement(By.name("_.computerRetentionCheckInterval"));

        Assert.assertEquals(input.getAttribute("value"), defaultValue);
    }
}
