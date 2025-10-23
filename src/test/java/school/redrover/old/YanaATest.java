package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class YanaATest {
    WebDriver driver;
    private final static String mainPageURL = "https://automationexercise.com/";
    private final static String mainLogoCssSelector = "a img[alt='Website for automation practice']";
    private final static String signUpLoginButtonCssSelector = "li a[href='/login']"; // убрать
    String testName = "TestUser";
    String testEmail = "testuseremail@themail.com";
    String testPass = "TestPass123!";

    @Test
    void testCase1RegisterUser() throws InterruptedException {

        driver = new ChromeDriver();
        driver.get(mainPageURL);
        Thread.sleep(1500);

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement cookieBanner = shortWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".fc-consent-root")));

            if (cookieBanner.isDisplayed()) {
                WebElement acceptCookiesButton = driver.findElement(
                        By.cssSelector(".fc-button.fc-cta-consent.fc-primary-button"));
                acceptCookiesButton.click();
                System.out.println("Cookie banner detected and accepted.");
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            System.out.println("No cookie banner found, continue test.");
        }
        WebElement homeButton = driver.findElement(By.cssSelector("#header .shop-menu li > a[href=\"/\"]"));
        String color = homeButton.getAttribute("style");  // color: orange;
        Assert.assertTrue(color.contains("color: orange;"), "Кнопка не оранжевая");

        driver.findElement(By.cssSelector(signUpLoginButtonCssSelector)).click();
        Thread.sleep(1500);
        WebElement signUpText = driver.findElement(By.cssSelector(".signup-form h2"));
        Assert.assertTrue(signUpText.isDisplayed(), "Заголовок 'New User Signup!' отсутствует на странице");
        Assert.assertEquals(signUpText.getText(),
                "New User Signup!",
                "'New User Signup!' isn't visible");

        driver.findElement(By.cssSelector("[data-qa='signup-name']")).sendKeys(testName);
        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys(testEmail);
        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();
        Thread.sleep(1500);
        Assert.assertTrue(driver.
                        findElement(By.cssSelector("div.login-form > h2 > b")).isDisplayed(),
                "Заголовок 'Enter Account Information' отсутствует на странице");

        driver.findElement(By.id("id_gender2")).click();
        driver.findElement(By.id("password")).sendKeys(testPass);
        driver.findElement(By.cssSelector("[data-qa='days'] :nth-child(5)")).click();
        driver.findElement(By.cssSelector("[data-qa='months'] option:nth-child(3)")).click();
        driver.findElement(By.cssSelector("[data-qa='years'] option:nth-child(31)")).click();

        driver.findElement(By.id("newsletter")).click();

        driver.findElement(By.id("optin")).click();

        driver.findElement(By.cssSelector("[data-qa='first_name']")).sendKeys("John");
        driver.findElement(By.cssSelector("[data-qa='last_name']")).sendKeys("Smith");
        driver.findElement(By.cssSelector("[data-qa='address']")).sendKeys("Acme Innovations Inc., 123 Main Street Apt 4B, Anytown, NY 12345");
        driver.findElement(By.cssSelector("[data-qa='address2']")).sendKeys("Smallville, CA 90210");
        driver.findElement(By.cssSelector("[data-qa='country']"));
        driver.findElement(By.cssSelector("#country > option:nth-child(2)")).click();
        driver.findElement(By.cssSelector("[data-qa='state']")).sendKeys("NY");
        driver.findElement(By.cssSelector("[data-qa='city']")).sendKeys("New York");
        driver.findElement(By.cssSelector("[data-qa='zipcode']")).sendKeys("12345");
        driver.findElement(By.cssSelector("[data-qa='mobile_number']")).sendKeys("+1 (123) 456-7890");

        driver.findElement(By.cssSelector("[data-qa='create-account']")).click();

        WebElement accountCreated = driver.findElement(By.cssSelector("[data-qa='account-created']"));
        Assert.assertEquals(accountCreated.getText().toLowerCase(), "account created!");

        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();
        Thread.sleep(1000);

        WebElement loggedInAs = driver.findElement(By.cssSelector("#header .shop-menu li > a > b"));
        Assert.assertEquals(loggedInAs.getText(), testName);

        driver.findElement(By.cssSelector("#header .shop-menu li > a[href=\"/delete_account\"]")).click();

        WebElement accountDeleted = driver.findElement(By.cssSelector("[data-qa='account-deleted']"));
        Assert.assertEquals(accountDeleted.getText().toLowerCase(), "account deleted!");
        driver.findElement(By.cssSelector("[data-qa='continue-button']")).click();

        driver.quit();

    }
}