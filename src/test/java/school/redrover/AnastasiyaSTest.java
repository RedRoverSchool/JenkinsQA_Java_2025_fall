package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AnastasiyaSTest {

    @Test
    public void testTea () {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://roast.by/");

        WebElement submitButton = driver.findElement(By.xpath("/html/body/div[2]/header[2]/div/div/div/div[3]/div/ul/li[2]/a/span"));
        submitButton.click();

        WebElement tea = driver.findElement(By.xpath("//*[@id='catalog-ics__list-js']/a[8]"));
        tea.click();

        WebElement message = driver.findElement(By.xpath("//*[@id='bx_1527614749_20308']/div/div/div[2]/a"));

        Assert.assertEquals(message.getText(), "Фэн Хуан Дань Цун");
        driver.quit();
    }


    @Test
    public void testTeaCart () {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get("https://roast.by/");

        WebElement submitButton = driver.findElement(By.xpath("/html/body/div[2]/header[2]/div/div/div/div[3]/div/ul/li[2]/a/span"));
        submitButton.click();

        WebElement tea = driver.findElement(By.xpath("//*[@id='catalog-ics__list-js']/a[8]"));
        tea.click();

        WebElement cart = driver.findElement(By.xpath("//*[@id='bx_1527614749_20308']/div/div/div[3]/div/div[2]/div[1]"));
        cart.click();

        WebElement accs = driver.findElement(By.xpath("/html/body/div[2]/header[2]/div/div/div/div[3]/div/ul/li[3]/a/span"));
        accs.click();

        driver.quit();

    }
}
