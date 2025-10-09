package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class EMartyanovaTest {
    @Test
    public void testBonusPage() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.rzd.ru");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        driver.findElement(By.xpath("//a[@class='header_actions-item__link' " +
                "and @href='https://rzd-bonus.ru/']")).click();
        driver.findElement(By.xpath("//ul[@class = 'horizontal-nav']" +
                "//a[@href = '#save-points']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//h2[(text()='Как копить баллы')]")).getText(),
                "Как копить баллы");

        driver.quit();
    }

    @Test
    public void testBackToMainPageLink() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/inventory.html");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        driver.findElement(By.xpath("//div[@class = 'inventory_item_name'][1]")).click();
        driver.findElement(By.xpath("//button[@class = 'inventory_details_back_button']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class = 'product_label']")).getText(),
                "Products");

        driver.quit();
    }

    @Test
    public void testChangeButtonTextAtAdding() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/inventory.html");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        driver.findElement(By.xpath("(//button[@class='btn_primary btn_inventory'])[1]")).click();
        Assert.assertEquals(driver.findElement(By.xpath("(//button[@class='btn_secondary btn_inventory'])[1]"))
                .getText(), "REMOVE");

        driver.quit();
    }

    @Test
    public void testChangeBasketCounterAtAdding() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/inventory.html");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        String basketCounterSelector = "//span[@class = 'fa-layers-counter shopping_cart_badge']";

        Assert.assertTrue(driver.findElements(By.xpath(basketCounterSelector)).isEmpty());

        driver.findElement(By.xpath("(//button[@class='btn_primary btn_inventory'])[1]")).click();
        Assert.assertEquals(driver.findElement(By.xpath(basketCounterSelector)).getText(), "1");

        driver.quit();
    }
}
