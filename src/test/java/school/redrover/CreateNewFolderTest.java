package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

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
        List<WebElement> trElements = getDriver().findElements(
                By.xpath("//tr[@id]"));
        List<String> jobNames = new ArrayList<>();
        for (WebElement tr : trElements) {
            String id = tr.getAttribute("id");
            String name = id.replaceFirst("^job_", "");
            jobNames.add(name);
        }
        assertTrue(jobNames.contains(folderName), "Folder NOT found: " + folderName);
    }
}