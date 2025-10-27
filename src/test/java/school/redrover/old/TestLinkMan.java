package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
@Test
public class TestLinkMan {
    public void testLink() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com");
        Thread.sleep(1000);

        WebElement elementMan = driver.findElement(By.xpath("//a[@href=\"#Men\"]"));
        elementMan.click();
        Thread.sleep(500);

        WebElement elementJeans = driver.findElement(By.xpath("//a[@href=\"/category_products/6\"]"));
        elementJeans.click();
        Thread.sleep(1000);

        Assert.assertTrue(driver.findElement(By.xpath("//h2[@class]")).isDisplayed(),
                "Men - Jeans Products");

        driver.quit();
    }

}
