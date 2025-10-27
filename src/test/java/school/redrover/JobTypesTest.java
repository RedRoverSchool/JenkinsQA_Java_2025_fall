package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;
import java.util.List;

public class JobTypesTest extends BaseTest {

    @Test
    public void testJobTypesAreDisplayed() {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='jenkins-form-label']")));

        List<String> expectedItemType = List.of(
                "Pipeline",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder"
        );

        List<String> actualItemTypes = getDriver().findElements(By.xpath("//div[@id='items']//label"))
                .stream()
                .map(WebElement::getText)
                .filter(expectedItemType::contains)
                .toList();

        Assert.assertEquals(actualItemTypes, expectedItemType);
    }
}

