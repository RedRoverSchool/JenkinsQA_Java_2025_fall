package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupUnitedByJavaTest {

    private static final String LOGIN = "admin@admin.com";
    private static final String PASSWORD = "admin123";

    @Test
    public void testDoubleClick() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get("https://demoqa.com/");

        WebElement elementsPage = driver.findElement(By.xpath("//h5[text()='Elements']"));
        elementsPage.click();
        WebElement buttons = driver.findElement(By.xpath("//span[@class='text' and text()='Buttons']"));
        buttons.click();

        WebElement doubleClickMe = driver.findElement(By.id("doubleClickBtn"));
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickMe);
        actions.perform();

        String doubleClickMessage = driver.findElement(By.id("doubleClickMessage")).getText();

        Assert.assertEquals(doubleClickMessage, "You have done a double click");

        driver.quit();
    }

    @Test
    public void testFirstTest() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");

        driver.findElement(By.xpath("/html/body/section[2]/div/div/div[2]/div[1]/div[4]/div/div[2]/ul/li/a")).click();
        driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/span/button")).click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("View Cart")).click();

        Assert.assertEquals(driver.findElement(By.xpath("/html/body/section/div/div[2]/table/tbody/tr/td[2]/h4/a")).getText(), "Sleeveless Dress");

        driver.quit();
    }

    @Test
    public void testPositiveLogin() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://qa-practice.netlify.app/auth_ecommerce");

        driver.findElement(By.name("emailAddress")).sendKeys(LOGIN);
        driver.findElement(By.name("password")).sendKeys(PASSWORD);
        driver.findElement(By.id("submitLoginBtn")).click();

        Assert.assertTrue(driver.findElement(By.id("logout")).isDisplayed(),
                "Logout button should be displayed after successful login");

        driver.quit();
        @Test
        public void testBackToMainPageLink() {
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.saucedemo.com/v1/inventory.html");
            driver.manage().window().maximize();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

            driver.findElement(By.xpath("//div[@class = 'inventory_item_name'][1]")).click();
            driver.findElement(By.xpath("//button[@class = 'inventory_details_back_button']")).click();

            Assert.assertEquals(driver.findElement(By.xpath("//div[@class = 'product_label']")).getText(),
                    "Products");

            driver.quit();
        }

        @Test
        public void testChangeButtonTextAtAdding() {
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.saucedemo.com/v1/inventory.html");
            driver.manage().window().maximize();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

            driver.findElement(By.xpath("(//button[@class='btn_primary btn_inventory'])[1]")).click();
            Assert.assertEquals(driver.findElement(By.xpath("(//button[@class='btn_secondary btn_inventory'])[1]"))
                    .getText(), "REMOVE");

            driver.quit();
        }

        @Test
        public void testChangeBasketCounterAtAdding() {
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.saucedemo.com/v1/inventory.html");
            driver.manage().window().maximize();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

            String basketCounterSelector = "//span[@class = 'fa-layers-counter shopping_cart_badge']";

            Assert.assertTrue(driver.findElements(By.xpath(basketCounterSelector)).isEmpty());

            driver.findElement(By.xpath("(//button[@class='btn_primary btn_inventory'])[1]")).click();
            Assert.assertEquals(driver.findElement(By.xpath(basketCounterSelector)).getText(), "1");

            driver.quit();
    }
}
