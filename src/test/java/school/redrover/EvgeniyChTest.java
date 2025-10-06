package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class EvgeniyChTest {
    @Test
    public void testNegativeAuthorization() {     //test negative authorization on Drom.ru (empty input and admin/admin)
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
    @Test
    public void testSearchToyota() {              //test search car Toyota
        WebDriver driver = new ChromeDriver();

        driver.get("https://drom.ru/");

        driver.findElement(By.cssSelector("body > div:nth-child(7) > div._1e74g3sa.css-10ib5jr > div > div._1e74g3sd > a:nth-child(1)")).click();
     //   driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.findElement(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div/div[1]/div[1]/div/div[1]/input"))
                .click();
       // driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.findElement(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div/div[1]/div[1]/div/div[2]/div/div[3]/div"))
                .click();
       // driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.findElement(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div/div[4]/div[3]/button/div"))
                .click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        String toyotaAd =  driver.findElement(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[5]/div/div[1]/div[1]/div[2]/div[1]/a/h3"))
                .getText();
        Assert.assertEquals(toyotaAd.substring(0, 6), "Toyota");

        driver.quit();

    }

}
