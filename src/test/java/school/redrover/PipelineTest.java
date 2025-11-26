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

public class PipelineTest extends BaseTest {

    private static final String PIPELINE_NAME = "PipelineName";

    private void createPipeline(String name) {
        new HomePage(getDriver())
                .clickCreateJob()
                .sendName(name)
                .selectPipelineAndSubmit()
                .clickSaveButton();
    }

    @Test
    public void testCreateNewPipeline() {
        createPipeline(PIPELINE_NAME);
        List<String> actualProjectList = new PipelinePage(getDriver())
                .gotoHomePage()
                .getProjectList();

        Assert.assertTrue(actualProjectList.contains(PIPELINE_NAME),
                String.format("Pipeline with name '%s' was not created", PIPELINE_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewPipeline")
    public void testCancelDeletePipelineViaSideMenu() {
        List<String> actualProjectList = new HomePage(getDriver())
                .openPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickDeletePipeline()
                .cancelDelete()
                .gotoHomePage()
                .getProjectList();

        Assert.assertTrue(actualProjectList.contains(PIPELINE_NAME));
    }

    @Test(dependsOnMethods = "testCancelDeletePipelineViaSideMenu")
    public void testBuildPipeline() {

        String consoleOutput = new HomePage(getDriver())
                .openPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickBuildNow()
                .clickBuildHistory()
                .clickConsoleOutput()
                .getConsoleOutput();

        Assert.assertTrue(consoleOutput.contains("Finished:"),
                "Build output should contain 'Finished:'");

    }


    @Test(dependsOnMethods = "testBuildPipeline")
    public void testAddDescription() {
        final String textDescription = "@0*8nFP'cRU0k.|6Gz-wO*se h~OtJ4kz0!)cl0ZAE3vN>q";

        String descriptionText = new HomePage(getDriver())
                .gotoHomePage()
                .openPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickAddDescriptionButton()
                .addDescriptionAndSave(textDescription)
                .getDescription();

        Assert.assertEquals(descriptionText, textDescription);
    }



    @Test(dependsOnMethods = "testAddDescription")
    public void testEditDescription() {
        final String textDescription = "D0XVcGo8k(=D7myr/.YC6umm>]\"gY)?X_E|#HPku6T5im[oYHD-\\|B`";

        String descriptionText = new HomePage(getDriver())
                .openPage(PIPELINE_NAME, new PipelinePage(getDriver()))
                .clickEditDescriptionButton()
                .clearDescription()
                .addDescriptionAndSave(textDescription)
                .getDescription();

        Assert.assertEquals(
                descriptionText,
                textDescription,
                "Не совпал текст description после его редактирования");
    }


    @Test(dependsOnMethods = "testEditDescription")
    public void testCancelDeletePipelineViaDropDownMenu() {
        List<String> actualProjectList = new HomePage(getDriver())
                .gotoHomePage()
                .openDropdownMenu(PIPELINE_NAME)
                .clickDeleteItemInDropdownMenu()
                .cancelDelete()
                .getProjectList();

        Assert.assertTrue(actualProjectList.contains(PIPELINE_NAME));
    }

    @Test(dependsOnMethods = "testCancelDeletePipelineViaDropDownMenu")
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
    public void testDeletePipelineViaSideMenu() {
        final String expectedHomePageHeading = "Welcome to Jenkins!";

        createPipeline(PIPELINE_NAME);

        String actualHomePageHeading = new PipelinePage(getDriver())
                .clickDeletePipeline()
                .confirmDeleteAtJobPage()
                .getHeadingText();

        Assert.assertEquals(actualHomePageHeading, expectedHomePageHeading);
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
