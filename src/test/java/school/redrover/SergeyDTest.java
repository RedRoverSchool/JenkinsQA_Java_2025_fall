package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Тестовый класс для проверки работы Selenium с веб-формой.
 * Данный тест открывает указанный URL, вводит текст в поле формы,
 * отправляет его и проверяет, что ответ на экране соответствует ожидаемому.
 */
public class SergeyDTest {
    /**
     * Метод тестирования функциональности веб-формы.
     * Выполняет следующие шаги:
     * 1. Инициализирует драйвер Chrome.
     * 2. Открывает страницу с формой.
     * 3. Устанавливает неявное ожидание элементов.
     * 4. Вводит текст в поле "my-text".
     * 5. Нажимает кнопку отправки.
     * 6. Проверяет, что отображаемое сообщение совпадает с "Received!".
     * 7. Закрывает браузер после завершения.
     */
    @Test
    public void testSelenium() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.findElement(By.name("my-text")).sendKeys("Selenium");
        driver.findElement(By.cssSelector("button")).click();

        WebElement message = driver.findElement(By.id("message"));

        Assert.assertEquals(message.getText(), "Received!");

        driver.quit();
    }
}
