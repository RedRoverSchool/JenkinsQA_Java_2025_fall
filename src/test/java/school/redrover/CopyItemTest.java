package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;

public class CopyItemTest extends BaseTest {

    static final String PROJECT_NAME1 = "Project1";
    static final String PROJECT_NAME2 = "Project2";

    @Test
    public void testCopyItem() {

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]/div[2]/label/span")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME1);
        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", okButton);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cb6")));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("cb6")));
        WebElement checkbox = getDriver().findElement(By.id("cb6"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", checkbox);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", checkbox);

        WebElement checkbox1 = getDriver().findElement(By.id("cb16"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", checkbox1);
        WebElement checkbox2 = getDriver().findElement(By.id("cb20"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", checkbox2);
        WebElement checkbox3 = getDriver().findElement(By.id("cb21"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", checkbox3);
        WebElement checkbox4 = getDriver().findElement(By.id("cb23"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", checkbox4);
        getDriver().findElement(By.name("Submit")).click();


        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.id("from")).sendKeys(PROJECT_NAME1);
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME2);
        getDriver().findElement(By.id("ok-button")).click();


        WebElement generalSectionCheckbox = getDriver().findElement(By.id("cb6"));
        Assert.assertTrue(generalSectionCheckbox.isSelected());

        WebElement triggerSectionCheckbox = getDriver().findElement(By.id("cb16"));
        Assert.assertTrue(triggerSectionCheckbox.isSelected());

        WebElement environmentSectionCheckbox1 = getDriver().findElement(By.id("cb20"));
        Assert.assertTrue(environmentSectionCheckbox1.isSelected());

        WebElement environmentSectionCheckbox2 = getDriver().findElement(By.id("cb21"));
        Assert.assertTrue(environmentSectionCheckbox2.isSelected());

        WebElement environmentSectionCheckbox3 = getDriver().findElement(By.id("cb23"));
        Assert.assertTrue(environmentSectionCheckbox3.isSelected());

        getDriver().findElement(By.name("Submit")).click();

        WebElement projectName = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[1]/div[1]/h1"));
        Assert.assertEquals(projectName.getText(), "Project2");
    }
}