package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;



public class IraTest {


    @Test
    public void buyBuketTest()   {
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@class='submit-button btn_action']")).click();
        WebElement header = driver.findElement(By.xpath("//div[@class='header_label']"));
        Assert.assertEquals(header.getText(), "Swag Labs");
    }

    @Test
    public void addToCardTest(){
        WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.xpath("//input[@class='submit-button btn_action']")).click();
            WebElement header = driver.findElement(By.xpath("//div[@class='header_label']"));
        Assert.assertEquals(header.getText(), "Swag Labs");
        driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']")).click();
        driver.findElement(By.id("add-to-cart")).click();
        String basket = driver.findElement(By.cssSelector(".shopping_cart_badge")).getText();
        Assert.assertEquals(basket,"1");
        }


        @Test
    public void logoutTest()  {
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.get("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.xpath("//input[@class='submit-button btn_action']")).click();
            WebElement header = driver.findElement(By.xpath("//div[@class='header_label']"));
            Assert.assertEquals(header.getText(), "Swag Labs");
            driver.findElement(By.id("react-burger-menu-btn")).click();
            driver.findElement(By.id("logout_sidebar_link")).click();
                driver.findElement(By.id("logout_sidebar_link")).click();
            Assert.assertTrue(driver.findElement(By.id("user-name")).getAttribute("value").isEmpty());
            Assert.assertTrue(driver.findElement(By.id("user-name")).getAttribute("placeholder").equals("Username"));
        }
// The commented out code is redundant. Try to compile (run) your code prior creating a commit.
        //package jenkinsTest;

//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import java.time.Duration;

    public class FirstTestJenkins {

        @Test
        public void logintest(){
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get("http://localhost:8081/");
            WebElement login = driver.findElement(By.name("j_username"));
            WebElement password = driver.findElement(By.name("j_password"));
            WebElement singIn = driver.findElement(By.name("Submit"));
            login.sendKeys("admin");
            password.sendKeys("admin");
            singIn.click();
            WebElement wellCome = driver.findElement(By.xpath("//h1"));
            Assert.assertEquals(wellCome.getText(),"Добро пожаловать в Jenkins!");
        }
        @Test
        public void logintestInvalidCredentions(){
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get("http://localhost:8081/");
            WebElement login = driver.findElement(By.name("j_username"));
            WebElement password = driver.findElement(By.name("j_password"));
            WebElement singIn = driver.findElement(By.name("Submit"));
            login.sendKeys("admin1");
            password.sendKeys("admin1");
            singIn.click();
            WebElement error = driver.findElement(By.cssSelector(".app-sign-in-register__error"));
            Assert.assertEquals(error.getText(),"Invalid username or password");
            WebElement login2 = driver.findElement(By.name("j_username"));
            Assert.assertTrue(login2.getAttribute("class").contains("jenkins-input--error"));
        }
    }

}
