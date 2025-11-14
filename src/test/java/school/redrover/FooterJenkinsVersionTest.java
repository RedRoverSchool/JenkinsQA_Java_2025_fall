package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BasePage;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.time.Duration;
import java.util.List;

public class FooterJenkinsVersionTest extends BaseTest {

    @Test
    public void testDropdownMenu() {
        final List<String> expectedDropdownList = List.of("About Jenkins", "Get involved", "Website");

        List<String> dropdownList = new HomePage(getDriver())
                .clickJenkinsVersion()
                .getDropdownList();

        Assert.assertEquals(dropdownList, expectedDropdownList);
    }
}
