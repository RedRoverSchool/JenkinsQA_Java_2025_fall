package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class PetriukA {

    WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void get_cards_by_selector() throws InterruptedException {
        driver.get("https://demoqa.com/");

        //получение списка элементов по селектору
        List<WebElement> elementsBySelector = driver.findElements(By.cssSelector(".card.mt-4.top-card"));

        assertEquals(elementsBySelector.size(), 6);
    }

    @Test
    public void get_cards_by_xPath() throws InterruptedException {
        driver.get("https://demoqa.com/");

        //получение списка элементов по xPath
        List<WebElement> elementsByXPath = driver.findElements(By.xpath("//div[@class='card mt-4 top-card']"));

        assertEquals(elementsByXPath.size(), 6);
    }

    @AfterTest
    public void closeAll() {
        driver.quit();
    }

}
