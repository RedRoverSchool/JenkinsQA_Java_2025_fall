package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class ConfigurationMatrixTest extends BaseTest {

    @Test (testName = "TC_04.006.01 | Configuration Matrix > Section visibility")
    public void testSectionDisplayed() {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("nameProject");
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement matrixHeader = getDriver().findElement(By.id("configuration-matrix"));
        new Actions(getDriver()).moveToElement(matrixHeader).perform();

        Assert.assertTrue(matrixHeader.isDisplayed(), "Configuration Matrix header is not visible");
    }
}