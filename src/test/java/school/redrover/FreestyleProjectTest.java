package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;


public class FreestyleProjectTest extends BaseTest {

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

    @Test
    public void testAddDescription() {
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

        WebElement addDescription = getDriver().findElement(By.id("description-link"));
        addDescription.click();

        WebElement textBox = getDriver().findElement(By.xpath("//textarea"));
        textBox.sendKeys("This is a Freestyle Project");

        WebElement saveButtonDescription = getDriver().findElement(By.name("Submit"));
        saveButtonDescription.click();

        WebElement description = getDriver().findElement(By.id("description-content"));

        Assert.assertEquals(description.getText(), "This is a Freestyle Project");
    }
}
