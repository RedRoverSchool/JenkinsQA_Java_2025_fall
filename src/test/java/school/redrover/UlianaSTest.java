package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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

    @Test
    public void testTextBox() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/");

        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.findElement(By.xpath("//div[@class='card-body']/h5[text()='Elements']")).click();
        driver.findElement(By.xpath("//span[text()='Text Box']")).click();

        WebElement inputFullName = driver.findElement(By.xpath("//input[@placeholder='Full Name']"));
        inputFullName.sendKeys("Scolnicova Uliana");

        WebElement inputEmail = driver.findElement(By.xpath("//input[@placeholder='name@example.com']"));
        inputEmail.sendKeys("uliana@gmail.com");

        WebElement inputCurrentAddress = driver.findElement(By.xpath("//textarea[@placeholder='Current Address']"));
        inputCurrentAddress.sendKeys("Chisinau");

        WebElement inputPermanentAddress = driver.findElement(By.xpath("//textarea[@id='permanentAddress']"));
        inputPermanentAddress.sendKeys("Chisinau");

        WebElement buttonSubmit = driver.findElement(By.xpath("//button[@id='submit']"));
        new Actions(driver)
                .scrollToElement(buttonSubmit)
                .perform();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonSubmit);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("output")));

        Assert.assertEquals(
                driver.findElement(By.xpath("//p[@id='name']")).getText(), "Name:Scolnicova Uliana"
        );
        Assert.assertEquals(
                driver.findElement(By.xpath("//p[@id='email']")).getText(), "Email:uliana@gmail.com"
        );
        Assert.assertEquals(
                driver.findElement(By.xpath("//p[@id='currentAddress']")).getText(), "Current Address :Chisinau"
        );
        Assert.assertEquals(
                driver.findElement(By.xpath("//p[@id='permanentAddress']")).getText(), "Permananet Address :Chisinau"
        );

        driver.quit();

    }

    @Test
    public void testRadioButton() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/elements");

        driver.manage().window().setSize(new Dimension(1920, 1080));

        WebElement radioButtonMenu = driver.findElement(By.xpath("//span[text()='Radio Button']"));
        radioButtonMenu.click();

        WebElement yesRadioButton = driver.findElement(By.xpath("//label[@for='yesRadio']"));
        yesRadioButton.click();

        WebElement result = driver.findElement(By.xpath("//p[@class='mt-3']"));
        Assert.assertEquals(result.getText(), "You have selected Yes");

        driver.quit();
    }
}
