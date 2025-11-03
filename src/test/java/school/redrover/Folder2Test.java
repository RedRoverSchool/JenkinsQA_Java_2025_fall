package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Folder2Test extends BaseTest {

    private void createItem(String itemName, String item) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        String itemClassName = switch (item) {
            case "Folder" -> "com_cloudbees_hudson_plugins_folder_Folder";
            case "Pipeline" -> "org_jenkinsci_plugins_workflow_job_WorkflowJob";
            default -> "";
        };
        getDriver().findElement(By.className(itemClassName)).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(driver -> Objects.requireNonNull(
                driver.getCurrentUrl()).endsWith("/job/%s/".formatted(itemName)));
    }

    @Test
    public void testCreateFolder() {
        final String folderName = "Folder" + UUID.randomUUID().toString().substring(0, 5);
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
        final String parentFolderName = "Folder" + UUID.randomUUID().toString().substring(0, 5);
        final String childFolderName = "Folder" + UUID.randomUUID().toString().substring(0, 5);

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

    @Test
    public void testPutPipelineToFolder() {
        final String folderName = "Folder" + UUID.randomUUID().toString().substring(0, 5);
        final String pipelineName = "Pipeline" + UUID.randomUUID().toString().substring(0, 5);

        createItem(folderName, "Folder");
        getDriver().findElement(By.className("jenkins-mobile-hide")).click();
        createItem(pipelineName, "Pipeline");

        getDriver().findElement(By.className("jenkins-mobile-hide")).click();

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(
                By.xpath("//a[.//span[contains(text(), '%s')]]".formatted(pipelineName)))).perform();
        getDriver().findElement(By.xpath(("//button[contains(@class, 'jenkins-menu-dropdown-chevron')" +
                " and contains(@data-href, '%s')]").formatted(pipelineName))).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement moveLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'move')]")));
        moveLink.click();
        Select selectObject = new Select(getDriver().findElement(By.className("jenkins-select__input")));
        selectObject.selectByVisibleText("Jenkins » %s".formatted(folderName));
        getDriver().findElement(By.name("Submit")).click();

        List<String> breadcrumbTexts = new ArrayList<>();
        for (WebElement element : getDriver().findElements(By.xpath("//ol[@id='breadcrumbs']/li/a"))) {
            breadcrumbTexts.add(element.getText());
        }

        List<String> itemsNames = new ArrayList<>();
        itemsNames.add(folderName);
        itemsNames.add(pipelineName);
        Assert.assertEquals(
                breadcrumbTexts,
                itemsNames,
                "Путь хлебных крошек не соответствует ожиданию");
    }
}
