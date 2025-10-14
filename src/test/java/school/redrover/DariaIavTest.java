package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DariaIavTest {

    @Test
    public void testWebForm() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        driver.findElement(By.xpath("//a[text()='Web form']")).click();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("web-form"), "ссылка должна содержать 'web-form'");

        driver.quit();
    }
}
