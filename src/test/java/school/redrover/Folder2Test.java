package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Folder2Test extends BaseTest {

    @Test
    public void testCreateFolder() {
        final String folderName = "Folder-" + UUID.randomUUID().toString().substring(0, 5);

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertTrue(Objects.requireNonNull(getDriver().getCurrentUrl()).contains("/job/%s".formatted(folderName)),
                "Ошибка в ссылке на папку");
        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                folderName,
                "Неверное название папки");
        Assert.assertTrue(
                getDriver().findElement(By.className("empty-state-section")).getText().contains("This folder is empty"),
                "Отсутствует сообщение 'This folder is empty'");
        List<WebElement> itemsInFolder = getDriver().findElements(By.xpath("//*[@id='projectstatus']/tbody/tr"));
        Assert.assertTrue(itemsInFolder.isEmpty(), "Элементы должны отсутствовать в новой таблице");
    }
}
