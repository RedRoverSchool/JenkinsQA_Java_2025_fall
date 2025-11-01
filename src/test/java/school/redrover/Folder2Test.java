package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

    private void createFolder(String folderName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(driver -> Objects.requireNonNull(
                driver.getCurrentUrl()).endsWith("/job/%s/".formatted(folderName)));
    }

    @Test
    public void testCreateFolder() {
        final String folderName = "Folder" + UUID.randomUUID().toString().substring(0, 5);
        createFolder(folderName);

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

        createFolder(parentFolderName);
        createFolder(childFolderName);

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
}
