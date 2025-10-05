package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KiraMittTest {
    
    public static final String ALERT_URL = "https://demoqa.com/alerts";
    public static final String TEST_NAME = "Ivan";
    public static final String DATE_PICKER_URL = "https://demoqa.com/date-picker";

    @Test
    public void testSimpleAlert() {
        WebDriver driver = new ChromeDriver();
        driver.get(ALERT_URL);

        driver.findElement(By.id("alertButton")).click();
        Alert alert = driver.switchTo().alert();
        Assert.assertTrue(alert.getText().contains("You clicked a button"),
                "Отсутствует сообщение в окне при появлении обычного alert");

        driver.quit();
    }

    @Test
    public void testTimeDelayAlert() {
        WebDriver driver = new ChromeDriver();
        driver.get(ALERT_URL);

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
        driver.get(ALERT_URL);

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
        driver.get(ALERT_URL);

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
        driver.get(ALERT_URL);

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
        driver.get(ALERT_URL);

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

    @Test
    public void testSelectDateWithScrollingArrows() {
        WebDriver driver = new ChromeDriver();
        driver.get(DATE_PICKER_URL);

        WebElement inputField = driver.findElement(By.id("datePickerMonthYearInput"));
        inputField.click();
        driver.findElement(By.className("react-datepicker__navigation--next")).click();
        driver.findElement(By.className("react-datepicker__day--024")).click();
        Assert.assertEquals(inputField.getAttribute("value"),
                LocalDate.now().plusMonths(1).withDayOfMonth(24)
                        .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                "Неверно указана выбранная дата");

        driver.quit();
    }

    @Test
    public void testSelectDateWithDropdownList() {
        WebDriver driver = new ChromeDriver();
        driver.get(DATE_PICKER_URL);

        WebElement inputField = driver.findElement(By.id("datePickerMonthYearInput"));
        inputField.click();
        Select selectMonth = new Select(driver.findElement(By.className("react-datepicker__month-select")));
        selectMonth.selectByVisibleText("March");
        Select selectYear = new Select(driver.findElement(By.className("react-datepicker__year-select")));
        selectYear.selectByVisibleText("1999");
        driver.findElement(By.className("react-datepicker__day--024")).click();
        Assert.assertEquals(inputField.getAttribute("value"),
                "03/24/1999",
                "Неверно указана выбранная дата");

        driver.quit();
    }

    @Test
    public void testSelectDateAndTime() {
        WebDriver driver = new ChromeDriver();
        driver.get(DATE_PICKER_URL);

        WebElement inputField = driver.findElement(By.id("dateAndTimePickerInput"));
        inputField.click();
        driver.findElement(By.className("react-datepicker__month-read-view--down-arrow")).click();
        driver.findElement(By.xpath(
                "//div[contains(@class,'react-datepicker__month-option') and text()='March']")).click();
        driver.findElement(By.className("react-datepicker__year-read-view--down-arrow")).click();
        driver.findElement(By.xpath(
                "//div[contains(@class,'react-datepicker__year-option') and text()='2028']")).click();
        driver.findElement(By.xpath(
                "//div[contains(@aria-label,'Choose Wednesday, March 15th, 2028')]")).click();
        driver.findElement(By.xpath("//li[normalize-space()='21:00']")).click();
        Assert.assertEquals(inputField.getAttribute("value"),
                "March 15, 2028 9:00 PM",
                "Неверно указаны выбранные дата и время");

        driver.quit();
    }
}
