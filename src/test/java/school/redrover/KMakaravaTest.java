package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class KMakaravaTest {
    @Test
    public void testSelenium(){
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        Assert.assertEquals(message.getText(), "Received!");
        driver.quit();
    }

    @Test
    public void testSwagLabs() {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.name("login-button")).click();
        Assert.assertEquals(driver.getTitle(), "Swag Labs");

        driver.quit();
    }

    @Test
    public void testHerokuapp1(){
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://the-internet.herokuapp.com/");
        driver.findElement(By.linkText("Add/Remove Elements")).click();
        WebElement title = driver.findElement(By.cssSelector("h3"));
        Assert.assertEquals(title.getText(), "Add/Remove Elements");
        driver.quit();
    }

    @Test
    public void testHerokuapp2(){
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://the-internet.herokuapp.com/add_remove_elements/");
        driver.findElement(By.tagName("button")).click();
        List<WebElement> elements = driver.findElements(By.xpath("//button[@class='added-manually']"));
        Assert.assertEquals(elements.size(), 1);
        driver.quit();
    }
}
