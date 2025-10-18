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
}
