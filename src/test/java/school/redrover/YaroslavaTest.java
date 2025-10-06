package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class YaroslavaTest {
    @Test
    public void testProductsButton() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");

        WebElement products = driver.findElement(By.xpath("//a[@href='/products']"));
        System.out.println(products.getText());
        Assert.assertEquals(products.getText(), " Products");

        driver.quit();
    }

}
