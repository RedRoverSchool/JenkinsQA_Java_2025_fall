package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class KiraMittTest {
    public static final String URL = "https://demoqa.com/alerts";
    public static final String TEST_NAME = "Ivan";

    @Test
    public void testSimpleAlert() {
        WebDriver driver = new ChromeDriver();
        driver.get(URL);

        driver.findElement(By.id("alertButton")).click();
        Alert alert = driver.switchTo().alert();
        Assert.assertTrue(alert.getText().contains("You clicked a button"),
                "Отсутствует сообщение в окне при появлении обычного alert");

        driver.quit();
    }

    @Test
    public void testTimeDelayAlert() {
        WebDriver driver = new ChromeDriver();
        driver.get(URL);

        driver.findElement(By.id("timerAlertButton")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alertTime = driver.switchTo().alert();
        Assert.assertTrue(alertTime.getText().contains("This alert appeared after 5 seconds"),
                "Отсутствует сообщение в окне при появлении alert с задержкой");

        driver.quit();
    }

    @Test
    public void testConfirmBoxSelectOkAlert() {
        WebDriver driver = new ChromeDriver();
        driver.get(URL);

        driver.findElement(By.id("confirmButton")).click();
        Alert alertOk = driver.switchTo().alert();
        Assert.assertTrue(alertOk.getText().contains("Do you confirm action?"),
                "Отсутствует сообщение в окне при появлении выбора действий");
        alertOk.accept();
        Assert.assertEquals(driver.findElement(By.id("confirmResult")).getText(),
                "You selected Ok",
                "Отсутствует отметка о выбранной кнопке Ok");

        driver.quit();
    }

    @Test
    public void testConfirmBoxSelectCancelAlert() {
        WebDriver driver = new ChromeDriver();
        driver.get(URL);

        driver.findElement(By.id("confirmButton")).click();
        Alert alertCancel = driver.switchTo().alert();
        Assert.assertTrue(alertCancel.getText().contains("Do you confirm action?"),
                "Отсутствует сообщение в окне при появлении выбора действий");
        alertCancel.dismiss();
        Assert.assertEquals(driver.findElement(By.id("confirmResult")).getText(),
                "You selected Cancel",
                "Отсутствует отметка о выбранной кнопке Cancel");

        driver.quit();
    }

    @Test
    public void testPromptBoxSelectOkAlert() {
        WebDriver driver = new ChromeDriver();
        driver.get(URL);

        driver.findElement(By.id("promtButton")).click();
        Alert promptAlert = driver.switchTo().alert();
        Assert.assertTrue(promptAlert.getText().contains("Please enter your name"),
                "Отсутствует сообщение в окне с просьбой ввести имя");
        promptAlert.sendKeys(TEST_NAME);
        promptAlert.accept();
        Assert.assertEquals(driver.findElement(By.id("promptResult")).getText(),
                "You entered " + TEST_NAME,
                "Отсутствует отметка о введенном имени");
        driver.quit();
    }

    @Test
    public void testPromptBoxSelectCancelAlert() {
        WebDriver driver = new ChromeDriver();
        driver.get(URL);

        driver.findElement(By.id("promtButton")).click();
        Alert promptAlertCancel = driver.switchTo().alert();
        Assert.assertTrue(promptAlertCancel.getText().contains("Please enter your name"),
                "Отсутствует сообщение в окне с просьбой ввести имя");
        promptAlertCancel.sendKeys(TEST_NAME);
        promptAlertCancel.dismiss();
        boolean promptResultPresent;
        try {
            driver.findElement(By.id("promptResult"));
            promptResultPresent = true;
        } catch (NoSuchElementException e) {
            promptResultPresent = false;
        }
        Assert.assertFalse(promptResultPresent,
                "Элемент для отметки имени должен отсутствовать");

        driver.quit();
    }
}
