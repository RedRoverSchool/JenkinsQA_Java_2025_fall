package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class IrinaShTest {

   @Test
   public void testOZ() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://oz.by/");


        //accept cookie
        driver.findElement(By.xpath("//*[@id=\"modalCookie\"]/div/div/div[3]/button[2]")).click();

        WebElement menu = driver.findElement(By.xpath("//*[@id=\"top-page\"]/div/div[1]/div[1]/div/div/ul/li[2]/a"));
        WebElement subMenu = driver.findElement(By.xpath("//*[@id=\"top-page\"]/div/div[1]/div[1]/div/div/ul/li[2]/div/div/ul/li[1]/div[1]/div[1]/a"));

        // open first category in submenu
        Actions actions = new Actions(driver);
        actions.moveToElement(menu).moveToElement(subMenu).click().build().perform();

        // add first book in checkout

        actions.moveToElement(driver.findElement(By.cssSelector("#goods-table > article:nth-child(1) > div.product-card__footer > form > button"))).click().build().perform();

        // open checkout
        Thread.sleep(10000);
        actions.moveToElement(driver.findElement(By.xpath("//*[@id=\"top-page\"]/header/div[1]/div/a[2]"))).click().build().perform();

        // check book in checkout


        Assert.assertNotNull(driver.findElement(By.xpath("//*[@id=\"goods-block\"]/tbody/tr[2]/td[1]")));



    }
}
