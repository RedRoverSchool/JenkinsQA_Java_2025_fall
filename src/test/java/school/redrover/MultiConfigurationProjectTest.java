package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.time.Duration;


public class MultiConfigurationProjectTest extends BaseTest {
    private static final String MULTICONFIGURATION_PROJECT_NAME = "Multiconfiguration project";
    private static final String RENAMED_MULTICONFIGURATION_PROJECT = "RenamedMulticonfiguration project";
    private static final String DESCRIPTION = "Description for this project...";

    SoftAssert softAssert = new SoftAssert();

    public WebDriverWait waitTime(int time) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(time));
    }

    public void createNewJob() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
    }

    public void setNameOfProject(String nameOfProject) {
        getDriver().findElement(By.name("name")).sendKeys(nameOfProject);
    }

    public void selectProject() {
        getDriver().findElement(By.xpath("(//div[@id='j-add-item-type-standalone-projects']/ul)/li[3]"))
                .click();
    }

    public void submitCreateProject() {
        getDriver().findElement(By.id("ok-button")).click();
    }

    public void submitConfigure() {
        getDriver().findElement(By.name("Submit")).click();
    }

    public String getTitleOfProject() {
        return getDriver().findElement(By.xpath("//h1[@class= 'matrix-project-headline page-headline']")).getText();
    }

    public void goToProject(String projectName) {
        getDriver().findElement(By.xpath(String.format("//td/a[@href='job/%s/']", projectName))).click();
    }

    public void goToDashBoard() {
        waitTime(30).until(ExpectedConditions.visibilityOfElementLocated((By.className("app-jenkins-logo"))))
                .click();
    }

    public void renameWithSidePanel(String newName) {
        getDriver().findElement(By.xpath("(//div[@id='side-panel']//div[@class='task '])[7]")).click();
        WebElement rename = getDriver().findElement(By.name("newName"));
        rename.clear();
        rename.sendKeys(newName, Keys.ENTER);
    }

    public void editDescription(String text) {
        getDriver().findElement(By.xpath("//a[@href='editDescription']"))
                .click();
        WebElement description = getDriver().findElement(By.name("description"));
        description.clear();
        description.sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();
    }

    public String checkDescription() {
        return waitTime(40).until(ExpectedConditions.presenceOfElementLocated(By.id("description-content")))
                .getText();
    }

    @Ignore
    @Test
    public void testCreateProject() {
        createNewJob();
        setNameOfProject(MULTICONFIGURATION_PROJECT_NAME);
        selectProject();
        submitCreateProject();
        submitConfigure();

        softAssert.assertEquals(getTitleOfProject(), MULTICONFIGURATION_PROJECT_NAME);
        softAssert.assertTrue(waitTime(20).until(ExpectedConditions.urlContains(MULTICONFIGURATION_PROJECT_NAME)));
        softAssert.assertAll();
    }

    @Ignore
    @Test
    public void testRenameProject() {
        createNewJob();
        setNameOfProject(MULTICONFIGURATION_PROJECT_NAME);
        selectProject();
        submitCreateProject();
        submitConfigure();
        goToDashBoard();
        goToProject(MULTICONFIGURATION_PROJECT_NAME);
        renameWithSidePanel("NewNameProject");
        getTitleOfProject();

        softAssert.assertEquals(getTitleOfProject(), "NewNameProject");
    }

    @Ignore
    @Test
    public void testAddDescriptionToProject() {
        createNewJob();
        setNameOfProject(MULTICONFIGURATION_PROJECT_NAME);
        selectProject();
        submitCreateProject();
        submitConfigure();
        goToDashBoard();
        goToProject(MULTICONFIGURATION_PROJECT_NAME);
        editDescription(DESCRIPTION);
        String result = checkDescription();

        softAssert.assertEquals(result, DESCRIPTION);
    }

    @Test
    public void testRenameViaDropDownMenu() {
        String actualProjectName = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(MULTICONFIGURATION_PROJECT_NAME)
                .selectMultiConfigurationProjectAndSubmit()
                .clickSubmit()
                .clickRenameViaDropDownMenu()
                .renameProject(RENAMED_MULTICONFIGURATION_PROJECT)
                .getHeading();

        Assert.assertEquals(actualProjectName, RENAMED_MULTICONFIGURATION_PROJECT);
    }
}
