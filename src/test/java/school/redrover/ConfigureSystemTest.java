package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.ConfigurationSystemPage;
import school.redrover.page.HomePage;

import java.util.ArrayList;
import java.util.Iterator;
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

    @Test
    public void testChangeNumberOfExecutors() {

        final String numberOfExecutors = "5";

        new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickConfigurationSystem()
                .setNumberOfExecutors(numberOfExecutors)
                .clickSave();

        String actualNumberOfExecutors = new HomePage(getDriver())
                .gotoHomePage()
                .getNumberOfExecutors();

        Assert.assertEquals(actualNumberOfExecutors, numberOfExecutors);
    }

    @Test(dataProvider = "tooltips")
    public void testTooltips(String tooltipName) {

        Integer actualNumberOfTooltip = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickConfigurationSystem()
                .clickTooltip(tooltipName)
                .getNumberOfOpenTooltips();

        Assert.assertEquals(actualNumberOfTooltip, 1);
    }

    @DataProvider(name = "tooltips")
    private Iterator<Object[]> getTooltipNameList() {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{"Home directory"});
        data.add(new Object[]{"Usage"});
        data.add(new Object[]{"Computer Retention Check Interval"});
        data.add(new Object[]{"Quiet period"});
        data.add(new Object[]{"Jenkins URL"});
        data.add(new Object[]{"System Admin e-mail address"});
        data.add(new Object[]{"Resource Root URL"});
        data.add(new Object[]{"Disable deferred wipeout on this node"});
        return data.iterator();
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
