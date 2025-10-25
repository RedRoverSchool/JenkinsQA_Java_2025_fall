package school.redrover;

import org.openqa.selenium.By;
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


}
