package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupJavaCrackersRedroverTest {

    public static void scrollAndClick(WebDriver driver, Actions actions, WebElement element) {
        actions.scrollToElement(element).perform();
        actions.scrollByAmount(0, 200).perform();
        if (element.isDisplayed() && element.isEnabled()) {
            actions.moveToElement(element).click().perform();
        }
    }

    @Test
    public void testAddToCart() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com");

        Thread.sleep(200);
        WebElement consentButton = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[2]/button[1]/p"));
        consentButton.click();

        WebElement productLink = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[2]/a"));
        productLink.click();

        WebElement womenCategory = driver.findElement(By.xpath("//*[@id=\"accordian\"]/div[1]/div[1]/h4/a"));
        womenCategory.click();
        Thread.sleep(100);

        WebElement dressCategory = driver.findElement(By.xpath("//*[@id=\"Women\"]/div/ul/li[1]/a"));
        dressCategory.click();

        WebElement product1 = driver.findElement(By.xpath("/html/body/section/div/div[2]/div[2]/div/div[2]/div/div[1]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(product1).perform();

        WebElement addToCartButton = driver.findElement(By.xpath("/html/body/section/div/div[2]/div[2]/div/div[2]/div/div[1]/div[2]/div/a"));
        Thread.sleep(100);
        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        addToCartButton.click();
        Thread.sleep(300);
        WebElement message = driver.findElement(By.xpath("//*[@id=\"cartModal\"]/div/div/div[2]/p[1]"));
        Assert.assertEquals(message.getText(), "Your product has been added to cart.");

        driver.quit();
    }

    @Test
    public void testRecaptchaValidationMessage() {
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);

        driver.get("https://demoqa.com/");

        WebElement appPage = driver.findElement(By.xpath("//h5[text()='Book Store Application']"));
        scrollAndClick(driver, actions, appPage);

        WebElement login = driver.findElement(By.xpath("//span[text()='Login']"));
        scrollAndClick(driver, actions, login);

        WebElement newUser = driver.findElement(By.id("newUser"));
        scrollAndClick(driver, actions, newUser);

        driver.findElement(By.id("firstname")).sendKeys("Roman");
        driver.findElement(By.id("lastname")).sendKeys("T");
        driver.findElement(By.id("userName")).sendKeys("romant");
        driver.findElement(By.id("password")).sendKeys("123456");

        WebElement register = driver.findElement(By.id("register"));
        scrollAndClick(driver, actions, register);

        WebElement message = driver.findElement(By.id("output"));
        Assert.assertTrue(message.isDisplayed(), "Please verify reCaptcha to register!");

        driver.quit();
    }
}
