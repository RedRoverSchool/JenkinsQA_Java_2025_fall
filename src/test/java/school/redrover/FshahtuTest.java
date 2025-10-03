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

        driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(4) > a")).click();

        Assert.assertEquals(driver.getTitle(), "Automation Exercise - Signup / Login");

        String namelogin = "Kirill Bohenek";
        WebElement loginname = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/input[2]"));
        loginname.sendKeys(namelogin);
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/input[3]")).sendKeys("kirill.bohenek@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/button")).click();
        Assert.assertEquals(driver.getTitle(), "Automation Exercise - Signup");

        driver.findElement(By.xpath("//*[@id=\"id_gender1\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("1234567890");
        driver.findElement(By.xpath("//*[@id=\"days\"]/option[20]")).click();
        driver.findElement(By.xpath("//*[@id=\"months\"]/option[9]")).click();
        driver.findElement(By.xpath("//*[@id=\"years\"]/option[41]")).click();
        driver.findElement(By.xpath("//*[@id=\"newsletter\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"optin\"]")).click();

        WebElement name = driver.findElement(By.xpath("//*[@id=\"first_name\"]"));
        name.sendKeys("Kirill");
        WebElement surname = driver.findElement(By.xpath("//*[@id=\"last_name\"]"));
        surname.sendKeys("Bohenek");
        driver.findElement(By.xpath("//*[@id=\"company\"]")).sendKeys("Unknown");
        driver.findElement(By.xpath("//*[@id=\"address1\"]")).sendKeys("Street, 33, 12345, Unknown");
        driver.findElement(By.xpath("//*[@id=\"address2\"]")).sendKeys("Null");
        driver.findElement(By.xpath("//*[@id=\"country\"]/option[5]")).click();
        driver.findElement(By.xpath("//*[@id=\"state\"]")).sendKeys("North");
        driver.findElement(By.xpath("//*[@id=\"city\"]")).sendKeys("Capital");
        driver.findElement(By.xpath("//*[@id=\"zipcode\"]")).sendKeys("12345");
        driver.findElement(By.xpath("//*[@id=\"mobile_number\"]")).sendKeys("1234567890");
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div/div[1]/form/button")).click();

        WebElement account = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div/h2/b"));
        Assert.assertEquals(account.getText(), "ACCOUNT CREATED!"); // <b>Account Created!</b>

        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div/div/a")).click();

        WebElement checkname = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[10]/a/b"));
        System.out.println(checkname.getText());

        Assert.assertEquals(checkname.getText(), namelogin);

        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div/div[2]/div/ul/li[5]/a")).click();
        WebElement logout = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div/h2/b"));
        Assert.assertEquals(logout.getText(), "ACCOUNT DELETED!"); // <b>Account Deleted!</b>
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div/div/a")).click();

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
