package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ElenaGTest {
    private WebDriver driver;
    private static final String BASE_URL = "https://www.saucedemo.com";

    @BeforeMethod
    public void setup() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Test
    public void loginStandartUserTest() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Assert.assertEquals(driver.getTitle(), "Swag Labs");

        WebElement titleProductPage = driver.findElement(By.xpath(".//div[@id=\"header_container\"]/div[2]/span"));
        Assert.assertEquals(titleProductPage.getText(), "Products");
    }

    @Test
    public void correctProductNameTest() throws InterruptedException {

        loginStandartUserTest();

        WebElement productName = driver.findElement(By.xpath(".//*[@id=\"item_2_title_link\"]/div"));
        String nameByProductPage = productName.getText();

        productName.click();

        WebElement backToProductsButton = driver.findElement(By.id("back-to-products"));
        Assert.assertTrue(backToProductsButton.isDisplayed());
        Assert.assertEquals(backToProductsButton.getText(), "Back to products");

        WebElement thisProductName = driver.findElement(By.xpath(".//div[@class=\"inventory_details_desc_container\"]/div"));
        String nameByItemPage = thisProductName.getText();

        Assert.assertEquals(nameByItemPage,nameByProductPage);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}