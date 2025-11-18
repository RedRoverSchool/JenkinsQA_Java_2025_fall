package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.PipelinePage;

import java.util.List;
import java.util.Random;

public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "PipelineName";

    private static final Random random = new Random();

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

    private void createPipeline(String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreateNewPipeline() {
        List<String> actualProjectList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PIPELINE_NAME)
                .selectPipelineAndSubmit()
                .gotoHomePage()
                .getProjectList();

        Assert.assertTrue(actualProjectList.contains(PIPELINE_NAME),
                String.format("Pipeline with name '%s' was not created", PIPELINE_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewPipeline")
    public void testDeletePipelineViaDropDownMenu() {
        final String expectedHomePageHeading = "Welcome to Jenkins!";

        String actualHomePageHeading = new HomePage(getDriver())
                .openDropdownMenu(PIPELINE_NAME)
                .clickDeleteItemInDropdownMenu()
                .confirmDelete()
                .getHeadingText();

        Assert.assertEquals(actualHomePageHeading, expectedHomePageHeading);
    }

    @Test
    public void testCancelDeletePipelineViaDropDownMenu() {
        List<String> actualProjectList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PIPELINE_NAME)
                .selectPipelineAndSubmit()
                .gotoHomePage()
                .openDropdownMenu(PIPELINE_NAME)
                .clickDeleteItemInDropdownMenu()
                .cancelDelete()
                .getProjectList();

        Assert.assertTrue(actualProjectList.contains(PIPELINE_NAME));
    }

    @Test
    public void testCancelDeletePipelineViaSideMenu() {
        List<String> actualProjectList = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PIPELINE_NAME)
                .selectPipelineAndSubmit()
                .gotoHomePage()
                .openJobPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickDeletePipeline()
                .cancelDelete()
                .gotoHomePage()
                .getProjectList();

        Assert.assertTrue(actualProjectList.contains(PIPELINE_NAME));
    }

    @Test
    public void testDeletePipelineViaSideMenu() {
        final String expectedHomePageHeading = "Welcome to Jenkins!";

        String actualHomePageHeading = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PIPELINE_NAME)
                .selectPipelineAndSubmit()
                .gotoHomePage()
                .openJobPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickDeletePipeline()
                .confirmDeleteAtJobPage()
                .getHeadingText();

        Assert.assertEquals(actualHomePageHeading, expectedHomePageHeading);
    }

    @Test
    public void testCreatePipeline() {
        getDriver().findElement(By.cssSelector(".task:nth-child(1) a")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("div:first-child > ul > li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();


        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/']/img"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']")).getText(),
                PIPELINE_NAME);
    }

    @Test
    public void testDeletePipeline() {
        getDriver().findElement(By.cssSelector(".task:nth-child(1) a")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("div:first-child > ul > li:nth-child(2)")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/']/img"))).click();

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

        getWait10().until(ExpectedConditions.elementToBeClickable(By.id("jenkins-build-history"))).click();
        getDriver().findElement(By.xpath("//a[substring-before(@href, 'console')]")).click();

        WebElement consoleOutput = getDriver().findElement(By.id("out"));
        getWait10().until(d -> consoleOutput.getText().contains("Finished:"));

        Assert.assertTrue(consoleOutput.getText().contains("Finished: SUCCESS"),
                "Build output should contain 'Finished: SUCCESS'");
    }

    @Test
    public void testAddDescription() {
        final String textDescription = generateRandomStringASCII(32, 126, 85).trim();

        String descriptionText = new HomePage(getDriver())
                .clickNewItemOnLeftMenu()
                .sendName(PIPELINE_NAME)
                .selectPipelineAndSubmit()
                .clickSaveButton()
                .clickAddDescriptionButton()
                .addDescriptionAndSave(textDescription)
                .getDescription();

        Assert.assertEquals(descriptionText, textDescription);
    }

    @Test(dependsOnMethods = "testAddDescription")
    public void testEditDescription() {
        final String textDescription = generateRandomStringASCII(32, 126, 85).trim();

        String descriptionText = new HomePage(getDriver())
                .openJobPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickEditDescriptionButton()
                .clearDescription()
                .addDescriptionAndSave(textDescription)
                .getDescription();

        Assert.assertEquals(
                descriptionText,
                textDescription,
                "Не совпал текст description после его редактирования");
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
    public void testScheduleWithValidData(String validTimePeriod) {
        createPipeline(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[contains(@href , 'configure')]")).click();

        WebElement triggersSectionButton = getDriver().findElement(By.xpath("//button[@data-section-id = 'triggers']"));
        triggersSectionButton.click();
        getWait2()
                .until(ExpectedConditions.attributeContains(triggersSectionButton, "class", "task-link--active"));

        getDriver().findElement(By.xpath("//label[contains(text(), 'Build periodically')]")).click();
        getDriver().findElement(By.xpath("//textarea[@name = '_.spec']")).sendKeys(validTimePeriod);
        getDriver().findElement(By.xpath("//button[text() = 'Apply']")).click();

        WebElement actualNotificationMessage = getWait2()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'Saved']")));

        WebElement actualTextAreaValidationMessage = getDriver()
                .findElement(By.xpath("//div[contains(text(), 'Schedule')]/following-sibling::div" +
                        "//div[@class = 'ok']"));

        Assert.assertEquals(actualNotificationMessage.getText(), "Saved");
        Assert.assertTrue(actualTextAreaValidationMessage.getText()
                        .matches("(?s)Would last have run at .*; would next run at .*"),
                "Alias " + validTimePeriod + " не прошёл валидацию");
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
}
