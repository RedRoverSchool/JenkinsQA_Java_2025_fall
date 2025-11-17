package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

public class ConfigurationMatrixTest extends BaseTest {

    private void createConfigurationMatrix() {

        String jobName = "test-" + System.currentTimeMillis();

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

    }

    @Test
    public void testSectionDisplayed() {

        createConfigurationMatrix();

        WebElement matrixHeader = getDriver().findElement(By.id("configuration-matrix"));
        new Actions(getDriver()).moveToElement(matrixHeader).perform();

        Assert.assertTrue(matrixHeader.isDisplayed(), "Configuration Matrix header is not visible");

    }

    @Test
    public void testAddAxisMenuOnlyUserDefined() {

        createConfigurationMatrix();

        By addAxisLocator = By.cssSelector("[suffix=axis]");
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(addAxisLocator));
        TestUtils.clickJS(getDriver(), addAxisLocator);

        By itemMenu = By.cssSelector(".jenkins-dropdown__item");
        List<WebElement> items = getWait10().until(visibilityOfAllElementsLocatedBy(itemMenu));

        Assert.assertEquals(items.size(), 1, "Dropdown should contain exactly one item");
        Assert.assertEquals(items.get(0).getText().trim(), "User-defined Axis",
                "The only option must be 'User-defined Axis'");
    }
}