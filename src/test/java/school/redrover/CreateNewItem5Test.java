package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CreateNewItem5Test extends BaseTest {

    @Test
    public void testCreateNewItem() throws InterruptedException {
        List<String> expectedTextsProjects =
                List.of("Freestyle project", "Pipeline", "Multi-configuration project",
                        "Folder", "Multibranch Pipeline", "Organization Folder");

        WebElement newItemButton = getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a"));
        newItemButton.click();

        Thread.sleep(2000);

        WebElement checkTextInput = getDriver().findElement(By.id("name"));

        Assert.assertTrue(checkTextInput.isDisplayed(), "The input field is not present on the page");

        List<WebElement> projectsNames = getDriver().findElements(By.cssSelector(".label"));
        List<String> actualProjectNames = projectsNames.stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualProjectNames, (expectedTextsProjects));
    }
}