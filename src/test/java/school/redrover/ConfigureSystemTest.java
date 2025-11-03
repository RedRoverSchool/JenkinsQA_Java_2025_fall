package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class ConfigureSystemTest extends BaseTest {

    @Test
    public void testUsageVariants() {
        final List<String> expectedVariants = List.of("Use this node as much as possible",
                "Only build jobs with label expressions matching this node");

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        Select select = new Select(getDriver().findElement(By.name("builtin.mode")));
        List<String> variants = select.getOptions()
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(variants, expectedVariants);

    }
}
