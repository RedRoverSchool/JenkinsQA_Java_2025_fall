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

public class NewItemPageTest extends BaseTest {
    private final By newItemButtonFromHomePage = By.xpath(".//div[@id=\"tasks\"]/div[1]/span/a");

    public void newItemButtonClick() {
        WebElement newItemButton = new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(newItemButtonFromHomePage));

        Assert.assertEquals(newItemButton.getText(), "New Item");
        getDriver().findElement(newItemButtonFromHomePage).click();
    }

    @Test
    public void testNewItemPageAccessibility() {
        newItemButtonClick();

        Assert.assertEquals(
                getDriver().findElement(By.xpath(".//h1")).getText(), "New Item");

        WebElement itemNameLabel = getDriver().findElement(By.xpath(".//label[@class=\"jenkins-form-label\"]"));
        Assert.assertEquals(itemNameLabel.getText(), "Enter an item name");

        WebElement itemNameField = getDriver().findElement(By.xpath(".//*[@id=\"name\"]"));
        Assert.assertTrue(itemNameField.isEnabled(), "Поле \"Enter an item name\" должно быть доступно для ввода");

        WebElement itemTypeLabel = getDriver().findElement(By.xpath(".//div[@class=\"jenkins-form-label\"]"));
        Assert.assertEquals(itemTypeLabel.getText(), "Select an item type");
    }

    @Test
    public void testNewItemTypesAccessibility() {
        newItemButtonClick();

        List<String> expectedItemTypes = List.of("Freestyle project", "Pipeline", "Multi-configuration project", "Folder", "Multibranch Pipeline", "Organization Folder");
        List<WebElement> itemTypes = getDriver().findElements(By.xpath(".//span[@class=\"label\"]"));

        List<String> itemTypeList = itemTypes.stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(itemTypeList, expectedItemTypes);
    }
}
