package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class CreateNewItem6Test extends BaseTest {

    @Test
    public void testOkButtonDisabledWithEmptyName(){

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        getDriver().findElement(By.linkText("New Item")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='label' and text()='Pipeline']")));

        getDriver().findElement(By.xpath("//span[@class='label' and text()='Pipeline']")).click();
        WebElement okButton = getDriver().findElement(By.id("ok-button"));

        Assert.assertFalse(okButton.isEnabled());


    }
}
