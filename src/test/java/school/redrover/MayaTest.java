package school.redrover;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MayaTest {

    @Test
    public void testPositiveLogin() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.demoblaze.com/");
        driver.manage().window().maximize();

        driver.findElement(By.id("login2")).click();
        driver.findElement(By.xpath("//*[@id='loginusername']")).sendKeys("mTest@gmail.com");
        driver.findElement(By.xpath("//*[@id='loginpassword']")).sendKeys("45784okng_75()");
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='logout2']")).isDisplayed(),
                "Success");

        driver.quit();
    }
}
