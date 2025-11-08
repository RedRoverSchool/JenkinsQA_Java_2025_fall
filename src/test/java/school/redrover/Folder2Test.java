package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.ArrayList;
import java.util.HashSet;
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
        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        getWait10().until(driver -> Objects.requireNonNull(driver.getCurrentUrl()).endsWith("/job/%s/".formatted(itemName)));
    }

    private List<String> getTextsOfItems(String xpathLocator) {
        List<String> itemsTexts = new ArrayList<>();
        for (WebElement element : getDriver().findElements(By.xpath(xpathLocator))) {
            itemsTexts.add(element.getText());
        }
        return itemsTexts;
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
        Assert.assertTrue(getTextsOfItems("//a[contains(@class, 'jenkins-table__link')]").isEmpty(),
                "Элементы должны отсутствовать в новой таблице");
    }

    @Test
    public void testNewFolderDefaultAddedToExistingFolder() {
        final String parentFolderName = "Folder" + UUID.randomUUID().toString().substring(0, 3);
        final String childFolderName = "Folder" + UUID.randomUUID().toString().substring(0, 3);

        createItem(parentFolderName, "Folder");
        createItem(childFolderName, "Folder");

        Assert.assertEquals(
                getTextsOfItems("//ol[@id='breadcrumbs']/li/a"),
                List.of(parentFolderName, childFolderName),
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
                {"Freestyle project", freestyleName},
                {"Pipeline", pipelineName},
                {"Multi-configuration project", multiConfigPrName},
                {"Folder", folderName},
                {"Multibranch Pipeline", multibrPipName},
                {"Organization Folder", orgFolderName}
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

        getWait10().until(driver -> Objects.requireNonNull(
                driver.getCurrentUrl()).endsWith("/job/%s/".formatted(itemName)));

        Assert.assertEquals(
                getTextsOfItems("//ol[@id='breadcrumbs']/li/a"),
                List.of(folderName, itemName),
                "Путь хлебных крошек не соответствует ожиданию");
    }

    @Test(invocationCount = 50)
    public void testPreventDuplicateItemNamesInFolder() {
        final String folderName = "Folder" + UUID.randomUUID().toString().substring(0, 3);
        final String pipelineName = "Pipeline" + UUID.randomUUID().toString().substring(0, 3);

        createItem(folderName, "Folder");
        createItem(pipelineName, "Pipeline");

        getDriver().findElement(By.xpath("//a[text()='%s']".formatted(folderName))).click();
        getWait10().until(driver -> Objects.requireNonNull(
                driver.getCurrentUrl()).endsWith("/job/%s/".formatted(folderName)));
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);

        WebElement duplicateMessage = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid")));
        Assert.assertEquals(
                duplicateMessage.getText(),
                "» A job already exists with the name ‘%s’".formatted(pipelineName),
                "Неверное сообщение о дублировании имени");
    }

    @Test
    public void testSameItemNamesInTwoFolders() {
        final String folder1Name = "Folder" + UUID.randomUUID().toString().substring(0, 3);
        final String folder2Name = "Folder" + UUID.randomUUID().toString().substring(0, 3);
        final String pipelineName = "Pipeline" + UUID.randomUUID().toString().substring(0, 3);

        createItem(folder1Name, "Folder");
        createItem(pipelineName, "Pipeline");
        getDriver().findElement(By.className("jenkins-mobile-hide")).click();
        createItem(folder2Name, "Folder");

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
        Assert.assertFalse(getDriver().findElement(By.id("itemname-invalid")).isDisplayed());

        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getWait10().until(driver -> Objects.requireNonNull(
                driver.getCurrentUrl()).endsWith("/job/%s/".formatted(pipelineName)));
        getDriver().findElement(By.className("jenkins-mobile-hide")).click();

        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folder1Name))).click();
        List<String> folder1Items = getTextsOfItems("//a[contains(@class, 'jenkins-table__link')]");
        getDriver().findElement(By.className("jenkins-mobile-hide")).click();
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(folder2Name))).click();
        List<String> folder2Items = getTextsOfItems("//a[contains(@class, 'jenkins-table__link')]");

        Assert.assertEquals(
                folder1Items,
                folder2Items,
                "Несоответствие содержимого папок");
    }

    @Test
    public void testFindFolderContents() {
        final String folderName = "Folder" + UUID.randomUUID().toString().substring(0, 3);
        final String pipelineName = "Pipeline" + UUID.randomUUID().toString().substring(0, 3);
        final String freestyleName = "Freestyle" + UUID.randomUUID().toString().substring(0, 3);

        createItem(folderName, "Folder");
        createItem(pipelineName, "Pipeline");
        getDriver().findElement(By.xpath("//a[text()='%s']".formatted(folderName))).click();
        createItem(freestyleName, "Freestyle project");
        getDriver().findElement(By.xpath("//a[text()='%s']".formatted(folderName))).click();

        Assert.assertEquals(
                new HashSet<>(getTextsOfItems("//a[contains(@class, 'jenkins-table__link')]")),
                new HashSet<>(List.of(freestyleName, pipelineName)),
                "Неверное отображение элементов");

        getDriver().findElement(By.className("jenkins-mobile-hide")).click();
        getDriver().findElement(By.id("root-action-SearchAction")).click();
        WebElement searchInput = getDriver().findElement(By.id("command-bar"));
        searchInput.sendKeys(pipelineName);
        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                "//a[contains(text(), 'Get help using Jenkins search')]")));
        Assert.assertTrue(getTextsOfItems("//div[@id='search-results']//a").
                        contains("%s » %s".formatted(folderName, pipelineName)),
                "Список результатов поиска не содержит нужный элемент");

        new Actions(getDriver())
                .moveToElement(searchInput, 0, -50)
                .click()
                .perform();
        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getDriver().findElement(By.id("command-bar")).sendKeys(freestyleName);
        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                "//div[contains(text(), 'Get help using Jenkins search')]")));
        Assert.assertTrue(getTextsOfItems("//div[@id='search-results']//a").
                        contains("%s » %s".formatted(folderName, freestyleName)),
                "Список результатов поиска не содержит нужный элемент");
    }
}
