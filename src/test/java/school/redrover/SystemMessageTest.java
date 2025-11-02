package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class SystemMessageTest extends BaseTest {

    private void clearSystemMessage() {
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        getDriver().findElement(By.name("system_message")).clear();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreateMessage() {
        final String expectedMessage = "This is the best project!";

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        WebElement input = getDriver().findElement(By.name("system_message"));
        input.sendKeys(expectedMessage);
        getDriver().findElement(By.name("Submit")).click();

        String actualMessage = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.id("systemmessage"))).getText();

        Assert.assertEquals(actualMessage, expectedMessage);

        clearSystemMessage();
    }
    
    @Test
    public void testPreview() {
        final String expectedMessage = "I want to see this message on preview!";

        // усложнено специально для практики изученного материала
        getDriver().findElement(By.xpath("//div[@class='jenkins-header__actions']/a[1]")).click();
        getDriver().findElements(By.className("jenkins-section__item"))
                .stream()
                .filter(x -> x.getText().contains("System"))
                .findFirst()
                .ifPresent(WebElement::click);

        getDriver().findElement(By.name("system_message")).sendKeys(expectedMessage);
        getDriver().findElement(By.className("textarea-show-preview")).click();

        WebElement preview = new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.className("textarea-preview")
                ));

        Assert.assertEquals(preview.getText(), expectedMessage);
    }
}
