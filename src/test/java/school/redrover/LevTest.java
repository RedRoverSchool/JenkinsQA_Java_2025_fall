package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class LevTest {

    @Test
    public void myAssert() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/text-box");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.id("userName"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Lev Eydelkind");
        //submitButton.click();

        //WebElement message = driver.findElement(By.id("message"));
        //String txt = message.getText();

        //Assert.assertEquals(txt, "Name:Lev Eydelkind");


        //driver.quit();

    }
}
