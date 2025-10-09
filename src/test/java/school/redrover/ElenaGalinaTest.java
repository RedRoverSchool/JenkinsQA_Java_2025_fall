package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class ElenaGalinaTest {
    private WebDriver driver;

    private static final String BASE_URL = "https://www.saucedemo.com";
    private static final String PRODUCT_PAGE_URL = "https://www.saucedemo.com/inventory.html";

    private final By titleProductPage = By.xpath(".//div[@id=\"header_container\"]/div[2]/span");
    private final By backToProductsButton = By.id("back-to-products");
    private final By productName = By.xpath(".//*[@id=\"item_2_title_link\"]/div");
    private final By productPrice = By.xpath(".//*[@id=\"item_2_title_link\"]/ancestor::div[@class=\"inventory_item_description\"]//div[@class=\"pricebar\"]/div");
    private final By productImage = By.xpath(".//*[@id=\"item_2_img_link\"]/img");
    private final By currentProductName = By.xpath(".//div[@class=\"inventory_details_desc_container\"]/div");
    private final By allProducts = By.xpath(".//div[@class=\"inventory_item_name \"]");

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

    // Проверка регистрации пользователя с валидными входными данными (standard_user)
    @Test
    public void loginStandartUserTest() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        correctProductsPage();
    }

    // Проверка соответствия наименования товара в карточке наименованию в каталоге Products
    @Test
    public void correctProductNameTest() {
        loginStandartUserTest();

        String nameByProductPage = driver.findElement(productName).getText();
        driver.findElement(productName).click();

        correctItemPage();

        String nameByItemPage = driver.findElement(currentProductName).getText();

        Assert.assertEquals(nameByItemPage, nameByProductPage);
    }

    // Проверка соответствия цены товара в карточке цене в каталоге Products
    @Test
    public void correctProductPriceTest() {

        loginStandartUserTest();

        String priceByProductPage = driver.findElement(productPrice).getText();
        driver.findElement(productName).click();

        correctItemPage();

        String priceByItemPage = driver.findElement(
                        By.xpath(".//div[@class=\"inventory_details_price\"]"))
                .getText();

        Assert.assertEquals(priceByItemPage, priceByProductPage);
    }

    // Проверка соответствия изображения товара в карточке изображению в каталоге Products
    @Test
    public void correctProductImageTest() {

        loginStandartUserTest();

        String imageByProductPage = driver.findElement(productImage).getAttribute("src");
        driver.findElement(productName).click();

        correctItemPage();

        String imageByItemPage = driver.findElement(
                        By.xpath(".//div[@class=\"inventory_details_img_container\"]/img"))
                .getAttribute("src");

        Assert.assertEquals(imageByItemPage, imageByProductPage);
    }

    // Проверка работоспособности кнопки "Back to products" для возврата на страницу каталога Products
    @Test
    public void BackToProductsButtonTest() {
        loginStandartUserTest();

        driver.findElement(productName).click();

        correctItemPage();

        driver.findElement(backToProductsButton).click();

        correctProductsPage();
    }

    // Тест с использованием List
    // Проверка соответствия наименования товара в карточке наименованию в каталоге Products
    @Test
    public void correctAllProductNameTest() {
        loginStandartUserTest();

        List<WebElement> productNamesList = driver.findElements(allProducts);

        List<String> namesList = new ArrayList<>();  // список всех наименований товаров на странице Products
        List<String> currentNamesList = new ArrayList<>(); //список наименований из открывающихся карточек

        for (int i = 0; i < productNamesList.size(); i++) {
            productNamesList = driver.findElements(allProducts);

            WebElement nameForClick = productNamesList.get(i);
            namesList.add(nameForClick.getText());

            nameForClick.click(); //клик на каждый элемент из списка в каталоге Products

            currentNamesList.add(driver.findElement(currentProductName).getText());

            driver.findElement(backToProductsButton).click(); // назад на страницу Products
        }

        Assert.assertEquals(namesList, currentNamesList);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
