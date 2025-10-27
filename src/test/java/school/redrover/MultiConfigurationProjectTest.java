package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import school.redrover.common.BaseTest;

import java.time.Duration;


public class MultiConfigurationProjectTest extends BaseTest {
    private static final String NAME_OF_PROJECT = "Group-Code-Coffee_java_project";

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

    @Test
    public void testCreateProject() {
        createNewJob();
        setNameOfProject(NAME_OF_PROJECT);
        selectProject();
        submitCreateProject();
        submitConfigure();

        softAssert.assertEquals(getTitleOfProject(), NAME_OF_PROJECT);
        softAssert.assertTrue(waitTime(20).until(ExpectedConditions.urlContains(NAME_OF_PROJECT)));
        softAssert.assertAll();
    }

    @Test(priority = 1)
    public void testAddDescriptionToProject() {
        createNewJob();
        setNameOfProject(NAME_OF_PROJECT);
        selectProject();
        submitCreateProject();
        submitConfigure();
        goToDashBoard();
        goToProject(NAME_OF_PROJECT);
        renameWithSidePanel("NewNameProject");
        getTitleOfProject();

        softAssert.assertEquals(getTitleOfProject(), "NewNameProject");
    }

    @Test(priority = 2)
    public void testRenameProject() {
        //your code may be here

    }

    @Test(priority = 3)
    public void testDeleteProject() {
        //your code may be here

    }


}
