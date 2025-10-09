package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class SashaCorageTest {


    @Test
    public void niveaTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://www.nivea.co.uk/");

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            WebElement accept = driver.findElement(By.xpath("//*[@id=\"cmpbntyestxt\"]"));
            accept.click();

            WebElement arrow1 = driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/section[1]/div[2]/button[2]"));
            arrow1.click();
            arrow1.click();
            arrow1.click();

            WebElement hoverBody = driver.findElement(By.xpath("/html/body/header/div[2]/div/nav/ul/li[3]/a"));
            new Actions(driver).moveToElement(hoverBody).perform();

            WebElement w1click = driver.findElement(By.xpath("/html/body/header/div[2]/div/nav/ul/li[3]/div/div[1]/ul/li[2]/ul/li[2]/a"));
            w1click.click();

            WebElement text1 = driver.findElement(By.xpath("/html/body/section[2]/div[2]/div/div/h1"));
            Assert.assertEquals(text1.getText(), "SHOP SPRAY DEODORANTS");

            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1500);");
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1500);");

        } finally {
            driver.quit(); // гарантированно закроется
        }
    }
}
