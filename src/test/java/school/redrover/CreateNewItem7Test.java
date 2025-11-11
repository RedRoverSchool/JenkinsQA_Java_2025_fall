package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class CreateNewItem7Test extends BaseTest {

    @Test
    public void createNewFreeStyleProjectTest() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Freestyle Project Name");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getWait2().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//h1"), "Freestyle Project"));

        WebElement projectTitle = getDriver().findElement(By.xpath("//h1"));

        Assert.assertEquals(projectTitle.getText(), "Freestyle Project Name");
    }

}
