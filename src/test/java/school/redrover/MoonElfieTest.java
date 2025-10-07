package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class MoonElfieTest {

    @Test
    public void loginTest() {

        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();

        driver.get("https://the-internet.herokuapp.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement step1 = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[21]/a"));
        step1.click();
        WebElement step2 = driver.findElement(By.xpath("//*[@id=\"username\"]"));
        step2.sendKeys("tomsmith");
        WebElement step3 = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        step3.sendKeys("SuperSecretPassword!");
        WebElement step4 = driver.findElement(By.xpath("//*[@id=\"login\"]/button/i"));
        step4.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));

        WebElement check1 = driver.findElement(By.xpath("/html/body/div[2]/div/div/h4"));
        Assert.assertEquals(check1.getText(),"Welcome to the Secure Area. When you are done click logout below.");

        WebElement check2 = driver.findElement(By.xpath("/html/body/div[2]/div/div/h2"));
        Assert.assertEquals(check2.getText(),"Secure Area");

        WebElement check3 = driver.findElement(By.xpath("//*[@id=\"page-footer\"]/div/div"));
        Assert.assertEquals(check3.getText(),"Powered by Elemental Selenium");

        WebElement step5 = driver.findElement(By.xpath("//*[@id=\"page-footer\"]/div/div/a"));
        step5.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(4000));

        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        WebElement check4 = driver.findElement(By.xpath("//*[@id=\"__docusaurus_skipToContent_fallback\"]/header/div/h1"));
        Assert.assertEquals(check4.getText(),"Elemental Selenium");

        WebElement step6 = driver.findElement(By.xpath("//*[@id=\"__docusaurus_skipToContent_fallback\"]/main/div/div/div/div/div/a/button"));
        step6.click();

        WebElement check5 = driver.findElement(By.xpath("//*[@id=\"__docusaurus_skipToContent_fallback\"]/main/div/div[2]/div[2]/div/div[1]/div/div/button[2]/small"));
        Assert.assertEquals(check5.getText(),"RESOURCES");

        driver.quit();
    }
}