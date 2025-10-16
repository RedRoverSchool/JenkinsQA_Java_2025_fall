package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class RenataBoichenkoTest {
    private WebDriver driver;


    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.xpath("//a[text()='Web form']"));
        textBox.click();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test(priority = 1)
        public void testTextInput() {
        WebElement input = driver.findElement(By.xpath("//input[@id='my-text-id']"));
        input.click();
        input.sendKeys("Go !!!");

        String actualValue = input.getAttribute("value");
        Assert.assertEquals(actualValue, "Go !!!");
    }

    @Test(priority = 2)
    public void testPassword() {
        WebElement password = driver.findElement(By.name("my-password"));
        password.click();
        password.sendKeys("Java!");

        String actualValue = password.getAttribute("value");
        Assert.assertEquals(actualValue, "Java!");
    }

    @Test(priority = 3)
    public void testTextarea() {
        WebElement textarea = driver.findElement(By.name("my-textarea"));
        textarea.click();
        textarea.sendKeys("Java! Forever !!");

        String actualValue = textarea.getAttribute("value");
        Assert.assertEquals(actualValue, "Java! Forever !!");
    }

    @Test(priority = 4)
    public void testDisabledInput() {
        WebElement disabled = driver.findElement(By.name("my-disabled"));
        disabled.click();

        Assert.assertTrue(disabled.isDisplayed(), "Поле не задизейблено");
    }

    @Test(priority = 5)
    public void testReadonlyInput() {
        WebElement readOnlyInput = driver.findElement(By.name("my-readonly"));
        readOnlyInput.click();

        String expectedPlaceholder = "Readonly input";
        String actualPlaceholder = readOnlyInput.getAttribute("value");
        Assert.assertEquals(actualPlaceholder, expectedPlaceholder, "Текст должен быть 'Readonly input'");
    }

    @Test(priority = 6)
    public void testDropdownSelect() {
        WebElement select = driver.findElement(By.name("my-select"));
        select.click();

        List<WebElement> options = select.findElements(By.tagName("option"));

        Random random = new Random();
        WebElement randomOption = options.get(random.nextInt(options.size()));

        String expectedText = randomOption.getText().trim();

        randomOption.click();

        WebElement selectedOption = select.findElement(By.cssSelector("option:checked"));
        String actualText = selectedOption.getText().trim();

        Assert.assertEquals(actualText, expectedText, "Выбран неправильный элемент");
    }

    @Test(priority = 7)
    public void testDropdownDatalist() {
        WebElement datalist = driver.findElement(By.name("my-datalist"));
        datalist.click();

        WebElement shadowHost = driver.findElement(By.cssSelector("select[name='my-select']"));

        SearchContext shadowRoot = shadowHost.getShadowRoot();

        List<String> options = List.of(
                "San Francisco", "New York", "Seattle", "Los Angeles", "Chicago"
        );

        Random random = new Random();

        String expectedValue = options.get(random.nextInt(options.size()));

        datalist.sendKeys(expectedValue);

        String actualValue = datalist.getAttribute("value");
        Assert.assertEquals(actualValue, expectedValue, "Error");
    }

    @Test(priority = 8)
    public void testChexBox() {
        List<WebElement> chexBoxList = driver.findElements(By.xpath("//input[@type='checkbox']"));

        Assert.assertTrue(chexBoxList.size() == 2, "Not == 2");
        Assert.assertTrue(chexBoxList.get(0).isSelected(), "checkbox d't checked");

        WebElement checkbox = driver.findElement(By.xpath("//input[@id='my-check-1']"));
        WebElement checkbox2 = driver.findElement(By.xpath("//input[@id='my-check-2']"));

        checkbox.click();
        Assert.assertFalse(checkbox.isSelected(), "checkbox didn't checked");

        checkbox.click();
        Assert.assertTrue(checkbox.isSelected(), "checkbox else checked");

        checkbox2.click();
        Assert.assertTrue(checkbox2.isSelected(), "checkbox2 didn't checked");
    }

    @Test(priority = 9)
    public void testButton() {
        List<WebElement> radios = driver.findElements(By.xpath("//input[@type='radio']"));
        Assert.assertEquals(radios.size(), 2, "Нет 2 кнопок");
        Assert.assertTrue(radios.get(0).isSelected(), "Кнопка не выбрана");

        WebElement radio1 = driver.findElement(By.id("my-radio-1"));
        WebElement radio2 = driver.findElement(By.id("my-radio-2"));
        radio2.click();

        Assert.assertFalse(radio1.isSelected(), "Кнопка1 не выбрана");
        Assert.assertTrue(radio2.isSelected(), "Кнопка2 не выбрана");
    }

    @Test(priority = 10)
    public void testAllPageSubmit() {
        WebElement submit = driver.findElement(By.xpath("//button[text()='Submit']"));
        submit.click();

        WebElement resultForm = driver.findElement(By.xpath("//h1[text()='Form submitted']"));
        Assert.assertEquals(resultForm.getText(), "Form submitted");

    }
}

