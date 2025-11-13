package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class MultiConfigProjectTest extends BaseTest {

    @Ignore
    @Test
    public void testRenameViaDropDownMenu() {

        final String projectName = "New Project";
        final String changedProjectName = "Multi-configuration project";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector("[class='hudson_matrix_MatrixProject']")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getDriver().findElement(By.name("Submit")).click();

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.cssSelector("[class$='hoverable-children-model-link']"))).perform();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[href$='confirm-rename']"))).click();

        WebElement renameField = getDriver().findElement(By.name("newName"));
        renameField.clear();
        renameField.sendKeys(changedProjectName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getWait10().until(ExpectedConditions.not(ExpectedConditions.urlContains("confirm-rename")));

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), changedProjectName);
    }
}
