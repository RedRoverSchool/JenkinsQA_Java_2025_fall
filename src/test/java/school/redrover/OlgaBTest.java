package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class OlgaBTest {

    private WebDriver driver;
    private static final String BASE_URL = "http://automationexercise.com";

    @BeforeSuite
    public void setDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterSuite
    public void closeDriver(){
        driver.quit();
    }

    @Test
    public void testVerifyDetailOfProductVisible() {
        WebElement consentButton = driver.findElement(By.xpath("//button/*[contains(text(),'Consent')]"));
        consentButton.click();

        String titleHomePage = driver.getTitle();
        Assert.assertEquals(titleHomePage, "Automation Exercise");

        WebElement productsButton = driver.findElement(By.xpath("//a[@href='/products']"));
        productsButton.click();

        String titleProductsPage = driver.getTitle();
        Assert.assertEquals(titleProductsPage, "Automation Exercise - All Products");

        WebElement headerAllProducts = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        String headerAllProductsText = headerAllProducts.getText();
        Assert.assertTrue(headerAllProducts.isDisplayed());
        Assert.assertEquals(headerAllProductsText, "ALL PRODUCTS");

        WebElement productList = driver.findElement(By.xpath("//div[@class='features_items']"));
        Assert.assertTrue(productList.isDisplayed());

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        WebElement firstViewProductLink = driver.findElement(By.xpath("(//div[@class='choose']//a)[1]"));
        firstViewProductLink.click();

        WebElement nameProduct = driver.findElement(By.xpath("//div[@class='product-information']/h2"));
        Assert.assertTrue(nameProduct.isDisplayed());
    }
}