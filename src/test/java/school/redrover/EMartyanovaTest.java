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
}
