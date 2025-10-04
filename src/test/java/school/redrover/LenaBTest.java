package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LenaBTest {
    @Test
    public void testFullNameBox(){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://demoqa.com/text-box");
        System.out.println(driver.getTitle());

        WebElement inputBox = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
        inputBox.sendKeys("Olena Name");

        WebElement submitButton = driver.findElement(By.cssSelector("button#submit"));
        submitButton.click();


        WebElement testOutputBox = driver.findElement(By.xpath("//*[@id=\"name\"]"));

        Assert.assertEquals(testOutputBox.getText(), "Name:Olena Name");
        driver.quit();

    }

}
