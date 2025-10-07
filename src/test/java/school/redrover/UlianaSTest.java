package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class UlianaSTest {

    @Test
    public void testHeaderMenuNavigation() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.mazda.md/");
        driver.manage().window().setSize(new Dimension(1920, 1080));

        List<WebElement> cookieBanner = driver.findElements(By.id("cookiescript_accept"));
        if (!cookieBanner.isEmpty()) {
            cookieBanner.get(0).click();
        }

        WebElement flagRu = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//img[@src='/images/Flags/ru-MD.gif']")));
        flagRu.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/ru"));

        WebElement models = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//a[normalize-space()='Модели'])[2]")));
        models.click();
        WebElement modelsPageTitle = driver.findElement(By.xpath("//div[@class='l-view__title']//h2"));
        Assert.assertTrue(modelsPageTitle.isDisplayed());

        WebElement offers = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@class, 'c-item__link') and contains(text(), 'ПРЕДЛОЖЕНИЯ')][1]"))
        );
        offers.click();
        WebElement offersPageTitle = driver.findElement(By.xpath("//*[@id='Form']/header/section[2]/div/div/div/div/ul/li/a"));
        Assert.assertTrue(offersPageTitle.isDisplayed());

        WebElement logo = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("dnn_dnnLOGOHeader_imgLogo")));
        logo.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/ru/home"));

        driver.quit();
    }

    @Test
    public void testToolsQATitle() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement title = driver.findElement(By.xpath("//header/a/img"));

        Assert.assertTrue(title.isDisplayed());

        driver.quit();
    }
}
