package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SvetaDTest {
    @Test
    public void searchCoat() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.target.com");
        Thread.sleep(1000);

        WebElement searchButton = driver.findElement(By.id("search"));
        Thread.sleep(1000);
        searchButton.click();
        searchButton.sendKeys("coat");
        Thread.sleep(1000);
        searchButton.sendKeys(Keys.RETURN);
        Thread.sleep(1000);

        WebElement item = driver.findElement(By.cssSelector("[data-test='product-title"));
        Thread.sleep(1000);

        String str = item.getText();
        Thread.sleep(1000);
        boolean result = str.toLowerCase().contains("coat");
        Assert.assertEquals(result, true);
        driver.quit();
    }
}
