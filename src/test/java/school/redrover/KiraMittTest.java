package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KiraMittTest {

    public static final String BASE_URL = "https://demoqa.com/";
    public static final String ALERT_URL = BASE_URL+"alerts";
    public static final String DATE_PICKER_URL = BASE_URL+"date-picker";
    public static final String ACCORDIAN_URL = BASE_URL+"accordian";
    public static final String TEST_NAME = "Ivan";  // Для тестов с PromptBox

    private WebDriver driver;

    @BeforeMethod
    protected void beforeMethod(Method method) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    protected void afterMethod(Method method) {
        driver.quit();
    }

    public void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForInvisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    @Test
    public void testSimpleAlert() {
        driver.get(ALERT_URL);
        driver.findElement(By.id("alertButton")).click();
        Alert alert = driver.switchTo().alert();
        Assert.assertTrue(alert.getText().contains("You clicked a button"),
                "Отсутствует сообщение в окне при появлении обычного alert");
        alert.accept();
    }

    @Test
    public void testTimeDelayAlert() {
        driver.get(ALERT_URL);

        driver.findElement(By.id("timerAlertButton")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alertTime = driver.switchTo().alert();
        Assert.assertTrue(alertTime.getText().contains("This alert appeared after 5 seconds"),
                "Отсутствует сообщение в окне при появлении alert с задержкой");
        alertTime.accept();
    }

    @Test
    public void testConfirmBoxSelectOkAlert() {
        driver.get(ALERT_URL);

        driver.findElement(By.id("confirmButton")).click();
        Alert alertOk = driver.switchTo().alert();
        Assert.assertTrue(alertOk.getText().contains("Do you confirm action?"),
                "Отсутствует сообщение в окне при появлении выбора действий");
        alertOk.accept();
        Assert.assertEquals(driver.findElement(By.id("confirmResult")).getText(),
                "You selected Ok",
                "Отсутствует отметка о выбранной кнопке Ok");
    }

    @Test
    public void testConfirmBoxSelectCancelAlert() {
        driver.get(ALERT_URL);

        driver.findElement(By.id("confirmButton")).click();
        Alert alertCancel = driver.switchTo().alert();
        Assert.assertTrue(alertCancel.getText().contains("Do you confirm action?"),
                "Отсутствует сообщение в окне при появлении выбора действий");
        alertCancel.dismiss();
        Assert.assertEquals(driver.findElement(By.id("confirmResult")).getText(),
                "You selected Cancel",
                "Отсутствует отметка о выбранной кнопке Cancel");
    }

    @Test
    public void testPromptBoxSelectOkAlert() {
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
    }

    @Test
    public void testPromptBoxSelectCancelAlert() {
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
    }

    @Test
    public void testSelectDateWithScrollingArrows() {
        driver.get(DATE_PICKER_URL);

        WebElement inputField = driver.findElement(By.id("datePickerMonthYearInput"));
        inputField.click();
        driver.findElement(By.className("react-datepicker__navigation--next")).click();
        driver.findElement(By.className("react-datepicker__day--024")).click();
        Assert.assertEquals(inputField.getAttribute("value"),
                LocalDate.now().plusMonths(1).withDayOfMonth(24)
                        .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                "Неверно указана выбранная дата");
    }

    @Test
    public void testSelectDateWithDropdownList() {
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
    }

    @Test
    public void testSelectDateAndTime() {
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
    }

    @Test
    public void testAccordian() {
        driver.get(ACCORDIAN_URL);

        WebElement section1Header = driver.findElement(By.id("section1Heading"));
        WebElement section2Header = driver.findElement(By.id("section2Heading"));
        WebElement section3Header = driver.findElement(By.id("section3Heading"));
        WebElement section1Content = driver.findElement(By.id("section1Content"));
        WebElement section2Content = driver.findElement(By.id("section2Content"));
        WebElement section3Content = driver.findElement(By.id("section3Content"));

        SoftAssert softAssertAccordian = new SoftAssert();
        //  Проверка на первоначальное отображение элементов
        softAssertAccordian.assertTrue(section1Content.isDisplayed(), "Section1 должен быть открыт изначально");
        softAssertAccordian.assertFalse(section2Content.isDisplayed(), "Section2 изначально закрыт");
        softAssertAccordian.assertFalse(section3Content.isDisplayed(), "Section3 изначально закрыт");
        //  Проверка на открытие Section2
        section2Header.click();
        waitForVisibility(section2Content);
        waitForInvisibility(section1Content);
        softAssertAccordian.assertTrue(section2Content.isDisplayed(), "Section2 должен быть открыт после клика");
        softAssertAccordian.assertFalse(section1Content.isDisplayed(), "Section1 должен закрыться");
        softAssertAccordian.assertFalse(section3Content.isDisplayed(), "Section3 должен оставаться закрытым");
        //  Проверка на открытие Section3
        section3Header.click();
        waitForVisibility(section3Content);
        waitForInvisibility(section2Content);
        softAssertAccordian.assertTrue(section3Content.isDisplayed(), "Section3 должен быть открыт после клика");
        softAssertAccordian.assertFalse(section2Content.isDisplayed(), "Section2 должен закрыться");
        softAssertAccordian.assertFalse(section1Content.isDisplayed(), "Section1 должен оставаться закрытым");
        //  Проверка на открытие Section1
        section1Header.click();
        waitForVisibility(section1Content);
        waitForInvisibility(section3Content);
        softAssertAccordian.assertTrue(section1Content.isDisplayed(), "Section1 должен быть открыт после клика");
        softAssertAccordian.assertFalse(section3Content.isDisplayed(), "Section3 должен закрыться");
        softAssertAccordian.assertFalse(section2Content.isDisplayed(), "Section2 должен оставаться закрытым");
        //  Проверка на открытие-закрытие Section2
        section2Header.click();
        waitForVisibility(section2Content);
        softAssertAccordian.assertTrue(section2Content.isDisplayed(), "Section2 должен быть открыт после клика");
        section2Header.click();
        waitForInvisibility(section2Content);
        softAssertAccordian.assertFalse(section2Content.isDisplayed(), "Section2 должен быть закрыт после клика");
        //  Проверка на открытие-закрытие Section3
        section3Header.click();
        waitForVisibility(section3Content);
        softAssertAccordian.assertTrue(section3Content.isDisplayed(), "Section3 должен быть открыт после клика");
        section3Header.click();
        waitForInvisibility(section3Content);
        softAssertAccordian.assertFalse(section3Content.isDisplayed(), "Section3 должен быть закрыт после клика");
        //  Проверка на открытие-закрытие Section1
        section1Header.click();
        waitForVisibility(section1Content);
        softAssertAccordian.assertTrue(section1Content.isDisplayed(), "Section1 должен быть открыт после клика");
        section1Header.click();
        waitForInvisibility(section1Content);
        softAssertAccordian.assertFalse(section1Content.isDisplayed(), "Section1 должен быть закрыт после клика");

        softAssertAccordian.assertAll();
    }
}
