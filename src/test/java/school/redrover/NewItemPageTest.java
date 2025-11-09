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
    private final By newItemButtonFromHomePage = By.xpath(".//div[@id='tasks']/div[1]/span/a");

    private void newItemButtonClick() {
        WebElement newItemButton = new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(newItemButtonFromHomePage));

        newItemButton.click();
    }

    @Test
    public void testNewItemPageAccessibility() {
        newItemButtonClick();

        Assert.assertEquals(
                getDriver().findElement(By.xpath(".//h1")).getText(), "New Item");

        WebElement itemNameLabel = getDriver().findElement(By.xpath(".//label[@class='jenkins-form-label']"));
        Assert.assertEquals(itemNameLabel.getText(), "Enter an item name");

        WebElement itemNameField = getDriver().findElement(By.xpath(".//*[@id='name']"));
        Assert.assertTrue(itemNameField.isEnabled(), "Поле 'Enter an item name' должно быть доступно для ввода");

        WebElement itemTypeLabel = getDriver().findElement(By.xpath(".//div[@class='jenkins-form-label']"));
        Assert.assertEquals(itemTypeLabel.getText(), "Select an item type");
    }

    @Test
    public void testNewItemSelectAnItemType() {

        String itemName = "TestItem";
        String checkselect;

        getDriver().findElement(By.cssSelector("#tasks > :nth-child(1)")).click();
        getDriver().findElement((By.cssSelector(".add-item-name > #name"))).sendKeys(itemName);

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        checkselect = getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).getAttribute("aria-checked");
        Assert.assertEquals(checkselect, "true");
        getDriver().findElement(By.id("ok-button")).isDisplayed();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#side-panel > div.jenkins-app-bar > div.jenkins-app-bar__content > h1")).getText(), "Configure");
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".jenkins-toggle-switch__label__checked-title")).getText(), "Enabled");

        getDriver().findElement(By.cssSelector(".app-jenkins-logo")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("#projectstatus > :nth-child(2) > :nth-child(1) > :nth-child(3) > a > span")).getText(), itemName);

    }

    @Test
    public void testSelectItemTypeIsVisible() {
        final List<String> expectedItemTypes = List.of(
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder"
        );

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        WebElement sectionTitle = getDriver().findElement(By.xpath("//div[text()='Select an item type']"));
        List<WebElement> actualItemTypes = getDriver().findElements(By.xpath("//div[@id='items']//label"));
        Assert.assertNotEquals(actualItemTypes.size(), 0);
        for (int i = 0; i < expectedItemTypes.size(); i++) {
            Assert.assertTrue(actualItemTypes.get(i).isDisplayed());
        }
        Assert.assertTrue(sectionTitle.isDisplayed());
    }
}
