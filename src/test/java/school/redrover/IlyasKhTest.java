package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IlyasKhTest {
    String testName = "TestQWERTY123";
    String testEmail = "QAZwsx123@test.com";
    String testPass = "qweRtyui123!@#";

    @Test
    public void testSeleniumRandom() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://automationexercise.com/products");

        Thread.sleep(1003);

        WebElement inputSearch = driver.findElement(By.xpath("/html/body/section[1]/div/input"));

        inputSearch.sendKeys("Sleeve");
        WebElement buttonSearch = driver.findElement(By.xpath("/html/body/section[1]/div/button"));
        buttonSearch.click();
        Thread.sleep(2000);

        WebElement findedSleeve = driver.findElement(By.xpath("/html/body/section[2]/div/div/div[2]/div/div[4]/div/div[2]/ul/li/a"));
        findedSleeve.click();
        Thread.sleep(3000);

        WebElement element = driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/h2"));
        Assert.assertEquals(element.getText(), "Half Sleeves Top Schiffli Detailing - Pink");

        driver.quit();
    }

    @Test
    public void  testRegisterUser() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com");
        Thread.sleep(1500);
        WebElement homeButton = driver.findElement(By.cssSelector("#header .shop-menu li > a[href=\"/\"]"));
        String color = homeButton.getCssValue("color");
        String expectedColor = "rgba(255, 165, 0, 1)";
        Assert.assertEquals(color, expectedColor, "Color of Home link is not orange");

        driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(4) > a")).click();
        WebElement signUp = driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > h2"));
        Assert.assertTrue(signUp.isDisplayed());

        // driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > form > input[type=text]:nth-child(2)")).sendKeys("testName123");
        // driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > form > input[type=email]:nth-child(3)")).sendKeys("MyFirsttest@email.com");
        // driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > form > button")).click();

        driver.findElement(By.cssSelector("[data-qa='signup-name']")).sendKeys(testName);
        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys(testEmail);
        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();
        Thread.sleep(1500);
        Assert.assertTrue(driver.findElement(By.cssSelector("div.login-form > h2 > b")).isDisplayed(),
                "Заголовок 'Enter Account Information' отсутствует на странице");
        //Fill details: Title, Name, Email, Password, Date of birth
        driver.findElement(By.cssSelector("#id_gender1")).click();
        driver.findElement(By.cssSelector("[data-qa='password']")).sendKeys(testPass);

        driver.findElement(By.cssSelector("[data-qa='days'] :nth-child(5)")).click();
        driver.findElement(By.cssSelector("[data-qa='months'] :nth-child(5)")).click();
        driver.findElement(By.cssSelector("[data-qa='years'] :nth-child(5)")).click();
        //Select checkbox 'Sign up for our newsletter!'
        driver.findElement(By.cssSelector("#newsletter")).click();
        //Select checkbox 'Receive special offers from our partners!'
        driver.findElement(By.cssSelector("#optin")).click();
        //Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
        driver.findElement(By.cssSelector("[data-qa='first_name']")).sendKeys("Ivan");
        driver.findElement(By.cssSelector("[data-qa='last_name']")).sendKeys("Ivanov");
        driver.findElement(By.cssSelector("[data-qa='address']")).sendKeys("Str.Pushkina, h.2, office 123");
        driver.findElement(By.cssSelector("[data-qa='country'] :nth-child(6)")).click();
        driver.findElement(By.cssSelector("[data-qa='state']")).sendKeys("Alabama");
        driver.findElement(By.cssSelector("[data-qa='city']")).sendKeys("Sin city");
        driver.findElement(By.cssSelector("[data-qa='zipcode']")).sendKeys("442446");
        driver.findElement(By.cssSelector("[data-qa='mobile_number']")).sendKeys("+799911144452");
        driver.findElement(By.cssSelector("[data-qa='create-account']")).click();
        //Verify that 'ACCOUNT CREATED!' is visible
        Assert.assertTrue(
                driver.findElement(By.cssSelector("[data-qa='account-created'] :nth-child(1)")).isDisplayed(),
                "Заголовок 'Account Created!' отсутствует на странице");
        //Click 'Delete Account' button
        //Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();
        driver.findElement(By.cssSelector("li a[href='/delete_account']")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector("[data-qa='account-deleted'] :nth-child(1)")).isDisplayed(),
                "Заголовок 'Account Deleted!' отсутствует");

        driver.quit();
    }
}
