package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ARudakTest {

    @Test
    public void demoQATest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/elements");

        String title = driver.getTitle();
        assertEquals(title, "DEMOQA");

        WebElement textBoxButton = driver.findElement(By.id("item-0"));
        textBoxButton.click();

        WebElement textBox = driver.findElement(By.className("text-center"));
        assertEquals("Text Box", textBox.getText());

        driver.quit();
    }
}
