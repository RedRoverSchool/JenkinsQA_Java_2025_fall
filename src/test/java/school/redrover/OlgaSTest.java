package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class OlgaSTest {

    @Test
    public void testDodaho() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://dodaho.tj/");
        driver.getTitle();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement textBox = driver.findElement(By.name("search"));
        textBox.sendKeys("Погода в Душанбе");
        Thread.sleep(1000);
        textBox.sendKeys(Keys.RETURN);
        Thread.sleep(1000);
        WebElement textBox1 = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/h4/a"));
        Thread.sleep(1000);
        textBox1.click();
        Thread.sleep(4000);
        driver.quit();


    }
}