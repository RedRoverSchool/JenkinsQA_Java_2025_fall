package school.redrover;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class AnnaMelnikovaTest {

    @Test
    public void testNumberOne() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        String title = driver.getTitle();
        System.out.println("Title of the page: " + title);
        Assert.assertTrue(title.contains("Example Domain"), "Title does not contain 'Example Domain'");
        driver.quit();
    }
    @Test
    public void testHW15() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.99-bottles-of-beer.net/lyrics.html");
        WebElement topLists =driver.findElement(By.xpath("//*[@id=\"menu\"]/li[4]/a"));
        topLists.click();
        WebElement topHits = driver.findElement(By.linkText("Top Hits"));
        topHits.click();
        System.out.println("Две кнопки нашли, нажали, вот заголовок перехода и название сайта  "+ driver.getTitle());
        driver.quit();
    }
}
