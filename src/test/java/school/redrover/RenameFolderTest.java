package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class RenameFolderTest extends BaseTest{
    @Test
    public void testRenameFolder(){
        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("NewFolder");
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Jenkins']"))).click();
    WebElement element = getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='NewFolder']")));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element)
                .moveByOffset(5, 0)
                .click()
                .perform();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/job/NewFolder/confirm-rename']"))).click();
        WebElement newName = getDriver().findElement(By.name("newName"));
        newName.click();
        newName.clear();
        newName.sendKeys("NewNameForFolder");
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("*//h1")).getText(), "NewNameForFolder");







    }



}
