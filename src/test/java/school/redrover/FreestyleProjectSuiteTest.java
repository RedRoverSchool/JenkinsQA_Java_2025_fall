package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

public class FreestyleProjectSuiteTest extends BaseTest {

    final String FREESTYLE_PROJECT_NAME = "Freestyle-" + UUID.randomUUID().toString().substring(0, 5);
    private final WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

    public void createNewItem(String itemName, String item) {
        getDriver().findElement(By.linkText("New Item")).click();

        getDriver().findElement(By.id("name")).sendKeys(itemName);
        String itemClassName = switch (item) {
            case "Freestyle project" -> "hudson_model_FreeStyleProject";
            case "Folder" -> "com_cloudbees_hudson_plugins_folder_Folder";
            default -> "";
        };
        getDriver().findElement(By.className(itemClassName)).click();
        Assert.assertEquals(getDriver().findElement(By.className(itemClassName)).getAttribute("aria-checked"),
                "true", item + "is not checked");

        getDriver().findElement(By.id("ok-button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.name("Submit")))).click();

        Assert.assertTrue(Objects.requireNonNull(getDriver().getCurrentUrl()).contains("http://localhost:8080/job/" + itemName + "/"),
                item + " is not created");
    }

    @Test
    public void testCreateFreestyleProject() {
        createNewItem(FREESTYLE_PROJECT_NAME, "Freestyle project");

        Assert.assertEquals(getDriver().findElement(By.className("job-index-headline")).getText(),
                FREESTYLE_PROJECT_NAME, "Wrong project is created/opened");
    }

    @Test
    public void testAddDescriptionToFreestyleProject() {
        createNewItem(FREESTYLE_PROJECT_NAME, "Freestyle project");

        getDriver().findElement(By.linkText("Add description")).click();
        WebElement descriptionField = getDriver().findElement(By.name("description"));
        descriptionField.clear();
        descriptionField.sendKeys("Description for " + FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.id("description-content")))).getText(),
                "Description for " + FREESTYLE_PROJECT_NAME, "Wrong description");
    }

    @Test
    public void testRenameFreestyleProject() {
        final String newFreestyleProjectName = "New" + FREESTYLE_PROJECT_NAME;
        createNewItem(FREESTYLE_PROJECT_NAME, "Freestyle project");

        getDriver().findElement(By.linkText("Rename")).click();
        WebElement newNameField = getDriver().findElement(By.name("newName"));
        newNameField.clear();
        newNameField.sendKeys(newFreestyleProjectName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.className("job-index-headline")).getText(), newFreestyleProjectName,
                "Wrong new name");
    }

    @Test
    public void testMoveFreestyleProjectToFolder() throws InterruptedException {
        final String folderName = "Folder-" + UUID.randomUUID().toString().substring(0, 5);
        createNewItem(folderName, "Folder");
        Thread.sleep(200);
        getDriver().findElement(By.className("app-jenkins-logo")).click();
        createNewItem(FREESTYLE_PROJECT_NAME, "Freestyle project");
        getDriver().findElement(By.linkText("Move")).click();

        Select select = new Select(getDriver().findElement(By.className("jenkins-select__input")));
        select.selectByValue("/" + folderName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().findElement(By.id("main-panel")).getText().contains(
                "Full project name: " + folderName + "/" + FREESTYLE_PROJECT_NAME), "Wrong full project name");

        getDriver().findElement(By.className("app-jenkins-logo")).click();
        getDriver().findElement(By.linkText(folderName)).click();

        try {
            getDriver().findElement(By.linkText(FREESTYLE_PROJECT_NAME));
            Assert.assertTrue(true);
        } catch (NoSuchElementException e) {
            Assert.fail("Freestyle project not found in folder");
        }
    }

    @Test
    public void testDeleteFreestyleProject() {
        createNewItem(FREESTYLE_PROJECT_NAME, "Freestyle project");

        getDriver().findElement(By.linkText("Delete Project")).click();
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//button[@data-id='ok']")))).click();
        getDriver().get("http://localhost:8080/job/" + FREESTYLE_PROJECT_NAME);

        Assert.assertTrue(getDriver().findElement(By.id("error-description")).getText().contains("Not Found"),
                "Wrong message about deleted item");
    }
}
