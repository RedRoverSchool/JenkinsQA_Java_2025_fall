package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupCodeCoffeeJavaTest {
    @Test
    public void testDemoQa() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/text-box");
        WebElement fullName = driver.findElement(By.id("userName"));
        fullName.sendKeys("Алиса Т.");

        Thread.sleep(1000);
        WebElement email = driver.findElement(By.cssSelector("#userEmail"));
        email.sendKeys("Alisya152@gmail.com");

        Thread.sleep(3000);
        WebElement submitButton = driver.findElement(By.cssSelector("#submit"));
        submitButton.click();

        WebElement resultName = driver.findElement(By.cssSelector("#name"));
        WebElement resultEmail = driver.findElement(By.cssSelector("#email"));

        Thread.sleep(3000);
        // Проверяем правильность текста
        Assert.assertEquals(resultName.getText(), "Name:Алиса Т.");
        Assert.assertEquals(resultEmail.getText(), "Email:Alisya152@gmail.com");

        Thread.sleep(3000);
        driver.close();
    }

    @Test
    public void testNegativeAuthorization() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://drom.ru/");

        driver.findElement(By.cssSelector("body > div:nth-child(7) > div._1e74g3sa.css-10ib5jr > div > a")).click();
        driver.findElement(By.id("signbutton")).click();

        driver.findElement(By.id("sign")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.id("signbutton")).click();

        WebElement error = driver.findElement(By.id("sign_errors"));
        Assert.assertEquals(error.getText(), "Данные для входа неверны");

        driver.quit();
    }
    @Test
    public void testSearchToyota() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://drom.ru/");

        driver.findElement(By.cssSelector("body > div:nth-child(7) > div._1e74g3sa.css-10ib5jr > div > div._1e74g3sd > a:nth-child(1)")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div/div[1]/div[1]/div/div[1]/input")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div/div[1]/div[1]/div/div[2]/div/div[3]/div")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div/div[4]/div[3]/button/div")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        String toyotaAd = driver.findElement(By.xpath("/html/body/div[2]/div[5]/div[1]/div[1]/div[5]/div/div[1]/div[1]/div[2]/div[1]/a/h3")).getText();
        Assert.assertEquals(toyotaAd.substring(0, 6), "Toyota");

        driver.quit();
    }
}
