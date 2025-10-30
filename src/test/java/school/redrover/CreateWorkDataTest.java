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

public class CreateWorkDataTest extends BaseTest {

    @Test
    public void TestCreateWorkData() throws InterruptedException{

        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]/div/section[1]/ul/li/a/span[1]"))
                .click();

        getDriver().findElement(By.xpath("//*[@id=\"name\"]"))
                .sendKeys("WorksData");

        WebElement organizationFolder = getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[3]"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", organizationFolder);
        organizationFolder.click();

        WebElement buttonOK = getDriver().findElement(By.xpath("//*[@id='ok-button']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", buttonOK);
        buttonOK.click();

        WebElement displayName = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/input"));
        displayName.sendKeys("29.10.2025");

        WebElement discriptionText = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[3]/div[2]/textarea"));
        discriptionText.sendKeys("Today I do more work for class project");

        WebElement saveButton = getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]"));
        saveButton.click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(text(),'Today I do more work for class project')]")
                )
        );

        Assert.assertEquals(message.getText(),
                "Today I do more work for class project");
    }
}
