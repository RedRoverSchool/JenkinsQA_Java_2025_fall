package school.redrover;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class CjCityTest {

    @Test
    public void testCjCity() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("http://www.cjcity.ru");

        WebElement button = driver.findElement(By.xpath("//*[@id=\"nav\"]/li[5]/a"));
        button.click();

        WebElement input = driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset/div/div[1]/input"));
        input.sendKeys("Luminoforium");

        WebElement button1 = driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset/div/div[3]/input"));
        button1.click();

        WebElement button2 = driver.findElement(By.xpath("//*[@id=\"content\"]/ul[2]/li/div[2]/span/a"));
        button2.click();

        driver.quit();
    }
}
