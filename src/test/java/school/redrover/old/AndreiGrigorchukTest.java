package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class AndreiGrigorchukTest {

    @Test
    public void addTest() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement addToCard = driver.findElement(By.xpath("/html/body/section[2]/div/div/div[2]/div[1]/div[2]/div/div[1]/div[1]/a"));
        addToCard.click();


        WebElement addedContinueShopping = driver.findElement(By.xpath("//*[@id=\"cartModal\"]/div/div/div[3]/button"));
        addedContinueShopping.click();

        WebElement card = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[3]/a"));
        card.click();

        WebElement delete = driver.findElement(By.xpath("//*[@id=\"product-1\"]/td[6]/a"));
        delete.click();

        driver.quit();
    }

}
