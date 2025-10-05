package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class JuliaShemTest {
    @Test
    public void FistHWTest(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.cssSelector("#app > div > div > div.home-body > div > div:nth-child(1)")).click();
        driver.findElement(By.xpath("//li[@id='item-0' and .//span[text()='Text Box']]")).click();
        driver.findElement(By.id("userName")).sendKeys("Julia");
        driver.findElement(By.id("submit")).click();
        WebElement name = driver.findElement(By.id("name"));

        Assert.assertEquals(name.getText(), "Name:Julia");

        driver.quit();
    }
    @Test
    public void checkBoxesTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement checkbox1 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]"));
        assertFalse(checkbox1.isSelected(), "Checkbox 1 is checked");
        checkbox1.click();
        assertTrue(checkbox1.isSelected(), "Checkbox 1 is not checked");

        WebElement checkbox2 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[2]"));
        assertTrue(checkbox2.isSelected(), "Checkbox 2 is checked");
        checkbox2.click();
        assertFalse(checkbox2.isSelected(), "Checkbox 2 is not clicking");

        driver.quit();
    }
}
