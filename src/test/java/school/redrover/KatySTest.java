package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class KatySTest {
    @Test
    public void test() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.publix.com/");

        WebElement searchBar = driver.findElement(By.name("searchTerm"));
        searchBar.sendKeys("harissa", Keys.ENTER);
//        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
//        submitButton.click();

        WebElement linkName = driver.findElement(By.xpath("//*[@id=\"main\"]/div[1]/div/div[2]/div[2]/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div[1]/div/a"));
        linkName.getText().contains("harissa");

        driver.quit();
    }
}
