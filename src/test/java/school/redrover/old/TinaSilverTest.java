package school.redrover.old;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;

@Ignore
public class TinaSilverTest {
    @org.testng.annotations.Test
    public void testAutomate() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://practice-automation.com/javascript-delays/");

        WebElement button = driver.findElement(By.id("start"));
        button.click();

        Thread.sleep(10000);

        WebElement message = driver.findElement(By.id("delay"));

        Assert.assertEquals(message.getText(), "Liftoff!");

    }

    @org.testng.annotations.Test
    public void testAccordions() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://practice-automation.com/accordions/");

        WebElement accordion = driver.findElement(By.className("wp-block-coblocks-accordion-item__title"));
        accordion.click();

        WebElement message = driver.findElement(By.xpath("//*[@id=\"post-1261\"]/div/div[1]/div/details/div/p"));

        Assert.assertEquals(message.getText(), "This is an accordion item.");
    }

    @org.testng.annotations.Test
    public void testModal() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://practice-automation.com/modals/");

        WebElement button = driver.findElement(By.xpath("//*[@id=\"simpleModal\"]"));
        button.click();

        Thread.sleep(1000);
        WebElement message = driver.findElement(By.xpath("//*[@id=\"popmake-1318\"]/div[2]/p"));


        Assert.assertEquals(message.getText(), "Hi, I’m a simple modal.");

        WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"popmake-1318\"]/button"));
        closeButton.click();

    }

}

