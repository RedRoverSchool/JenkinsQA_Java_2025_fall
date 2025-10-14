package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MariannaKlimenkoTest {
    @Test
    public void testAddToCart() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com");

        Thread.sleep(200);
        WebElement consentButton = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[2]/button[1]/p"));
        consentButton.click();

        WebElement productLink = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[2]/a"));
        productLink.click();

        WebElement womenCategory = driver.findElement(By.xpath("//*[@id=\"accordian\"]/div[1]/div[1]/h4/a"));
        womenCategory.click();
        Thread.sleep(100);

        WebElement dressCategory = driver.findElement(By.xpath("//*[@id=\"Women\"]/div/ul/li[1]/a"));
        dressCategory.click();

        WebElement product1 = driver.findElement(By.xpath("/html/body/section/div/div[2]/div[2]/div/div[2]/div/div[1]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(product1).perform();

        WebElement addToCartButton = driver.findElement(By.xpath("/html/body/section/div/div[2]/div[2]/div/div[2]/div/div[1]/div[2]/div/a"));
        Thread.sleep(100);
        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        addToCartButton.click();
        Thread.sleep(300);
        WebElement message = driver.findElement(By.xpath("//*[@id=\"cartModal\"]/div/div/div[2]/p[1]"));
        Assert.assertEquals(message.getText(), "Your product has been added to cart.");

        driver.quit();
    }
}
