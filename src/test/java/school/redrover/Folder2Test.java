package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Folder2Test extends BaseTest {

    private void createItem(String itemName, String itemType) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        WebElement selectedItemType = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(itemType)));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", selectedItemType);
        selectedItemType.click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(driver -> Objects.requireNonNull(
                driver.getCurrentUrl()).endsWith("/job/%s/".formatted(itemName)));
    }

    @Test
    public void testCreateFolder() {
        final String folderName = "Folder" + UUID.randomUUID().toString().substring(0, 3);
        createItem(folderName, "Folder");

        Assert.assertEquals(
                getDriver().findElement(By.tagName("h1")).getText(),
                folderName,
                "Неверное название папки");
        Assert.assertTrue(
                getDriver().findElement(By.className("empty-state-section")).getText().contains("This folder is empty"),
                "Отсутствует сообщение 'This folder is empty'");
        List<WebElement> itemsInFolder = getDriver().findElements(By.xpath("//*[@id='projectstatus']/tbody/tr"));
        Assert.assertTrue(itemsInFolder.isEmpty(), "Элементы должны отсутствовать в новой таблице");
    }

    @Test
    public void testNewFolderDefaultAddedToExistingFolder() {
        final String parentFolderName = "Folder" + UUID.randomUUID().toString().substring(0, 3);
        final String childFolderName = "Folder" + UUID.randomUUID().toString().substring(0, 3);

        createItem(parentFolderName, "Folder");
        createItem(childFolderName, "Folder");

        List<String> breadcrumbTexts = new ArrayList<>();
        for (WebElement element : getDriver().findElements(By.xpath("//ol[@id='breadcrumbs']/li/a"))) {
            breadcrumbTexts.add(element.getText());
        }

        List<String> folderNames = new ArrayList<>();
        folderNames.add(parentFolderName);
        folderNames.add(childFolderName);

        Assert.assertEquals(
                breadcrumbTexts,
                folderNames,
                "Путь хлебных крошек не соответствует ожиданию");
    }

    @DataProvider(name = "itemsProvider")
    public Object[][] itemsProvider() {
        String freestyleName = "Freestyle" + UUID.randomUUID().toString().substring(0, 3);
        String pipelineName = "Pipeline" + UUID.randomUUID().toString().substring(0, 3);
        String multiConfigPrName = "MultiConfigPr" + UUID.randomUUID().toString().substring(0, 3);
        String folderName = "Folder" + UUID.randomUUID().toString().substring(0, 3);
        String multibrPipName = "MultibrPip" + UUID.randomUUID().toString().substring(0, 3);
        String orgFolderName = "OrgFolder" + UUID.randomUUID().toString().substring(0, 3);

        return new Object[][]{
                { "Freestyle project", freestyleName },
                { "Pipeline", pipelineName },
                { "Multi-configuration project", multiConfigPrName },
                { "Folder", folderName },
                { "Multibranch Pipeline", multibrPipName },
                { "Organization Folder", orgFolderName }
        };
    }

    @Test(dataProvider = "itemsProvider")
    public void testPutItemToFolder(String itemType, String itemName) {
        final String folderName = "Folder" + UUID.randomUUID().toString().substring(0, 3);

        createItem(folderName, "Folder");
        getDriver().findElement(By.className("jenkins-mobile-hide")).click();
        createItem(itemName, itemType);

        getDriver().findElement(By.xpath("//a[contains(@href, 'move')]")).click();
        Select selectObject = new Select(getDriver().findElement(By.className("jenkins-select__input")));
        selectObject.selectByVisibleText("Jenkins » %s".formatted(folderName));
        getDriver().findElement(By.name("Submit")).click();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(driver -> Objects.requireNonNull(
                driver.getCurrentUrl()).endsWith("/job/%s/".formatted(itemName)));
        List<String> breadcrumbTexts = new ArrayList<>();
        for (WebElement element : getDriver().findElements(By.xpath("//*[@id='breadcrumbs']//a"))) {
            breadcrumbTexts.add(element.getText());
        }

        List<String> itemsNames = new ArrayList<>();
        itemsNames.add(folderName);
        itemsNames.add(itemName);
        Assert.assertEquals(
                breadcrumbTexts,
                itemsNames,
                "Путь хлебных крошек не соответствует ожиданию");
    }
}
