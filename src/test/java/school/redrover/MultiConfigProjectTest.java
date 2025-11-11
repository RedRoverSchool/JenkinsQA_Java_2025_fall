package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class MultiConfigProjectTest extends BaseTest {

    @Test
    public void renameViaDropDownMenu() {

        final String ProjectName = "New Project";
        final String ChangedProjectName = "Multi-configuration project";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(ProjectName);
        getDriver().findElement(By.cssSelector("[class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement hoverElement = getDriver().findElement(By.cssSelector("[class$='hoverable-children-model-link']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(hoverElement).perform();
        getDriver().findElement(By.cssSelector("[href$='confirm-rename']")).click();

        WebElement renameField = getDriver().findElement(By.name("newName"));
        renameField.clear();
        renameField.sendKeys(ChangedProjectName);
        getDriver().findElement(By.xpath("//button[@name=\"Submit\"]")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), ChangedProjectName);
    }
}
