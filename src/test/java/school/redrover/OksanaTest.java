package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OksanaTest {
    @Test
    public void testWikipediaEnglishPage() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.wikipedia.org");

        Thread.sleep(1000);

        WebElement englishLink = driver.findElement(By.id("js-link-box-en"));
        englishLink.click();

        Thread.sleep(1000);

        String heading = driver.findElement(By.id("mp-welcome")).getText();
        Assert.assertTrue(heading.contains("Welcome to Wikipedia"));

        driver.quit();
    }
}
