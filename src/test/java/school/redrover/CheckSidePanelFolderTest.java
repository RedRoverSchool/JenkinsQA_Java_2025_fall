package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;

public class CheckSidePanelFolderTest extends BaseTest {

    @Test
    public void checkSidePanelJobs_Configure() {
        //подготовка
        String dirName = "newFolder";
        createFolder(dirName);
        goToMainPage();
        WebElement folderJob = findJobByName(dirName);
        folderJob.click();

        //тест
        WebElement sidePanel = getDriver().findElement(By.id("tasks"));
        List<WebElement> sidePanelElements = sidePanel.findElements(By.className("task"));
        WebElement testedElement = findElementByText(sidePanelElements, "Configure");
        Assert.assertNotNull(testedElement);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(testedElement));

        testedElement.click();
        String configureURL = getDriver().getCurrentUrl();
        Assert.assertTrue(configureURL.endsWith("/configure"));
    }

    private void goToMainPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        WebElement linkToMainPage = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.className("app-jenkins-logo")
                )
        );
        linkToMainPage.click();
    }

    private void createFolder(String dirName) {
        //нажимаем Создать Item
        WebElement createJobLink = getDriver().findElement(By.cssSelector("a[href='newJob']"));
        createJobLink.click();
        //вводим имя Item
        WebElement itemNameInput = getDriver().findElement(By.id("name"));
        itemNameInput.sendKeys(dirName);
        //Выбираем Folder
        WebElement folderButton = getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder"));
        folderButton.click();
        //кликаем OK
        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();
        //нажимаем Save
        WebElement saveButton = getDriver().findElement(By.cssSelector("button.jenkins-submit-button.jenkins-button--primary[name='Submit']"));
        saveButton.click();
    }

    private WebElement findJobByName(String dirName) {
        List<WebElement> allJobs = getDriver().findElements(By.cssSelector("a.jenkins-table__link.model-link.inside"));
        WebElement foundElement = null;
        for (int i = 0; i < allJobs.size(); i++) {
            WebElement current = allJobs.get(i);
            String jobName = current.getText();
            if (jobName != null && jobName.equals(dirName)) {
                foundElement = current;
                break;
            }
        }
        if (foundElement == null) {
            throw new RuntimeException("Element was not found");
        }
        return foundElement;
    }

    public WebElement findElementByText(List<WebElement> elements, String expectedText) {
        for (int j = 0; j < elements.size(); j++) {
            WebElement current = elements.get(j);
            String elementText = current.getText();
            if (elementText != null && elementText.equals(expectedText)) {
                return current;
            }
        }
        return null;
    }
}
