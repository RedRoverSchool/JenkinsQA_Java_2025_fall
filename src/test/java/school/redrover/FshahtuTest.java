package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FshahtuTest {
    @Test
    public void testautomationexercise() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");
        WebElement homeButton = driver.findElement(By.cssSelector(".shop-menu li > a[href=\"/\"]"));
        //подумать
        String color = homeButton.getAttribute("style");  // color: orange;
        System.out.println(homeButton.getAttribute("style"));
        Assert.assertTrue(color.contains("color: orange;"), "Кнопка не оранжевая");

        driver.findElement(By.cssSelector(".shop-menu li > a[href=\"/login\"")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector(".signup-form h2")).getText(), "New User Signup!");

        String namelogin = "newuser";
        WebElement loginname = driver.findElement(By.cssSelector("[data-qa='signup-name']"));
        loginname.sendKeys(namelogin);
        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys("newuser@supermail.com");
        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("div.login-form > h2 > b")).getText().toLowerCase(), "enter account information");

        driver.findElement(By.id("id_gender1")).click();
        driver.findElement(By.id("password")).sendKeys("dvdsgfsdfsd987654321");
        driver.findElement(By.cssSelector("[data-qa='days'] :nth-child(19)")).click();
        driver.findElement(By.cssSelector("[data-qa='months'] :nth-child(8)")).click();
        driver.findElement(By.cssSelector("[data-qa='years'] :nth-child(40)")).click();
        driver.findElement(By.id("newsletter")).click();

        driver.findElement((By.id("first_name"))).sendKeys("User");
        driver.findElement((By.id("last_name"))).sendKeys("User");
        driver.findElement((By.id("company"))).sendKeys("Company");
        driver.findElement((By.id("address1"))).sendKeys("Street, 33, 12345, Unknown");
        driver.findElement((By.id("address2"))).sendKeys("Unknown");
        driver.findElement(By.cssSelector("[data-qa='country'] :nth-child(3)")).click();
        driver.findElement((By.id("state"))).sendKeys("Unknown");
        driver.findElement((By.id("city"))).sendKeys("Unknown");
        driver.findElement((By.id("zipcode"))).sendKeys("12345");
        driver.findElement((By.id("mobile_number"))).sendKeys("+12322245");
        driver.findElement(By.cssSelector("[data-qa='create-account']")).click();

        Assert.assertEquals(driver.findElement((By.cssSelector("[data-qa='account-created'] :nth-child(1)"))).getText(), "ACCOUNT CREATED!");

        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector(".shop-menu > ul > :nth-child(10) > a > b")).getText(), namelogin);
        driver.findElement(By.cssSelector(".shop-menu > ul > :nth-child(5)")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("[data-qa='account-deleted'] > b")).getText(), "ACCOUNT DELETED!");
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        driver.quit();
    }

    @Test
    public void testautomationexercise_case2() {
        String namelogin = "newuser";
        String email = "newuser@supermail.com";
        String password = "dvdsgfsdfsd987654321";
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");
        WebElement homeButton = driver.findElement(By.cssSelector(".shop-menu li > a[href=\"/\"]"));
        String color = homeButton.getAttribute("style");  // color: orange;
        System.out.println(homeButton.getAttribute("style"));
        Assert.assertTrue(color.contains("color: orange;"), "Кнопка не оранжевая");
        driver.findElement(By.cssSelector(".shop-menu li > a[href=\"/login\"")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector(".signup-form h2")).getText(), "New User Signup!");
// make account
        driver.findElement(By.cssSelector(".shop-menu li > a[href=\"/login\"")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector(".signup-form h2")).getText(), "New User Signup!");
        WebElement loginname = driver.findElement(By.cssSelector("[data-qa='signup-name']"));
        loginname.sendKeys(namelogin);
        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("div.login-form > h2 > b")).getText().toLowerCase(), "enter account information");
        driver.findElement(By.id("id_gender1")).click();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("[data-qa='days'] :nth-child(19)")).click();
        driver.findElement(By.cssSelector("[data-qa='months'] :nth-child(8)")).click();
        driver.findElement(By.cssSelector("[data-qa='years'] :nth-child(40)")).click();
        driver.findElement(By.id("newsletter")).click();
        driver.findElement((By.id("first_name"))).sendKeys("User");
        driver.findElement((By.id("last_name"))).sendKeys("User");
        driver.findElement((By.id("company"))).sendKeys("Company");
        driver.findElement((By.id("address1"))).sendKeys("Street, 33, 12345, Unknown");
        driver.findElement((By.id("address2"))).sendKeys("Unknown");
        driver.findElement(By.cssSelector("[data-qa='country'] :nth-child(3)")).click();
        driver.findElement((By.id("state"))).sendKeys("Unknown");
        driver.findElement((By.id("city"))).sendKeys("Unknown");
        driver.findElement((By.id("zipcode"))).sendKeys("12345");
        driver.findElement((By.id("mobile_number"))).sendKeys("+12322245");
        driver.findElement(By.cssSelector("[data-qa='create-account']")).click();
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();
        //logout
        driver.findElement(By.cssSelector(".shop-menu  > ul > :nth-child(4)")).click();
//login
        driver.findElement(By.cssSelector(".shop-menu li > a[href=\"/login\"")).click();
        driver.findElement(By.cssSelector("[data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.cssSelector("[data-qa='login-password']")).sendKeys(password);
        driver.findElement(By.cssSelector("[data-qa='login-button']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector(".shop-menu > ul > :nth-child(10) > a > b")).getText(), namelogin);
        driver.findElement(By.cssSelector(".shop-menu > ul > :nth-child(5)")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("[data-qa='account-deleted'] > b")).getText(), "ACCOUNT DELETED!");
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        driver.quit();
    }

    @Test
    public void testDelenium() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://195.91.225.98:81/login.php");
        driver.getTitle();
        WebElement textBox = driver.findElement(By.id("username"));
        textBox.sendKeys("guest");
        WebElement textpass = driver.findElement(By.id("password"));
        textpass.sendKeys("guest");
        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"pagecontent\"]/div/div/div/div/input[3]"));
        submitButton.click();

        WebElement test = driver.findElement(By.cssSelector("#system"));

        Assert.assertEquals(test.getText(), "HP ProLiant MicroServer");

        WebElement exit = driver.findElement(By.xpath("//*[@id=\"navhdr\"]/ul/li/ul/li[2]/a"));
        exit.click();
        exit = driver.findElement(By.xpath("//*[@id=\"navhdr\"]/ul/li/ul/li[2]/ul/li[3]/a"));
        exit.click();

        driver.quit();
    }

}
