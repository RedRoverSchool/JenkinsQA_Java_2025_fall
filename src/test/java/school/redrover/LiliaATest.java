package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LiliaATest {

    @Test
    public void testTextBox() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/");

        driver.findElement(By.cssSelector(".top-card:first-child")).click();
        driver.findElement(By.cssSelector(".menu-list li.btn:nth-child(1)")).click();

        driver.findElement(By.id("userName")).sendKeys("Lilia");
        driver.findElement(By.id("userEmail")).sendKeys("lilia@gmail.com");
        driver.findElement(By.id("currentAddress")).sendKeys("Current Address");
        driver.findElement(By.id("permanentAddress")).sendKeys("Permanent Address");
        driver.findElement(By.id("submit")).click();

        Assert.assertEquals(driver.findElement(By.className("text-center")).getText(), "Text Box");
        Assert.assertEquals(driver.findElement(By.id("name")).getText(), "Name:Lilia");
        Assert.assertEquals(driver.findElement(By.id("email")).getText(), "Email:lilia@gmail.com");
        Assert.assertEquals(driver.findElement(By.cssSelector("p#currentAddress")).getText(), "Current Address :Current Address");
        Assert.assertEquals(driver.findElement(By.cssSelector("p#permanentAddress")).getText(), "Permananet Address :Permanent Address");

        driver.close();
    }

}
