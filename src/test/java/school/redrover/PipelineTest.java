package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;

public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "PipelineName";

    private void createPipeline(String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreatePipeline() throws InterruptedException {
        getDriver().findElement(By.cssSelector(".task:nth-child(1) a")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("div:first-child > ul > li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//a[@href='/']/img")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']")).getText(),
                PIPELINE_NAME);
    }

    @Test
    public void testDeletePipeline() throws InterruptedException {
        getDriver().findElement(By.cssSelector(".task:nth-child(1) a")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("div:first-child > ul > li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//a[@href='/']/img")).click();

        List<WebElement> countPosition = getDriver().findElements(By.cssSelector("#projectstatus > tbody > tr"));

        getDriver().findElement(By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']")).click();
        getDriver().findElement(By.cssSelector(".task:nth-child(6)")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertEquals(countPosition.size() - 1, 0);
    }

    @Test
    public void testSuccessfulBuildPipeline() throws InterruptedException {
        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@data-build-success='Build scheduled']")).click();

        Thread.sleep(3000);
        getDriver().findElement(By.id("jenkins-build-history")).click();
        getDriver().findElement(By.xpath("//a[substring-before(@href, 'console')]")).click();

        String consoleOutputText = getDriver().findElement(By.id("out")).getText();

        Assert.assertTrue(consoleOutputText.contains("Finished: SUCCESS"),
                "Build output should contain 'Finished: SUCCESS'");
    }

    @Test
    public void testAddDescription() throws InterruptedException {
        final String textDescription = "\"aB3_mX9!qW@vL# zP$eR%nY^kU&[";

        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(textDescription);
        getDriver().findElement(By.name("Submit")).click();

        Thread.sleep(500);
        Assert.assertEquals(
                getDriver().findElement(By.id("description-content")).getText(),
                textDescription);
    }

    @DataProvider
    public Object[][] validAliases() {
        return new String[][]{
                {"@yearly"},
                {"@annually"},
                {"@monthly"},
                {"@weekly"},
                {"@daily"},
                {"@midnight"},
                {"@hourly"}
        };
    }

    @Test(dataProvider = "validAliases")
    public void testScheduleWithValidData(String name) {
        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[contains(@href , 'configure')]")).click();

        WebElement triggersSectionButton = getDriver().findElement(By.xpath("//button[@data-section-id = 'triggers']"));
        triggersSectionButton.click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains(triggersSectionButton, "class", "task-link--active"));

        getDriver().findElement(By.xpath("//label[contains(text(), 'Build periodically')]")).click();

        WebElement scheduleTextarea = getDriver().findElement(By.xpath("//textarea[@name = '_.spec']"));
        scheduleTextarea.click();
        scheduleTextarea.sendKeys(name);

        getDriver().findElement(By.xpath("//button[text() = 'Apply']")).click();

        WebElement notificationResult = getDriver().findElement(By.xpath("//span[text() = 'Saved']"));
        WebElement validationMessageResult = getDriver()
                .findElement(By.xpath("//div[contains(text(), 'Schedule')]/following-sibling::div" +
                        "//div[@class = 'ok']"));

        Assert.assertEquals(notificationResult.getText(), "Saved");
        Assert.assertTrue(validationMessageResult.getText()
                        .matches("(?s)Would last have run at .*; would next run at .*"),
                "Alias " + name + "не прошёл валидацию");
    }
}
