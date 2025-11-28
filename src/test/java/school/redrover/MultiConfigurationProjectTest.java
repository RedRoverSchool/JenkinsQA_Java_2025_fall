package school.redrover;

import org.openqa.selenium.By;
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
    private static final String PROJECT_NAME = "Multiconfiguration project name";
    private static final String RENAMED_PROJECT = "Renamed multiconfiguration project";
    private static final String PROJECT_DESCRIPTION = "Project description...";

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

    public void goToProject(String projectName) {
        getDriver().findElement(By.xpath(String.format("//td/a[@href='job/%s/']", projectName))).click();
    }

    public void goToDashBoard() {
        waitTime(30).until(ExpectedConditions.visibilityOfElementLocated((By.className("app-jenkins-logo"))))
                .click();
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

    @Test
    public void testCreateProject() {
        String actualProjectName = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndSubmit()
                .clickSubmit()
                .getHeading();

        Assert.assertEquals(actualProjectName, PROJECT_NAME);
    }

    @Test
    public void testRenameViaSidebar() {
        String actualProjectName = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndSubmit()
                .clickSubmit()
                .clickRenameLinkInSideMenu()
                .clearNameField()
                .setNewProjectName(RENAMED_PROJECT)
                .getHeading();

        Assert.assertEquals(actualProjectName, RENAMED_PROJECT);
    }

    @Ignore
    @Test
    public void testAddDescriptionToProject() {
        createNewJob();
        setNameOfProject(PROJECT_NAME);
        selectProject();
        submitCreateProject();
        submitConfigure();
        goToDashBoard();
        goToProject(PROJECT_NAME);
        editDescription(PROJECT_DESCRIPTION);
        String result = checkDescription();

        softAssert.assertEquals(result, PROJECT_DESCRIPTION);
    }

    @Test
    public void testRenameViaDashboardDropdownMenu() {
        String actualProjectName = new HomePage(getDriver())
                .clickSidebarNewItem()
                .sendName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndSubmit()
                .clickSubmit()
                .clickRenameViaDashboardDropDownMenu()
                .clearNameField()
                .setNewProjectName(RENAMED_PROJECT)
                .getHeading();

        Assert.assertEquals(actualProjectName, RENAMED_PROJECT);
    }
}
