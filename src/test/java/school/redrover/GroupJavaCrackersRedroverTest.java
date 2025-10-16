package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupJavaCrackersRedroverTest {

    public static void scrollAndClick(Actions actions, WebElement element) {
        actions.scrollToElement(element).perform();
        actions.scrollByAmount(0, 200).perform();
        if (element.isDisplayed() && element.isEnabled()) {
            actions.moveToElement(element).click().perform();
        }
    }

    @Test
    public void testAddToCart() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);

        driver.get("https://automationexercise.com");
        Thread.sleep(200);

        driver.findElement(By.xpath("//button[@aria-label='Consent']")).click();

        driver.findElement(By.xpath("//a[@href='/products']")).click();
        driver.findElement(By.xpath("//a[@href='#Women']")).click();
        Thread.sleep(100);
        driver.findElement(By.xpath("//a[@href='/category_products/1']")).click();
        scrollAndClick(actions, driver.findElement(By.xpath("//a[@href='/product_details/3']")));
        Thread.sleep(200);
        driver.findElement(By.xpath("//button[@type='button']")).click();
        Thread.sleep(300);

        WebElement message = driver.findElement(By.xpath("//div[@class='modal-body']/p[1]"));
        Assert.assertEquals(message.getText(), "Your product has been added to cart.");

        driver.quit();
    }

    @Test
    public void testRecaptchaValidationMessage() {
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);

        driver.get("https://demoqa.com/");

        WebElement appPage = driver.findElement(By.xpath("//h5[text()='Book Store Application']"));
        scrollAndClick(actions, appPage);

        WebElement login = driver.findElement(By.xpath("//span[text()='Login']"));
        scrollAndClick(actions, login);

        WebElement newUser = driver.findElement(By.id("newUser"));
        scrollAndClick(actions, newUser);

        driver.findElement(By.id("firstname")).sendKeys("Roman");
        driver.findElement(By.id("lastname")).sendKeys("T");
        driver.findElement(By.id("userName")).sendKeys("romant");
        driver.findElement(By.id("password")).sendKeys("123456");

        WebElement register = driver.findElement(By.id("register"));
        scrollAndClick(actions, register);

        WebElement message = driver.findElement(By.id("output"));
        Assert.assertTrue(message.isDisplayed(), "Please verify reCaptcha to register!");

        driver.quit();
    }
}
