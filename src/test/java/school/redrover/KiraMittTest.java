package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class KiraMittTest {

    private static final String BASE_URL = "https://demoqa.com/";
    private static final String ALERT_URL = BASE_URL + "alerts";
    private static final String DATE_PICKER_URL = BASE_URL + "date-picker";
    private static final String ACCORDION_URL = BASE_URL + "accordian";
    private static final String BUTTONS_URL = BASE_URL + "buttons";
    private static final String BROWSER_WINDOWS_URL = BASE_URL + "browser-windows";
    private static final String TOOLTIPS_URL = BASE_URL + "tool-tips";
    private static final String MENU_URL = BASE_URL + "menu";
    private static final String TEST_NAME = "Ivan";  // Для тестов с PromptBox

    private WebDriver driver;
    private Actions action;

    @BeforeMethod
    protected void beforeMethod() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        action = new Actions(driver);
    }

    @AfterMethod
    protected void afterMethod() {
        driver.quit();
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

    // Для теста с аккордеоном:
    protected void assertSectionVisibility(SoftAssert softAssert, WebElement sectionContent, boolean shouldBeVisible, String actionDescription) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String id = sectionContent.getAttribute("id");
        try {
            if (shouldBeVisible) {
                softAssert.assertTrue(wait.until(ExpectedConditions.visibilityOf(sectionContent)).isDisplayed(),
                        id + " должен быть открыт" + actionDescription);
            } else {
                softAssert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(sectionContent)),
                        id + " должен быть закрыт" + actionDescription);
            }
        } catch (TimeoutException e) {
            softAssert.fail("Время ожидания вышло для " + id + " при " + actionDescription);
        }
    }

    @Test
    public void testAccordion() {
        driver.get(ACCORDION_URL);

        WebElement section1Header = driver.findElement(By.id("section1Heading"));
        WebElement section2Header = driver.findElement(By.id("section2Heading"));
        WebElement section3Header = driver.findElement(By.id("section3Heading"));
        WebElement section1Content = driver.findElement(By.id("section1Content"));
        WebElement section2Content = driver.findElement(By.id("section2Content"));
        WebElement section3Content = driver.findElement(By.id("section3Content"));

        SoftAssert softAssertAccordion = new SoftAssert();
        //  Проверка на первоначальное отображение элементов
        softAssertAccordion.assertTrue(section1Content.isDisplayed(), "Section1 должен быть открыт изначально");
        softAssertAccordion.assertFalse(section2Content.isDisplayed(), "Section2 изначально закрыт");
        softAssertAccordion.assertFalse(section3Content.isDisplayed(), "Section3 изначально закрыт");
        //  Проверка на открытие-закрытие блоков при нажатии Section2
        section2Header.click();
        assertSectionVisibility(softAssertAccordion, section2Content, true, "открытии Section2");
        assertSectionVisibility(softAssertAccordion, section1Content, false, "открытии Section2");
        assertSectionVisibility(softAssertAccordion, section3Content, false, "открытии Section2");
        section2Header.click();
        assertSectionVisibility(softAssertAccordion, section1Content, false, "закрытии Section2");
        assertSectionVisibility(softAssertAccordion, section2Content, false, "закрытии Section2");
        assertSectionVisibility(softAssertAccordion, section3Content, false, "закрытии Section2");
        //  Проверка на открытие-закрытие блоков при нажатии Section3
        section3Header.click();
        assertSectionVisibility(softAssertAccordion, section3Content, true, "открытии Section3");
        assertSectionVisibility(softAssertAccordion, section1Content, false, "открытии Section3");
        assertSectionVisibility(softAssertAccordion, section2Content, false, "открытии Section3");
        section3Header.click();
        assertSectionVisibility(softAssertAccordion, section1Content, false, "закрытии Section3");
        assertSectionVisibility(softAssertAccordion, section2Content, false, "закрытии Section3");
        assertSectionVisibility(softAssertAccordion, section3Content, false, "закрытии Section3");
        //  Проверка на открытие-закрытие блоков при нажатии Section1
        section1Header.click();
        assertSectionVisibility(softAssertAccordion, section1Content, true, "открытии Section1");
        assertSectionVisibility(softAssertAccordion, section2Content, false, "открытии Section1");
        assertSectionVisibility(softAssertAccordion, section3Content, false, "открытии Section1");
        section1Header.click();
        assertSectionVisibility(softAssertAccordion, section1Content, false, "закрытии Section1");
        assertSectionVisibility(softAssertAccordion, section2Content, false, "закрытии Section1");
        assertSectionVisibility(softAssertAccordion, section3Content, false, "закрытии Section1");
        softAssertAccordion.assertAll();
    }

    @Test
    public void testDoubleClickButton() {
        driver.get(BUTTONS_URL);

        action.moveToElement(driver.findElement(By.id("doubleClickBtn"))).doubleClick().perform();
        Assert.assertEquals(driver.findElement(By.id("doubleClickMessage")).getText(), "You have done a double click");
    }

    @Test
    public void testRightClickButton() {
        driver.get(BUTTONS_URL);

        action.contextClick(driver.findElement(By.id("rightClickBtn"))).build().perform();
        Assert.assertEquals(driver.findElement(By.id("rightClickMessage")).getText(), "You have done a right click");
    }

    @Test
    public void testDynamicClickButton() {
        driver.get(BUTTONS_URL);

        driver.findElement(By.xpath("//button[text()='Click Me']")).click();
        Assert.assertEquals(driver.findElement(By.id("dynamicClickMessage")).getText(), "You have done a dynamic click");
    }

    @Test
    public void testOpenURLInNewTab() {
        driver.get(BROWSER_WINDOWS_URL);

        driver.findElement(By.id("tabButton")).click();
        Assert.assertEquals(driver.getWindowHandles().size(), 2);
        Object[] windowHandles = driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/sample");
    }

    @Test
    public void testOpenNewWindowVerifyBackgroundColor() {
        driver.get(BROWSER_WINDOWS_URL);

        driver.findElement(By.id("windowButton")).click(); //Нет разницы в открытии нового окна и вкладки
        Assert.assertEquals(driver.getWindowHandles().size(), 2);
        Object[] windowHandles = driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
        Assert.assertEquals(driver.findElement(By.tagName("body")).getCssValue("background-color"),
                "rgba(169, 169, 169, 1)");
    }

    @Test
    public void testTooltipButton() {
        driver.get(TOOLTIPS_URL);

        WebElement button = driver.findElement(By.id("toolTipButton"));
        action.moveToElement(button).perform();
        Assert.assertTrue(driver.findElement(By.xpath("//*[text()='You hovered over the Button']")).isDisplayed(),
                "Отсутствует/неверное всплывающее уведомление у кнопки");
        Assert.assertEquals(button.getAttribute("aria-describedby"), "buttonToolTip",
                "Нет необходимого атрибута у кнопки");
    }

    @Test
    public void testTooltipTextField() {
        driver.get(TOOLTIPS_URL);

        action.moveToElement(driver.findElement(By.id("toolTipTextField"))).perform();
        Assert.assertEquals(driver.findElement(By.id("textFieldToolTip")).getText(), "You hovered over the text field",
                "Неверное всплывающее уведомление у текстового поля");
    }

    @Test
    public void testTooltipContraryInText() {
        driver.get(TOOLTIPS_URL);

        String text = "Contrary";
        action.moveToElement(driver.findElement(By.xpath("//div[@id='texToolTopContainer']//a[text()='" + text + "']"))).perform();
        Assert.assertEquals(driver.findElement(By.id(text.toLowerCase() + "TexToolTip")).getText(), "You hovered over the " + text,
                "Неверное всплывающее уведомление у слова Contrary");
    }

    @Test
    public void testTooltipNumberInText() {
        driver.get(TOOLTIPS_URL);

        String number = "1.10.32";
        action.moveToElement(driver.findElement(By.xpath("//div[@id='texToolTopContainer']//a[text()='" + number + "']"))).perform();
        Assert.assertEquals(driver.findElement(By.id("sectionToolTip")).getText(), "You hovered over the " + number,
                "Неверное всплывающее уведомление у чисел 1.10.32");
    }

    protected void verifyColorChangeOnHover(String elementXpath, int numberOfSubs) {
        WebElement elementParent = driver.findElement(By.xpath(elementXpath + "/parent::li"));
        String colorBefore = elementParent.getCssValue("background-color");
        String itemText = elementParent.getText();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        action.moveToElement(elementParent).perform();
        try {
            wait.until(driver -> !elementParent.getCssValue("background-color").equals(colorBefore));
        } catch (TimeoutException e) {
            Assert.fail("Время ожидания изменения цвета вышло для " + itemText);
        }

        List<WebElement> subItems = driver.findElements(By.xpath(elementXpath + "/following-sibling::ul/li"));
        if (numberOfSubs > 0) {
            Assert.assertEquals(numberOfSubs, subItems.size(), "Количество элементов подменю отличается для " + itemText);
        } else {
            if (!subItems.isEmpty()) {
                Assert.fail("Подменю должно отсутствовать для " + itemText);
            }
        }
    }

    @Test
    public void testMenuHoverColorAndSubmenuVisibility() {
        driver.get(MENU_URL);

        verifyColorChangeOnHover("//ul[@id='nav']//a[text()='Main Item 1']", 0);
        verifyColorChangeOnHover("//ul[@id='nav']//a[text()='Main Item 3']", 0);
        verifyColorChangeOnHover("//ul[@id='nav']//a[text()='Main Item 2']", 3);
        verifyColorChangeOnHover("//ul[@id='nav']//li[a[text()='Sub Item']][1]/a", 0);
        verifyColorChangeOnHover("//ul[@id='nav']//li[a[text()='Sub Item']][2]/a", 0);
        verifyColorChangeOnHover("//ul[@id='nav']//a[text()='SUB SUB LIST »']", 2);
    }
}
