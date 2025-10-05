package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TatianaKTest {
    @Test
    public void testSelenium() {
        WebDriver driver = new ChromeDriver();


        driver.get("https://practice-automation.com/modals/");

        WebElement simpleModalButton = driver.findElement(By.xpath("//*[@id=\"formModal\"]"));
        simpleModalButton.click();

        WebElement textName = driver.findElement(By.cssSelector("input#g1051-name"));
        textName.sendKeys("Tatiana");

        WebElement textEmail = driver.findElement(By.cssSelector("input#g1051-email"));
        textEmail.sendKeys("Ttt");

        WebElement textMessage = driver.findElement(By.xpath("//*[@id=\"contact-form-comment-g1051-message\"]"));
        textMessage.sendKeys("Hi! How are you?");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"jp-form-99ddf6d0cf76daf0c77607b1cc134112e4314c10\"]/button"));
        submitButton.click();

        WebElement message = driver.findElement(By.xpath("//*[@id=\"jp-form-99ddf6d0cf76daf0c77607b1cc134112e4314c10\"]/div[4]/ul/li/a"));
        Assert.assertEquals(message.getText(), "Email : Please enter a valid email address");

    }

}
