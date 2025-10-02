package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class PetriukATest {

    private WebDriver driver;

    /**
     * URL главной страницы сайта
     */
    private static final String MAIN_PAGE_URL = "https://demoqa.com/";

    private static final String TEST_ADDRESS = """
            c. Moscow
            str. Pushkin
            house Kolotushkin
            """;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test(testName = "Проверка количества карточек на главной странице с использованием cssSelector")
    public void check_cards_by_selector() {
        driver.get(MAIN_PAGE_URL);

        //получение списка элементов по селектору
        List<WebElement> elementsBySelector = driver.findElements(By.cssSelector(".card.mt-4.top-card"));

        assertEquals(elementsBySelector.size(), 6);
    }

    @Test(testName = "Проверка количества карточек на главной странице с использованием xPath")
    public void check_cards_by_xPath() {
        driver.get(MAIN_PAGE_URL);

        //получение списка элементов по xPath
        List<WebElement> elementsByXPath = driver.findElements(By.xpath("//div[@class='card mt-4 top-card']"));

        assertEquals(elementsByXPath.size(), 6);
    }

    @Test(testName = "Проверка количества и видов элементов при входе в меню elements")
    public void check_elements() {
        driver.get(MAIN_PAGE_URL + "elements");

        //проверяем, что есть раскрытое меню с 9 элементами
        List<WebElement> elementsShowMenu = driver.findElements(By.xpath("//div[@class='element-list collapse show']//li"));
        assertEquals(elementsShowMenu.size(), 9);

        //Проверяем последовательность и тип элементов
        assertNotNull(elementsShowMenu.get(0).findElement(By.xpath("//span[text()='Text Box']")));
        assertNotNull(elementsShowMenu.get(4).findElement(By.xpath("//span[text()='Buttons']")));
        assertNotNull(elementsShowMenu.get(8).findElement(By.xpath("//span[text()='Dynamic Properties']")));
    }

    @Test(testName = "Позитивная проверка text-box")
    public void check_textBox_positive() throws InterruptedException {
        driver.get(MAIN_PAGE_URL + "text-box");

        //проверка строки ввода FullName
        WebElement fullNameBox = driver.findElement(By.xpath("//input[@id='userName']"));
        assertNotNull(fullNameBox);
        assertNotNull(fullNameBox.getAttribute("placeholder"), "Full Name");

        //проверка строки ввода email
        WebElement emailBox = driver.findElement(By.xpath("//input[@id='userEmail']"));
        assertNotNull(emailBox);
        assertNotNull(emailBox.getAttribute("placeholder"), "name@example.com");

        //проверка строки ввода current address
        WebElement currentAddressBox = driver.findElement(By.xpath("//textarea[@id='currentAddress']"));
        assertNotNull(currentAddressBox);
        assertNotNull(currentAddressBox.getAttribute("placeholder"), "Current Address");

        //проверка строки ввода permanent address
        WebElement permanentAddressBox = driver.findElement(By.xpath("//textarea[@id='permanentAddress']"));
        assertNotNull(permanentAddressBox);

        //заполнение строк ввода
        fullNameBox.sendKeys("Ivanov Ivan Ivanovich");
        emailBox.sendKeys("ivanovii@test.ru");
        currentAddressBox.sendKeys(TEST_ADDRESS);
        permanentAddressBox.sendKeys(TEST_ADDRESS);

        //нажатие на кнопку
        WebElement submitButton = driver.findElement(By.xpath("//button[@id='submit' and @class='btn btn-primary']"));
        submitButton.click();

        //на всякий случай
        Thread.sleep(500);

        //проверка результатов
        WebElement responseName = driver.findElement(By.xpath("//div[@id='output']//p[@id='name']"));
        assertEquals("Name:Ivanov Ivan Ivanovich", responseName.getText());

        WebElement responseEmail = driver.findElement(By.xpath("//div[@id='output']//p[@id='email']"));
        assertEquals("Email:ivanovii@test.ru", responseEmail.getText());

        WebElement responseCurrentAddress = driver.findElement(By.xpath("//div[@id='output']//p[@id='currentAddress']"));
        assertEquals("Current Address :c. Moscow str. Pushkin house Kolotushkin", responseCurrentAddress.getText());

        WebElement responsePermanentAddress = driver.findElement(By.xpath("//div[@id='output']//p[@id='permanentAddress']"));
        assertEquals("Permananet Address :c. Moscow str. Pushkin house Kolotushkin", responsePermanentAddress.getText());

    }

    @AfterTest
    public void closeAll() {
        driver.quit();
    }

}
