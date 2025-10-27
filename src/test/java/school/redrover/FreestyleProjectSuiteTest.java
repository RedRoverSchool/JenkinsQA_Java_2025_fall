package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

public class FreestyleProjectSuiteTest extends BaseTest {

    private final String FREESTYLE_PROJECT_NAME = "Freestyle-" + UUID.randomUUID().toString().substring(0, 5);
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
        getDriver().findElement(By.id("ok-button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.name("Submit")))).click();
    }

    @Test
    public void testCreateFreestyleProject() {
        createNewItem(FREESTYLE_PROJECT_NAME, "Freestyle project");
        Assert.assertTrue(Objects.requireNonNull(getDriver().getCurrentUrl()).contains("/job/" + FREESTYLE_PROJECT_NAME + "/"),
                "Freestyle project is not created");
        Assert.assertEquals(getDriver().findElement(By.className("job-index-headline")).getText(),
                FREESTYLE_PROJECT_NAME, "Wrong project is created/opened");
    }
}
