package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class HannaKTest {
    private  WebDriver driver;
    private WebDriverWait wait;

    //@BeforeClass
    public  void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.get("https://www.automationexercise.com/products");
    }

    //закрытие окна Куки
    public void closeCookieConsentIfPresent(WebDriver driver) {
        try {
            WebElement consent = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[2]/button[1]/p")
            ));
            consent.click();
        } catch (Exception e) {
        }
    }

    @Test(priority = 1)
    public void testViewFirstProduct() {
        closeCookieConsentIfPresent(driver);
        WebElement viewProductButton = driver.findElement(By.xpath("/html/body/section[2]/div/div/div[2]/div/div[2]/div/div[2]/ul/li/a"));
        viewProductButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        String expectedUrl = "https://www.automationexercise.com/product_details/1";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl, actualUrl, "The actual URL does not match the expected one!");

        Assert.assertEquals(driver.findElement
                (By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/h2"))
                .getText(), "Blue Top");
    }

    //прокрутка страницы вниз к полю
    public void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void closeAdBannerIfPresent() {
        try {
            // Ждём, пока баннер появится и станет кликабельным - закрываем
            WebElement adClose = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/ins[2]/div[1]//ins")
            ));
            adClose.click();
            System.out.println("Banner closed");
        } catch (Exception e) {
            System.out.println("Not available or closed");
        }
    }

    @Test(priority = 2)
    public  void testAddReviewComment() {
       closeAdBannerIfPresent();

        WebElement fieldYourName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='name']"))
        );
        scrollToElement(driver, fieldYourName);
        fieldYourName.sendKeys("User1");

        WebElement fieldEmailAdress = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"email\"]"))
        );
        scrollToElement(driver, fieldEmailAdress);
        fieldEmailAdress.sendKeys("testuser1@test.com");

        WebElement fieldAddReview = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"review\"]"))
        );
        scrollToElement(driver, fieldAddReview);
        fieldAddReview.sendKeys("This is the text of a test user.");

        WebElement submitButton = driver.findElement(By.id("button-review"));
        submitButton.click();

        By messageLocator = By.xpath("//*[@id='review-section']/div/div/span");
        WebElement toastMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(messageLocator)
        );

        String actualText = toastMessage.getText();
        String expectedText = "Thank you for your review.";

        Assert.assertEquals(actualText, expectedText, "The message does not meet expectations.");
    }

    //@AfterClass
    public void tearDown() {
        driver.quit();
    }
}
