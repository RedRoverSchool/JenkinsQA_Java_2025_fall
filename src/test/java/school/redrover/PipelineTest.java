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
import java.util.Random;

public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "PipelineName";

    private static final Random random = new Random();

    private void createPipeline(String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    public static String generateRandomStringASCII(int minCode, int maxCode, int length) {
        if (length < 0 || length > 1000) {
            throw new IllegalArgumentException("Некорректная длина: " + length);
        }
        if (minCode < 32 || maxCode > 126 || minCode > maxCode) {
            throw new IllegalArgumentException("Некорректный диапазон для ASCII: [" + minCode + ", " + maxCode + "]");
        }
        if (length == 0) return "";

        StringBuilder sb = new StringBuilder(length);
        int range = maxCode - minCode + 1;
        for (int i = 0; i < length; i++) {
            sb.append((char) (minCode + random.nextInt(range)));
        }
        return sb.toString();
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
    public void testSuccessfulBuildPipeline() {
        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@data-build-success='Build scheduled']")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(6));

        wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-build-history"))).click();
        getDriver().findElement(By.xpath("//a[substring-before(@href, 'console')]")).click();

        WebElement consoleOutput = getDriver().findElement(By.id("out"));
        wait.until(d -> consoleOutput.getText().contains("Finished:"));

        Assert.assertTrue(consoleOutput.getText().contains("Finished: SUCCESS"),
                "Build output should contain 'Finished: SUCCESS'");
    }

    @Test
    public void testAddDescription() {
        final String textDescription = generateRandomStringASCII(32, 126, 85).trim();

        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(textDescription);
        getDriver().findElement(By.name("Submit")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        WebElement descriptionText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("description-content")));

        Assert.assertEquals(
                descriptionText.getText(),
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
    public void testScheduleWithValidData(String timePeriod) {
        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[contains(@href , 'configure')]")).click();

        WebElement triggersSectionButton = getDriver().findElement(By.xpath("//button[@data-section-id = 'triggers']"));
        triggersSectionButton.click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains(triggersSectionButton, "class", "task-link--active"));

        getDriver().findElement(By.xpath("//label[contains(text(), 'Build periodically')]")).click();

        WebElement scheduleTextArea = getDriver().findElement(By.xpath("//textarea[@name = '_.spec']"));
        scheduleTextArea.click();
        scheduleTextArea.sendKeys(timePeriod);

        getDriver().findElement(By.xpath("//button[text() = 'Apply']")).click();

        WebElement actualNotificationMessage = new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'Saved']")));

        WebElement actualTextAreaValidationMessage = getDriver()
                .findElement(By.xpath("//div[contains(text(), 'Schedule')]/following-sibling::div" +
                        "//div[@class = 'ok']"));

        Assert.assertEquals(actualNotificationMessage.getText(), "Saved");
        Assert.assertTrue(actualTextAreaValidationMessage.getText()
                        .matches("(?s)Would last have run at .*; would next run at .*"),
                "Alias " + timePeriod + " не прошёл валидацию");
    }

    @DataProvider
    public Object[][] invalidCronSyntaxAndAliases() {
        return new String[][]{
                {"60 * * * *", "60 is an invalid value. Must be within 0 and 59"},
                {"* 25 * * *", "25 is an invalid value. Must be within 0 and 23"},
                {"* * 32 * *", "32 is an invalid value. Must be within 1 and 31"},
                {"* * * 13 * *", "13 is an invalid value. Must be within 1 and 12"},
                {"* * * * 8", "8 is an invalid value. Must be within 0 and 7"},
                {"@", "mismatched input"},
                {"*****", "missing whitespace"}
        };
    }

    @Test(dataProvider = "invalidCronSyntaxAndAliases")
    public void testScheduleWithInvalidData(String invalidTimePeriod, String expectedErrorMessage) {
        createPipeline(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();

        WebElement triggersSectionButton = getDriver().findElement(By.xpath("//button[@data-section-id = 'triggers']"));
        triggersSectionButton.click();
        getWait2()
                .until(ExpectedConditions.attributeContains(triggersSectionButton, "class", "task-link--active"));

        getDriver().findElement(By.xpath("//label[contains(text(), 'Build periodically')]")).click();
        getDriver().findElement(By.xpath("//textarea[@name = '_.spec']")).sendKeys(invalidTimePeriod);
        getDriver().findElement(By.xpath("//button[text() = 'Apply']")).click();

        WebElement actualTextErrorMessage = getDriver()
                .findElement(By.xpath("//div[contains(text(), 'Schedule')]/following-sibling::div" +
                        "//div[@class = 'error']"));
        WebElement errorDescriptionModalWindow = getDriver().findElement(By.cssSelector("#error-description > h2"));
        getWait2().until(ExpectedConditions.visibilityOf(errorDescriptionModalWindow));

        Assert.assertTrue(
                actualTextErrorMessage.getText().contains(expectedErrorMessage),
                String.format("Сообщение: '%s', не содержит ожидаемую ключевую информацию об ошибке: '%s'",
                        actualTextErrorMessage.getText(), expectedErrorMessage));
        Assert.assertEquals(errorDescriptionModalWindow.getText(), "A problem occurred while processing the request");
    }

    @Test
    public void testEditDescription() {
        final String textDescription = generateRandomStringASCII(32, 126, 85).trim();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys("description");
        getDriver().findElement(By.name("Submit")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href = 'editDescription']")))
                .click();
        WebElement descriptionField = getDriver().findElement(By.name("description"));
        descriptionField.clear();
        descriptionField.sendKeys(textDescription);
        getDriver().findElement(By.name("Submit")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("description-link")));
        WebElement descriptionText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("description-content")));

        Assert.assertEquals(
                descriptionText.getText(),
                textDescription,
                "Не совпал текст description после его редактирования");
    }
}
