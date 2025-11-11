package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class CreateNewItem1Test extends BaseTest {

    @Test
    public void testItemsAreDisplayed() {

        String[] expectedItemsList = {
                "Freestyle project",
                "Pipeline",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder"
        };

        String[] actualItemsList = new String[expectedItemsList.length];

        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        for (int i = 0; i < expectedItemsList.length; i++) {
            actualItemsList[i] = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(expectedItemsList[i]))).getText();
        }

        Assert.assertEquals(actualItemsList,expectedItemsList);
    }

    @Test
    public void testInvalidItemNameField() {

        String invalidChars = " !@#$%^&*[]|\\;:<>?/";

        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        WebElement nameInput = getDriver().findElement(By.id("name"));

        for (char ch : invalidChars.toCharArray()) {
            nameInput.clear();
            nameInput.sendKeys("" + ch);
            String dataValid = nameInput.getAttribute("data-valid");

            Assert.assertEquals(dataValid, "false",
                    "Character '" + ch + "' should not be allowed");
        }
    }

    @Test
    public void testConfigurationPageIsVisible() {

        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        getDriver().findElement(By.id("name")).sendKeys("TestProject");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[text() = 'Configuration']")).getText(),"Configuration");
    }

    @Test
    public void testFreestyleProjectConfigBuildSteps() {

        String[] expectedBuildSteps = {
                "Execute Windows batch command",
                "Execute shell",
                "Invoke Ant",
                "Invoke Gradle script",
                "Invoke top-level Maven targets",
                "Run with timeout",
                "Set build status to \"pending\" on GitHub commit"
        };

        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        getDriver().findElement(By.id("name")).sendKeys("TestProject");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement addBuildStep = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Add build')]")));

        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", addBuildStep);

        addBuildStep.click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Execute Windows batch command']")));


        for (int i = 0; i < expectedBuildSteps.length; i++) {
            WebElement actualBuildStep = getDriver().findElement(By.xpath("//button[contains(text(),'%s')]".formatted(expectedBuildSteps[i])));

            Assert.assertEquals(actualBuildStep.getText(),expectedBuildSteps[i]);
        }
    }
    @Test
    public void testBuildStepsFilter() {

        String[] expectedBuildSteps = {
                "Execute Windows batch command",
                "Execute shell",
                "Invoke Ant",
                "Invoke Gradle script",
                "Invoke top-level Maven targets",
                "Run with timeout",
                "Set build status to \"pending\" on GitHub commit"
        };

        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        getDriver().findElement(By.id("name")).sendKeys("TestProject");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement addBuildStep = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Add build')]")));

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", addBuildStep);

        addBuildStep.click();

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[normalize-space()='Execute Windows batch command']")
        ));

        for (String expectedStep : expectedBuildSteps) {

            WebElement filter = getDriver().findElement(By.xpath("//input[@type='search' and @placeholder='Filter']"));
            filter.clear();

            String filterPart = expectedStep.substring(0, Math.min(expectedStep.length(), 5));
            filter.sendKeys(filterPart);

            WebElement actualBuildStep = new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//button[contains(@style,'inline-flex') and normalize-space()='%s']".formatted(expectedStep))));

            Assert.assertEquals(actualBuildStep.getText(),expectedStep, "Filter didn't match expected build step");
        }
    }
}
