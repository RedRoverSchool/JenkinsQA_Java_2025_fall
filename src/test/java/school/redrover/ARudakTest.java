package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class ARudakTest {

    @Test
    public void textInputTest() {
        String textExample = "test";
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement textInput = driver.findElement(By.id("my-text-id"));
        textInput.sendKeys(textExample);
        assertEquals(textInput.getAttribute("value"), textExample, "Input data is not equal");

        driver.quit();
    }

    @Test
    public void passwordInputTest() {
        String passwordExample = "123456";
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement passwordInput = driver.findElement(By.xpath("//input[@type='password']"));
        passwordInput.sendKeys(passwordExample);
        assertEquals(passwordInput.getAttribute("value"), passwordExample, "Input data is not equal");

        driver.quit();
    }

    @Test
    public void scrollBarAppearTextAreaTest() {
        String textExample = "1\n 2\n 3\n 4\n";
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement textArea = driver.findElement(By.cssSelector(".form-control[name='my-textarea']"));
        long cleanTextArea = Long.parseLong(textArea.getAttribute("scrollHeight"));
        textArea.sendKeys(textExample);
        long fullTextArea = Long.parseLong(textArea.getAttribute("scrollHeight"));
        assertTrue(cleanTextArea < fullTextArea);

        driver.quit();
    }

    @Test
    public void dropdownSelectTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement dropdown = driver.findElement(By.cssSelector(".form-select[name='my-select']"));
        dropdown.findElement(By.cssSelector(".form-select option:nth-child(3)")).click();

        assertEquals(dropdown.getAttribute("value"), "2");

        driver.quit();
    }

    @Test
    public void dropdownDatalistTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement cities = driver.findElement(By.id("my-options"));
        List<WebElement> options = cities.findElements(By.tagName("option"));
        List<String> citiesList = new ArrayList<>();
        for (WebElement element : options) {
            citiesList.add(element.getAttribute("value"));
        }

        WebElement dropdownDatalist = driver.findElement(By.xpath("//input[@name='my-datalist']"));
        dropdownDatalist.sendKeys(citiesList.get(0));
        assertEquals(dropdownDatalist.getAttribute("value"), "San Francisco");

        driver.quit();
    }

    @Test
    public void fileInputTest() {
        String filepath = "C:\\RedroverSQL\\test.txt";
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement fileInput = driver.findElement(By.xpath("//input[@name='my-file']"));
        fileInput.sendKeys(filepath);

        assertTrue(fileInput.getAttribute("value").contains("test.txt"),"File is not attached");

        driver.quit();
    }

    @Test
    public void checkboxesTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement chekedCheckbox = driver.findElement(By.xpath("//input[@id='my-check-1']"));
        boolean one = chekedCheckbox.isSelected();
        chekedCheckbox.click();
        assertNotEquals(chekedCheckbox.isSelected(), one, "Checked checkbox has an error");

        WebElement defaultCheckbox = driver.findElement(By.xpath("//input[@id='my-check-2']"));
        boolean two = defaultCheckbox.isSelected();
        defaultCheckbox.click();
        assertNotEquals(defaultCheckbox.isSelected(), two, "Default checkbox has an error");

        driver.quit();
    }

    @Test
    public void radioButtonTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement radio1 = driver.findElement(By.xpath("//input[@id='my-radio-1']"));
        boolean one = radio1.isSelected();
        WebElement radio2 = driver.findElement(By.xpath("//input[@id='my-radio-2']"));
        boolean two = radio2.isSelected();
        radio2.click();

        assertNotEquals(radio1.isSelected(), one, "RadioButton 1 didn't change the condition");
        assertNotEquals(radio2.isSelected(), two, "RadioButton 2 didn't change the condition");

        driver.quit();
    }

    @Test
    public void submitButtonTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        WebElement submitPageName = driver.findElement(By.xpath("//h1[text()='Form submitted']"));
        assertEquals(submitPageName.getText(), "Form submitted");

        driver.quit();
    }
}
