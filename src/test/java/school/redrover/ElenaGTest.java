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
        private static final String PRODUCT_PAGE_URL = "https://www.saucedemo.com/inventory.html";

        private final By titleProductPage = By.xpath(".//div[@id=\"header_container\"]/div[2]/span");
        private final By backToProductsButton = By.id("back-to-products");
        private final By productName = By.xpath(".//*[@id=\"item_2_title_link\"]/div");
        private final By productPrice = By.xpath(".//*[@id=\"item_2_title_link\"]/ancestor::div[@class=\"inventory_item_description\"]//div[@class=\"pricebar\"]/div");
        private final By productImage = By.xpath(".//*[@id=\"item_2_img_link\"]/img");

        @BeforeMethod
        public void setup() {
            driver = new EdgeDriver();
            driver.manage().window().maximize();
            driver.get(BASE_URL);
        }

        public void correctProductsPage() {
            Assert.assertEquals(driver.getTitle(), "Swag Labs");
            Assert.assertEquals(driver.getCurrentUrl(), PRODUCT_PAGE_URL);
            Assert.assertEquals(driver.findElement(titleProductPage).getText(), "Products");
        }

        public void correctItemPage() {
            WebElement backButton = driver.findElement(backToProductsButton);
            Assert.assertTrue(backButton.isDisplayed());
            Assert.assertEquals(backButton.getText(), "Back to products");
        }

        @Test
        public void loginStandartUserTest() {
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            correctProductsPage();
        }

        @Test
        public void correctProductNameTest() {
            loginStandartUserTest();

            String nameByProductPage = driver.findElement(productName).getText();
            driver.findElement(productName).click();

            correctItemPage();

            WebElement thisProductName = driver.findElement(By.xpath(".//div[@class=\"inventory_details_desc_container\"]/div"));
            String nameByItemPage = thisProductName.getText();

            Assert.assertEquals(nameByItemPage, nameByProductPage);
        }

        @Test
        public void correctProductPriceTest() {

            loginStandartUserTest();

            String priceByProductPage = driver.findElement(productPrice).getText();
            driver.findElement(productName).click();

            correctItemPage();

            WebElement thisProductPrice = driver.findElement(By.xpath(".//div[@class=\"inventory_details_desc_container\"]/div[@class=\"inventory_details_price\"]"));
            String priceByItemPage = thisProductPrice.getText();

            Assert.assertEquals(priceByItemPage, priceByProductPage);
        }

        @Test
        public void correctProductImageTest() {

            loginStandartUserTest();

            String imageByProductPage = driver.findElement(productImage).getAttribute("src");

            driver.findElement(productName).click();
            correctItemPage();

            WebElement thisProductImage = driver.findElement(By.xpath(".//div[@class=\"inventory_details_img_container\"]/img"));
            String imageByItemPage = thisProductImage.getAttribute("src");

            Assert.assertEquals(imageByItemPage, imageByProductPage);
        }

        @Test
        public void BackToProductsButtonTest() {
            loginStandartUserTest();

            driver.findElement(productName).click();

            correctItemPage();

            driver.findElement(backToProductsButton).click();

            correctProductsPage();
        }

        @AfterMethod
        public void tearDown() {

            driver.quit();
        }
    }
