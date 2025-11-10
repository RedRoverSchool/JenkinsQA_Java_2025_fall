package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateNewFolderTest extends BaseTest {

    @Test
    public void testCreateNewFolder() {
        String folderName = "Test Folder" + LocalDateTime.now().
                format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();

        WebElement inputNameFolder = getDriver().findElement(By.xpath("//*[@id='name']"));
        inputNameFolder.sendKeys(folderName);

        getDriver().findElement
                (By.xpath("//li[contains(@class, 'com_cloudbees_hudson_plugins_folder_Folder')]")).click();

        getDriver().findElement(By.id("ok-button")).click();

        WebElement generalHeader = getDriver().findElement(By.xpath("//span[text()='General']"));

        WebElement saveButton =
                getDriver().findElement(By.xpath("//button[contains(@class, 'jenkins-submit-button')]"));
        saveButton.click();

        WebElement folderTitleIn =
                getDriver().findElement(By.xpath("//h1[contains(@class,'job-index-headline page-headline')]"));

        WebElement logoJenkins =
                getDriver().findElement(By.xpath("//*[@id='jenkins-head-icon']"));
        logoJenkins.click();

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        List<WebElement> allFolders = getDriver().findElements(By.cssSelector(".jenkins-jobs-list__item__label"));
        boolean folderFound = false;
        for (WebElement folder : allFolders) {
            if (folder.getText().trim().equals(folderName)) {
                folderFound = true;
                break;
            }
        }
    }
}