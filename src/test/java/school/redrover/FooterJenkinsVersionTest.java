package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;

public class
FooterJenkinsVersionTest extends BaseTest {

    @Test
    public void testDropdownMenu() {
        final List<String> expectedDropdownItems = List.of("About Jenkins", "Get involved", "Website");

        getDriver().findElement(By.cssSelector(".page-footer__links > button")).click();

        Wait<WebDriver> wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        List<WebElement> dropdownElements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("jenkins-dropdown__item")));

        List<String> dropdownItems = dropdownElements
                .stream()
                .map(x -> x.getText())
                .toList();

        Assert.assertEquals(dropdownItems, expectedDropdownItems);
    }
}
