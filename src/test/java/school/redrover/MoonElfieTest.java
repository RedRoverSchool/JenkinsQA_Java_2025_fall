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

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        WebElement searchAuth = driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[21]/a"));
        searchAuth.click();
        WebElement writeLogin = driver.findElement(By.xpath("//*[@id=\"username\"]"));
        writeLogin.sendKeys("tomsmith");
        WebElement writePass = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        writePass.sendKeys("SuperSecretPassword!");
        WebElement pressButton1 = driver.findElement(By.xpath("//*[@id=\"login\"]/button/i"));
        pressButton1.click();

        WebElement checkWelcome = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h4"));
        Assert.assertEquals(checkWelcome.getText(),"Welcome to the Secure Area. When you are done click logout below.");

        WebElement checkSecArea = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h2"));
        Assert.assertEquals(checkSecArea.getText(),"Secure Area");

        WebElement checkPowerEl = driver.findElement(By.xpath("//*[@id=\"page-footer\"]/div/div"));
        Assert.assertEquals(checkPowerEl.getText(),"Powered by Elemental Selenium");

        WebElement pressElementSel = driver.findElement(By.xpath("//*[@id=\"page-footer\"]/div/div/a"));
        pressElementSel.click();


        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        WebElement checkElSel = driver.findElement(By.xpath("//*[@id=\"__docusaurus_skipToContent_fallback\"]/header/div/h1"));
        Assert.assertEquals(checkElSel.getText(),"Elemental Selenium");

        WebElement pressTmttt = driver.findElement(By.xpath("//*[@id=\"__docusaurus_skipToContent_fallback\"]/main/div/div/div/div/div/a/button"));
        pressTmttt.click();

        WebElement checkResources = driver.findElement(By.xpath("//*[@id=\"__docusaurus_skipToContent_fallback\"]/main/div/div[2]/div[2]/div/div[1]/div/div/button[2]/small"));
        Assert.assertEquals(checkResources.getText(),"RESOURCES");

        driver.quit();
    }
}