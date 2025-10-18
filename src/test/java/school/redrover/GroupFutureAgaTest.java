package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class GroupFutureAgaTest {

    /**
     * Класс DriverManager предназначен для управления экземпляром WebDriver.
     * Реализует паттерн Singleton — гарантирует, что будет создан только один экземпляр браузера.
     *
     * Используется в тестовых классах для получения уже инициализированного драйвера,
     * что позволяет избежать повторного запуска браузера и повышает производительность тестов.
     *
     * Для Windows 7 используется специальная версия 109 ChromeDriver,
     * так как стандартный не поддерживается.
     */
    public static class DriverManager {

        /**
         * Статическая переменная для хранения экземпляра WebDriver.
         * Используется как Singleton, чтобы избежать создания нескольких экземпляров браузера.
         */
        private static WebDriver driver;

        /**
         * Метод getDriver() возвращает текущий экземпляр WebDriver.
         * Если драйвер ещё не инициализирован, он создаётся с учётом ОС.
         *
         * @return Возвращает инициализированный WebDriver
         */
        public static WebDriver getDriver() {
            if (DriverManager.driver == null) {
                String osName = System.getProperty("os.name").toLowerCase();
                if (osName.contains("windows") && osName.contains("7")) {
                    String pathChromeDriver = "D:\\Projects\\ChromeDriver\\chromedriver_win32_109_win7\\chromedriver.exe";
                    System.setProperty("webdriver.chrome.driver", pathChromeDriver);
                    // для Windows 7 необходимо скачать и установить путь к драйверу ChromeDriver 109.0.5414.74 https://chromedriver.chromium.org/downloads

                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("auto-open-devtools-for-tabs");
                    driver = new ChromeDriver(options);
                    driver.manage().window().maximize();

                } else {

                    driver = new ChromeDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

                }
            }
            return driver;
        }
    }
    private WebDriver driver;

    private static final String DEMOQA_URL = "https://demoqa.com/";

    private static final String TEST_ADDRESS = """
            c. Moscow
            str. Pushkin
            house Kolotushkin
            """;

    @BeforeMethod
    public void setUpDriver() {
        driver = DriverManager.getDriver();
    }

    @AfterClass
    public void closeAllDriver() {
        if (nonNull(driver)) {
            driver.quit();
        }
    }

    @Test
    public void testSelenium() throws InterruptedException{
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        Thread.sleep(1000);

        driver.findElement(By.name("my-password")).sendKeys("123");
        driver.findElement(By.cssSelector("button")).click();

        WebElement message = driver.findElement(By.id("message"));
        Assert.assertEquals(message.getText(),"Received!");
    }

    @Test
    public void testSelenium1() throws InterruptedException{
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        Thread.sleep(1000);

        driver.findElement(By.name("my-textarea")).sendKeys("Hello world");
        driver.findElement(By.cssSelector("button")).click();


        WebElement message = driver.findElement(By.id("message"));
        Assert.assertEquals(message.getText(),"Received!");
    }

    @Test(testName = "Проверка количества карточек на странице через cssSelector/xPath")
    public void check_cards_by_xPath() {
        driver.get(DEMOQA_URL);

        List<WebElement> elementsByXPath = driver.findElements(By.xpath("//div[@class='card mt-4 top-card']"));
        List<WebElement> elementsBySelector = driver.findElements(By.cssSelector(".card.mt-4.top-card"));

        assertEquals(elementsBySelector.size(), 6);
        assertEquals(elementsByXPath.size(), 6);
    }

    @Test(testName = "Проверка карточек во вкладке elements")
    public void check_elements() {
        driver.get(DEMOQA_URL + "elements");

        List<WebElement> elementsShowMenu = driver.findElements(By.xpath("//div[@class='element-list collapse show']//li"));
        assertEquals(elementsShowMenu.size(), 9);

        assertNotNull(elementsShowMenu.get(0).findElement(By.xpath("//span[text()='Text Box']")));
        assertNotNull(elementsShowMenu.get(4).findElement(By.xpath("//span[text()='Buttons']")));
        assertNotNull(elementsShowMenu.get(8).findElement(By.xpath("//span[text()='Dynamic Properties']")));
    }

    @Test(testName = "Проверка работы text-box")
    public void check_textBox_positive() throws InterruptedException {
        driver.get(DEMOQA_URL + "text-box");

        WebElement fullNameBox = driver.findElement(By.xpath("//input[@id='userName']"));
        WebElement emailBox = driver.findElement(By.xpath("//input[@id='userEmail']"));
        WebElement currentAddressBox = driver.findElement(By.xpath("//textarea[@id='currentAddress']"));
        WebElement permanentAddressBox = driver.findElement(By.xpath("//textarea[@id='permanentAddress']"));

        fullNameBox.sendKeys("Ivanov Ivan Ivanovich");
        emailBox.sendKeys("ivanovii@test.ru");
        currentAddressBox.sendKeys(TEST_ADDRESS);
        permanentAddressBox.sendKeys(TEST_ADDRESS);

        WebElement submitButton = driver.findElement(By.xpath("//button[@id='submit' and @class='btn btn-primary']"));
        submitButton.click();

        Thread.sleep(500);

        WebElement responseName = driver.findElement(By.xpath("//div[@id='output']//p[@id='name']"));
        assertEquals("Name:Ivanov Ivan Ivanovich", responseName.getText());

        WebElement responseEmail = driver.findElement(By.xpath("//div[@id='output']//p[@id='email']"));
        assertEquals("Email:ivanovii@test.ru", responseEmail.getText());

        WebElement responseCurrentAddress = driver.findElement(By.xpath("//div[@id='output']//p[@id='currentAddress']"));
        assertEquals("Current Address :c. Moscow str. Pushkin house Kolotushkin", responseCurrentAddress.getText());

        WebElement responsePermanentAddress = driver.findElement(By.xpath("//div[@id='output']//p[@id='permanentAddress']"));
        assertEquals("Permananet Address :c. Moscow str. Pushkin house Kolotushkin", responsePermanentAddress.getText());
    }
}


