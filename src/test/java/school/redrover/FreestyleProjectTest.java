package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;


public class FreestyleProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "FreestyleProject";

    @Test
    public void testCreateFreestyleProject() throws InterruptedException {
        WebElement createJob = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        createJob.click();

        WebElement projectName = getDriver().findElement(By.id("name"));
        projectName.sendKeys("Freestyle Project");

        WebElement freestyleProjectOption = getDriver().findElement(By.xpath("//span[text()='Freestyle project']"));
        freestyleProjectOption.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.name("Submit"));
        saveButton.click();

        Thread.sleep(1500);

        WebElement projectTitle = getDriver().findElement(By.xpath("//h1"));

        Assert.assertEquals(projectTitle.getText(), "Freestyle Project");
    }

    @Ignore
    @Test
    public void testScheduleBuild() throws InterruptedException {
        WebElement createJob = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        createJob.click();

        WebElement projectName = getDriver().findElement(By.id("name"));
        projectName.sendKeys("Freestyle Project");

        WebElement freestyleProjectOption = getDriver().findElement(By.xpath("//span[text()='Freestyle project']"));
        freestyleProjectOption.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.name("Submit"));
        saveButton.click();

        WebElement jenkinsLogo = getDriver().findElement(By.id("jenkins-head-icon"));
        jenkinsLogo.click();

        WebElement projectTitle = getDriver().findElement(By.xpath("//a[starts-with(@href, 'job')]"));
        projectTitle.click();

        WebElement buildNowMenuOption = getDriver().findElement(By.xpath("//*[@id='tasks']/div[4]/span/a"));
        buildNowMenuOption.click();

        WebElement build = getDriver().findElement(By.xpath("//*[@id='tasks']/div[4]/span/a/span[2]"));

        Assert.assertTrue(build.isDisplayed());
    }

    @Test
    public void testCreateFreestyleProjectWithDesc() {
        String description = "This is a description for Freestyle Project";
        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//span[text()='Create a job']")).click();

        driver.findElement(By.id("name")).sendKeys("KM-Job1");
        driver.findElement(By.xpath("//span[text()='Freestyle project']")).click();
        driver.findElement(By.id("ok-button")).click();

        driver.findElement(By.name("description")).sendKeys(description);
        driver.findElement(By.name("Submit")).click();

        WebElement actualDesc = driver.findElement(By.id("description-content"));
        Assert.assertEquals(actualDesc.getText(), description);
    }

    @Test
    public void testDeleteFreestyleProject() {
        final String expectedHeadingText = "Welcome to Jenkins!";

        HomePage homePage = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(PROJECT_NAME)
                .selectFreestyleProjectAndSubmit()
                .clickSave()
                .gotoHomePage()
                .openDropdownMenu(PROJECT_NAME)
                .clickDeleteItemInDropdownMenu()
                .confirmDelete();

        Assert.assertEquals(homePage.getHeadingText(), expectedHeadingText);
    }
}
