package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Folder2Test extends BaseTest {

    private static final String MAIN_FOLDER_NAME = "MainFolder";

    private void createItem(String itemName, String itemType) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        WebElement selectedItemType = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(itemType)));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", selectedItemType);
        selectedItemType.click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        getWait5().until(driver -> Objects.requireNonNull(driver.getCurrentUrl()).endsWith("/job/%s/".formatted(itemName)));
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
        createItem(MAIN_FOLDER_NAME, "Folder");

        Assert.assertEquals(
                getDriver().findElement(By.tagName("h1")).getText(),
                MAIN_FOLDER_NAME,
                "Неверное название папки");
        Assert.assertTrue(
                getDriver().findElement(By.className("empty-state-section")).getText().contains("This folder is empty"),
                "Отсутствует сообщение 'This folder is empty'");
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
        };}

    @Test(dependsOnMethods = {"testCreateFolder"})
    public void testPutItemToFolder() {
        final String subFolderName = "SubFolder";
        final String freestyleProjectName = "SubFreestyleProject";
        final String pipelineName = "SubPipeline";
        final String multiconfigurationProjectName = "SubMulticonfigurationProject";
        final String multibranchPipelineName = "SubMultibranchPipeline";
        final String organizationFolderName = "SubOrganizationFolder";

        final Object[][] items = {
                {subFolderName, "Folder"},
                {freestyleProjectName, "Freestyle project"},
                {pipelineName, "Pipeline"},
                {multiconfigurationProjectName, "Multi-configuration project"},
                {multibranchPipelineName, "Multibranch Pipeline"},
                {organizationFolderName, "Organization Folder"}};

        for (Object[] item : items) {
            String itemName = (String) item[0];
            String itemType = (String) item[1];
            createItem(itemName, itemType);

            getDriver().findElement(By.xpath("//a[contains(@href, 'move')]")).click();
            Select selectObject = new Select(getDriver().findElement(By.className("jenkins-select__input")));
            selectObject.selectByVisibleText("Jenkins » %s".formatted(MAIN_FOLDER_NAME));
            getDriver().findElement(By.name("Submit")).click();

            getWait5().until(driver -> Objects.requireNonNull(
                    driver.getCurrentUrl()).endsWith("/job/%s/".formatted(itemName)));

            List<String> breadcrumbTexts = getTextsOfItems("//ol[@id='breadcrumbs']/li/a");
            Assert.assertFalse(breadcrumbTexts.isEmpty(), "Хлебные крошки не должны быть пусты");
            Assert.assertEquals(
                    breadcrumbTexts,
                    List.of(MAIN_FOLDER_NAME, itemName),
                    "Путь хлебных крошек не соответствует ожиданию");

            getDriver().findElement(By.className("jenkins-mobile-hide")).click();
        }
    }

    @Test
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
        WebElement selectedItemType = getDriver().findElement(By.xpath("//span[text()='Pipeline']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", selectedItemType);
        selectedItemType.click();

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

    @Test(dataProvider = "itemsProvider")
    public void testFolderIsIdentifiedByIcon(String itemType, String itemName) {
        final String folderName = "Folder" + UUID.randomUUID().toString().substring(0, 3);
        final String dAttributeOfFolderIcon = "M440 432H72a40 40 0 01-40-40V120a40 40 0 0140-40h75.89a40 40 0 0122.19 6.72";

        createItem(folderName, "Folder");
        getDriver().findElement(By.className("jenkins-mobile-hide")).click();
        createItem(itemName, itemType);
        getDriver().findElement(By.className("jenkins-mobile-hide")).click();

        String xpathForItemNameByIconAttribute = "//tr[.//*[contains(@d,'%s')]]//a//span".formatted(dAttributeOfFolderIcon);
        List<String> expectedItems = itemType.equals("Folder")
                ? List.of(folderName, itemName)
                : List.of(folderName);
        Assert.assertEquals(
                new HashSet<>(getTextsOfItems(xpathForItemNameByIconAttribute)),
                new HashSet<>(expectedItems),
                "Ошибка в отображении иконок");
    }

    @Test(dataProvider = "itemsProvider")
    public void testFolderIsIdentifiedByTooltip(String itemType, String itemName) {
        final String folderName = "Folder" + UUID.randomUUID().toString().substring(0, 3);
        Actions actions = new Actions(getDriver());

        createItem(folderName, "Folder");
        getDriver().findElement(By.className("jenkins-mobile-hide")).click();
        createItem(itemName, itemType);
        getDriver().findElement(By.className("jenkins-mobile-hide")).click();

        List<String> tooltipTexts = new ArrayList<>();
        for (WebElement statusIcon : getDriver().findElements(By.xpath("//tr[contains(@class, 'job')]/td[1]//*[@tooltip]"))) {
            actions
                    .moveToElement(statusIcon)
                    .perform();
            String tooltipIDByAttribute = getDriver().findElement(By.xpath("//*[@aria-describedby]"))
                    .getAttribute("aria-describedby");
            getWait10().until(ExpectedConditions.presenceOfElementLocated(By.id(Objects.requireNonNull(tooltipIDByAttribute))));
            tooltipTexts.add(getDriver().findElement(By.xpath("//*[@id='%s']/div/div".formatted(tooltipIDByAttribute))).getText());
        }

        Assert.assertEquals(tooltipTexts.size(), 2);
        if (itemType.equals("Folder")) {
            Assert.assertEquals(
                    tooltipTexts.get(0),
                    tooltipTexts.get(1),
                    "Тултипы у Folder не должны отличаться");
        } else {
            Assert.assertNotEquals(
                    tooltipTexts.get(0),
                    tooltipTexts.get(1),
                    "Тултипы других элементов должны отличаться от тултипа Folder");
        }
    }
}
