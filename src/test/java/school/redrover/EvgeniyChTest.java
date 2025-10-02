package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EvgeniyChTest {
    @Test
    public void testDrom() {                         //тест авторизации drom.ru на пустой ввод и admin admin
        WebDriver driver = new ChromeDriver();

        driver.get("https://drom.ru/");

        driver.findElement(By.cssSelector("body > div:nth-child(7) > div._1e74g3sa.css-10ib5jr > div > a")).click();
        driver.findElement(By.id("signbutton")).click();
        WebElement error = driver.findElement(By.id("sign_errors"));

        Assert.assertEquals(error.getText(), "Поле должно быть заполнено");

        driver.findElement(By.id("sign")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.id("signbutton")).click();
        error = driver.findElement(By.id("sign_errors"));

        Assert.assertEquals(error.getText(), "Данные для входа неверны");

        driver.quit();


    }
}
