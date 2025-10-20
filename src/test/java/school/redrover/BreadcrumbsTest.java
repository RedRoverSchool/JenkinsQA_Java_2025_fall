package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class BreadcrumbsTest {

    @Test
    public void mikhailOtest() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.cssSelector("#header .shop-menu li > a[href='/login']")).click();

        driver.findElement(By.cssSelector("[data-qa='signup-name']")).sendKeys("John");
        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys("test_site123@gmail.com");

        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();

        Thread.sleep(1000);

        WebElement titlesRadioButton = driver.findElement(By.id("id_gender1"));
        titlesRadioButton.click();

        driver.findElement(By.cssSelector("[data-qa='password']")).sendKeys("28inT6yy@qKYDqz");

        WebElement dropdownDayOfBirth = driver.findElement(By.cssSelector("[data-qa='days'] option:nth-child(5)"));
        dropdownDayOfBirth.click();
        WebElement dropdownMonthOfBirth = driver.findElement(By.cssSelector("[data-qa='months'] option:nth-child(3)"));
        dropdownMonthOfBirth.click();
        WebElement dropdownYearOfBirth = driver.findElement(By.cssSelector("[data-qa='years'] option:nth-child(31)"));
        dropdownYearOfBirth.click();

        WebElement checkboxNewsletter = driver.findElement(By.id("newsletter"));
        checkboxNewsletter.click();
        WebElement checkboxOffers = driver.findElement(By.id("optin"));
        checkboxOffers.click();

        driver.findElement(By.cssSelector("[data-qa='first_name']")).sendKeys("John");
        driver.findElement(By.cssSelector("[data-qa='last_name']")).sendKeys("Smith");
        driver.findElement(By.cssSelector("[data-qa='address']")).sendKeys("Acme Innovations Inc., 123 Main Street Apt 4B, Anytown, NY 12345");
        driver.findElement(By.cssSelector("[data-qa='address2']")).sendKeys("Smallville, CA 90210");
        WebElement dropdownCountry = driver.findElement(By.cssSelector("#country > option:nth-child(2)"));
        dropdownCountry.click();
        driver.findElement(By.cssSelector("[data-qa='state']")).sendKeys("NY");
        driver.findElement(By.cssSelector("[data-qa='city']")).sendKeys("New York");
        driver.findElement(By.cssSelector("[data-qa='zipcode']")).sendKeys("12345");
        driver.findElement(By.cssSelector("[data-qa='mobile_number']")).sendKeys("+1 (123) 456-7890");

        driver.findElement(By.cssSelector("[data-qa='create-account']")).click();
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        Thread.sleep(1000);

        driver.findElement(By.cssSelector("#header .shop-menu li > a[href='/delete_account']")).click();
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        driver.quit();
    }

    @Test
    public void kirill_testcase2() {
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
        driver.findElement(By.cssSelector(".shop-menu  > ul > :nth-child(4)")).click();
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
    public void kirill_testcase3() {
        String namelogin = "newuser";
        String email = "newuser@supermail.com";
        String password = "dvdsgfsdfsd987654dgdfgdfgfsgd321";
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");
        driver.findElement(By.cssSelector(".shop-menu li > a[href=\"/login\"")).click();
        driver.findElement(By.cssSelector("[data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.cssSelector("[data-qa='login-password']")).sendKeys(password);
        driver.findElement(By.cssSelector("[data-qa='login-button']")).click();

        Assert.assertTrue(driver.findElement(By.cssSelector(".login-form > form > :nth-child(4)")).isDisplayed(), "Your email or password is incorrect!");
        driver.quit();
    }

    @Test
    public void kirill_testcase4() {
        String namelogin = "gygygy";
        String email = "gygygy@supermail.com";
        String password = "dvdsgfsdfsd987654321";
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");
        driver.findElement(By.cssSelector(".shop-menu li > a[href=\"/login\"")).click();
        driver.findElement(By.cssSelector("[data-qa='login-email']")).sendKeys(email);
        driver.findElement(By.cssSelector("[data-qa='login-password']")).sendKeys(password);
        driver.findElement(By.cssSelector("[data-qa='login-button']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector(".shop-menu > ul > :nth-child(10) > a > b")).getText(), namelogin);
        driver.findElement(By.cssSelector(".shop-menu  > ul > :nth-child(4)")).click();
        driver.quit();
    }

    @Test
    public void kirill_testcase5() {
        String namelogin = "gygygy";
        String email = "gygygy@supermail.com";

        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");
        WebElement homeButton = driver.findElement(By.cssSelector(".shop-menu li > a[href=\"/\"]"));
        String color = homeButton.getAttribute("style");  // color: orange;
        System.out.println(homeButton.getAttribute("style"));
        Assert.assertTrue(color.contains("color: orange;"), "Кнопка не оранжевая");

        driver.findElement(By.cssSelector(".shop-menu li > a[href=\"/login\"")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector(".signup-form h2")).getText(), "New User Signup!");

        driver.findElement(By.cssSelector("[data-qa='signup-name']")).sendKeys(namelogin);
        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys(email);
        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();

        System.out.println(driver.findElement(By.cssSelector(".signup-form > form > :nth-child(5)")).getText());
        Assert.assertEquals(driver.findElement(By.cssSelector(".signup-form > form > :nth-child(5)")).getText(), "Email Address already exist!");
        driver.quit();
    }

    @Test
    public void testSauseDemoVisualUser() {

        WebDriver drive = new ChromeDriver();
        drive.get("https://www.saucedemo.com/");

        Assert.assertEquals(drive.getTitle(), "Swag Labs");

        drive.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement userName = drive.findElement(By.id("user-name"));
        userName.sendKeys("visual_user");

        WebElement passw = drive.findElement(By.id("password"));
        passw.sendKeys("secret_sauce");

        WebElement submitButton = drive.findElement(By.name("login-button"));
        submitButton.click();

        WebElement page = drive.findElement(By.className("title"));

        Assert.assertEquals(page.getText(), "Products");

        drive.quit();
    }

    @Test
    public void testSauceDemoStandardUser() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.getTitle();

        WebElement usName = driver.findElement(By.id("user-name"));
        WebElement pass = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("login-button"));

        usName.sendKeys("standard_user");
        pass.sendKeys("secret_sauce");
        login.click();

        WebElement page = driver.findElement(By.className("title"));

        Assert.assertEquals(page.getText(), "Products");

        driver.quit();
    }

    @Test
    public void testNegativeLogin() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.getTitle();

        WebElement usName = driver.findElement(By.id("user-name"));
        WebElement pass = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("login-button"));

        usName.sendKeys("error_user");
        pass.sendKeys("passworddd");
        login.click();

        WebElement messageError =
                driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3"));

        Assert.assertEquals(messageError.getText(),
                "Epic sadface: Username and password do not match any user in this service");


    }
}
