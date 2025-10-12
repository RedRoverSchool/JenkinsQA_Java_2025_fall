package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class MaksimSATest {

    @Test
    public void testSearchIphone() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://naveenautomationlabs.com/opencart/index.php?route=common/home");

        driver.findElement(By.cssSelector("[name='search']")).sendKeys("iPhone");
        driver.findElement(By.cssSelector("[class='btn btn-default btn-lg']")).click();

        driver.findElement(By.cssSelector("h4>a")).click();

        WebElement productName = driver.findElement(By.tagName("h1"));
        Assert.assertEquals(productName.getText(), "iPhone");

        WebElement searchInput = driver.findElement(By.cssSelector("[name='search']"));
        Assert.assertEquals(searchInput.getAttribute("value"), "iPhone");

        driver.quit();
    }
}
