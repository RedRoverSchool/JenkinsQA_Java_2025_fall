package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import java.util.Random;

import static org.testng.Assert.*;

public class GroupLoopCoreTest {
    private static final String HOME_PAGE = "https://bonigarcia.dev/selenium-webdriver-java/";
    private static final String WEB_FORM_PAGE = "//a[@href = 'web-form.html']";
    private static final String WEB_FORM_URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
    private static final String HOME_PAGE_TITLE = "//h1[@class='display-4']";
    private static final String SUBMITTED_PAGE_URL = "/submitted-form.html";
    private WebDriverWait wait;
    private WebDriver driver;

    @BeforeSuite
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(HOME_PAGE);
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        String title = driver.findElement(By.xpath(HOME_PAGE_TITLE)).getText();
        wait.until(ExpectedConditions.titleIs(title));
        driver.findElement(By.xpath(WEB_FORM_PAGE)).click();
    }

    @AfterSuite
    void tearDown() {
        driver.quit();
    }

    public static String readTextFileUtils(String filePath) throws IOException {
        return Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
    }

    @Test(groups = "Chapter3_WebForm", priority = 1)
    void inputTextTest() {
        Assert.assertEquals(driver.getCurrentUrl(), WEB_FORM_URL, "Unexpected link!");
        WebElement textInput = driver.findElement(By.xpath("//input[@id='my-text-id']"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        textInput.sendKeys("textInput");
        wait.until(ExpectedConditions.attributeContains(textInput, "value", "textInput"));

        String actual = textInput.getAttribute("value");
        String expected = "textInput";

        Assert.assertEquals(actual, expected, "Text not equal!");

        textInput.clear();
        String actualEmpty = textInput.getAttribute("value");

        Assert.assertEquals(actualEmpty, "", "Input field must be empty!");

        WebElement textInputTitle = driver.findElement(By.xpath("//label[@class='form-label w-100']"));
        String actualTitle = textInputTitle.getText();
        String expectedTitle = "Text input";
        Assert.assertEquals(actualTitle, expectedTitle, "Text input titles are not equals");
    }

    @Test(groups = "Chapter3_WebForm", priority = 2)
    void passwordInputTest() {
        Assert.assertEquals(driver.getCurrentUrl(), WEB_FORM_URL, "Unexpected link!");
        WebElement passwordInput = driver.findElement(By.xpath("//input[@type='password']"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        passwordInput.sendKeys("password");
        wait.until(ExpectedConditions.attributeContains(passwordInput, "value", "password"));

        String actual = passwordInput.getAttribute("value");
        String expected = "password";

        Assert.assertEquals(actual, expected, "Passwords are not equal!");

        passwordInput.clear();
        String actualEmpty = passwordInput.getAttribute("value");

        Assert.assertEquals(actualEmpty, "", "Password field must be empty!");
    }

    @Test(groups = "Chapter3_WebForm", priority = 3)
    void textAreaInputTest() throws IOException {
        Assert.assertEquals(driver.getCurrentUrl(), WEB_FORM_URL, "Unexpected link!");
        String filePath = "src/resources/DanilovaE/TextField.txt";
        WebElement textAreaInput = driver.findElement(By.xpath("//textarea[@name='my-textarea']"));

        String expected = readTextFileUtils(filePath);
        textAreaInput.sendKeys(expected);
        String actual = textAreaInput.getAttribute("value");
        String normExpected = expected.replace("\r\n", "\n");
        assert actual != null;
        String normActual = actual.replace("\r\n", "\n");

        Assert.assertEquals(normActual, normExpected, "Text in TextArea isn't equal!");

        textAreaInput.clear();
        String actualEmpty = textAreaInput.getAttribute("value");

        Assert.assertEquals(actualEmpty, "", "TextArea field must be empty!");
    }

    @Test(groups = "Chapter3_WebForm", priority = 4)
    void disabledInputTest() {
        Assert.assertEquals(driver.getCurrentUrl(), WEB_FORM_URL, "Unexpected link!");
        WebElement disabledInput = driver.findElement(By.xpath("//input[@placeholder='Disabled input']"));

        Assert.assertTrue(disabledInput.isDisplayed(), "field is not disabled!!!");
        Assert.assertThrows(ElementNotInteractableException.class, () -> disabledInput.sendKeys("disabled"));
    }

    @Test(groups = "Chapter3_WebForm", priority = 5)
    void readOnlyInputTest() {
        Assert.assertEquals(driver.getCurrentUrl(), WEB_FORM_URL, "Unexpected link!");
        WebElement readOnlyInput = driver.findElement(By.xpath("//input[@name='my-readonly']"));

        String expectedPlaceholder = "Readonly input";
        String actualPlaceholder = readOnlyInput.getAttribute("value");
        Assert.assertEquals(actualPlaceholder, expectedPlaceholder, "Text must be \"Readonly input\"");

        readOnlyInput.sendKeys("readOnly");
        String actualText = readOnlyInput.getText();
        Assert.assertEquals(actualText, "", "Text typing is impossible!!!");
    }

    @DataProvider(name = "getDropDownOptionsFromFile")
    public static Object[][] getDropDownOptionsFromFile() throws IOException {
        final String filePath = "src/resources/DanilovaE/DropDownSelect.txt";
        List<String> options = Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8)
                .stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        Object[][] data = new Object[options.size()][2];
        for (int i = 0; i < options.size(); i++) {
            data[i][0] = i;
            data[i][1] = options.get(i);
        }
        return data;
    }

    @Test(groups = "Chapter3_WebForm", dataProvider = "getDropDownOptionsFromFile", priority = 6)
    void dropDownSelectTest(int index, String expectedName) {

        Assert.assertEquals(driver.getCurrentUrl(), WEB_FORM_URL, "Unexpected link!");

        List<WebElement> optionsSelect = driver.findElements(By.xpath("//select[@class='form-select']/option[@value]"));
        String actual = optionsSelect.get(index).getText().trim();

        Assert.assertEquals(actual, expectedName, "option select name doesn't much");
    }

    @Test(groups = "Chapter3_WebForm", priority = 7)
    void checkDropDownSelectPlaceholderTest() {
        final WebElement dropDownSelectPlaceholder = driver.findElement(By.xpath("//select[@class='form-select']"));
        String expectedDropDownSelectPlaceholder = "Open this select menu";
        String actualDropDownSelectPlaceholder = dropDownSelectPlaceholder.getAttribute("value");

        Assert.assertEquals(actualDropDownSelectPlaceholder, expectedDropDownSelectPlaceholder, "Place holder must be \"Open this select menu\" ");
    }

    @Test(groups = "Chapter3_WebForm", dataProvider = "getDropDownOptionsFromFile", priority = 8)
    void clickDropDownSelectTest(int index, String expected) {

        List<WebElement> optionsSelect = driver.findElements(By.xpath("//select[@class='form-select']/option[@value]"));
        WebElement host = driver.findElement(By.cssSelector("select.form-select"));

        optionsSelect.get(index).click();
        String actual = host.getShadowRoot().findElement(By.cssSelector("div[aria-hidden='true']")).getText();
        Assert.assertEquals(actual, expected, "Error");
    }

    @DataProvider(name = "getDropDownListOptions")
    public static Object[][] getDropDownListOptions() throws IOException {
        final String filePath = "src/resources/DanilovaE/DropDownList.txt";
        List<String> optionLists = Files.readAllLines(Path.of(filePath))
                .stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        Object[][] data = new Object[optionLists.size()][2];
        for (int i = 0; i < optionLists.size(); i++) {
            data[i][0] = i;
            data[i][1] = optionLists.get(i);
        }
        return data;
    }

    @Test(groups = "Chapter3_WebForm", dataProvider = "getDropDownListOptions", priority = 9)
    void checkDropDownListNameTest(int index, String expectedName) {
        Assert.assertEquals(driver.getCurrentUrl(), WEB_FORM_URL, "Unexpected link!");
        List<WebElement> optionsList = driver.findElements(By.xpath("//datalist/option"));
        String actual = optionsList.get(index).getAttribute("value");

        Assert.assertEquals(actual, expectedName, "option list name doesn't much");
    }

    @Test(groups = "Chapter3_WebForm", priority = 10)
    void checkDropDownListPlaceholderTest() {

        final WebElement host = driver.findElement(By.cssSelector("input[name='my-datalist']"));
        final String expectedDropDownListPlaceholder = "Type to search...";
        String actualDropDownListPlaceholder = host.getShadowRoot().findElement(By.cssSelector("div#placeholder")).getText();
        Assert.assertEquals(actualDropDownListPlaceholder, expectedDropDownListPlaceholder, "Place holder must be \"Type to search...\" ");
    }

    @Test(groups = "Chapter3_WebForm", priority = 11)
    void enterDropDownListTest() {

        WebElement host = driver.findElement(By.cssSelector("input[name='my-datalist']"));
        String expected = "AN";
        host.sendKeys(expected);
        String actual = host.getShadowRoot().findElement(By.cssSelector("div#editing-view-port > div")).getText();

        Assert.assertEquals(actual, expected, "Error");
        host.clear();
    }

    @Test(groups = "Chapter3_WebForm", priority = 12)
    void fileTextFieldTest() {
        WebElement host = driver.findElement(By.cssSelector("input[type='file']"));
        WebElement fileNotChosenText = host.getShadowRoot().findElement(By.cssSelector("span[aria-hidden='true']"));
        String expected = "No file chosen";
        Assert.assertEquals(fileNotChosenText.getText(), expected, "Another text");
        Path path = Paths.get("src/resources/DanilovaE/FileFieldTest.txt");

        String absolutePath = path.toAbsolutePath().toString();
        host.sendKeys(absolutePath);

        String actualText = fileNotChosenText.getText();
        String expectedFileName = path.getFileName().toString();
        Assert.assertEquals(actualText, expectedFileName, "Another file name!");
    }

    @Test(groups = "Chapter3_WebForm", priority = 13)
    void checkBoxTest() {
        List<WebElement> checkBoxList = driver.findElements(By.xpath("//input[@type='checkbox']"));

        Assert.assertEquals(checkBoxList.size(), 2, "Not equal 2");
        Assert.assertTrue(checkBoxList.get(0).isSelected(), "checkBox must be checked!");

        WebElement checkBox2 = driver.findElement(By.xpath("//input[@id='my-check-2']"));
        WebElement checkBox1 = driver.findElement(By.xpath("//input[@id='my-check-1']"));
        checkBox2.click();
        Assert.assertTrue(checkBox2.isSelected(), "checkBox #2 must be checked!");
        checkBox2.click();
        Assert.assertFalse(checkBox2.isSelected(), "checkBox #2 must be not checked!");
        checkBox1.click();
        Assert.assertFalse(checkBox1.isSelected(), "checkBox #1 must be not checked!");
        checkBox1.click();
    }

    @Test(groups = "Chapter3_WebForm", priority = 14)
    void checkRadioTest() {
        List<WebElement> radios = driver.findElements(By.xpath("//input[@type='radio']"));
        Assert.assertEquals(radios.size(), 2, "Not equal 2");
        Assert.assertTrue(radios.get(0).isSelected(), "radio button not selected!");
        WebElement radio2 = driver.findElement(By.xpath("//input[@id='my-radio-2']"));
        WebElement radio1 = driver.findElement(By.xpath("//input[@id='my-radio-1']"));
        radio2.click();
        Assert.assertTrue(radio2.isSelected(), "radio2 is not selected");
        Assert.assertFalse(radio1.isSelected(), "radio1 is not selected");
        radio1.click();
    }

    @Test(groups = "Chapter3_WebForm", priority = 15)
    @Ignore
    void changeColorPickerTest() {
        WebElement host = driver.findElement(By.cssSelector("input[type='color']"));
        WebElement colorPickerButton = host.getShadowRoot().findElement(By.cssSelector("div > div"));
        Rectangle rect = colorPickerButton.getRect();

        int width = rect.width;
        int height = rect.height;

        int centrX = width / 2;
        int centrY = height / 2;

        int rightX = centrX + 10;
        int downY = centrY + 50;

        System.out.println("rightX = " + rightX + "; " + "downY = " + downY);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(colorPickerButton));

        colorPickerButton.click();

        Actions actions = new Actions(driver);

        actions
                .moveToElement(colorPickerButton)
                .moveToLocation(rightX, downY)
                .click()
                .perform();
    }

    @Test(groups = "Chapter3_WebForm", priority = 16)
    void DataPickerTest() {
        WebElement host = driver.findElement(By.cssSelector("input[name='my-date']"));
        WebElement dataResult = host.getShadowRoot().findElement(By.cssSelector("div"));
        String actual = dataResult.getText();
        String expected = "";
        Assert.assertEquals(actual, expected, "Field must be empty!");


        String expectet2 = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        host.sendKeys(expectet2);
        String actual2 = dataResult.getText();
        Assert.assertEquals(actual2, expectet2, "Field must have date!");
    }

    @Test(groups = "Chapter3_WebForm", priority = 17)
    void ExampleRangeTest() {
        WebElement rangeHost = driver.findElement(By.cssSelector("input[type='range']"));
        int width = rangeHost.getShadowRoot().findElement(By.cssSelector("div")).getRect().width;

        String startPositionThumb = rangeHost.getShadowRoot().findElement(By.cssSelector("div > div > div[id='thumb']")).getRect().getPoint().toString();

        double percent = 1.0;
        int xFromLeft = (int) (width * percent);
        int xOffsetFromCenter = xFromLeft - width / 2;
        Actions actions = new Actions(driver);
        actions
                .moveToElement(rangeHost.getShadowRoot().findElement(By.cssSelector("div > div > div[id='thumb']")), xOffsetFromCenter, 0)
                .click()
                .perform();

        String nextPositionThumb = rangeHost.getShadowRoot().findElement(By.cssSelector("div > div > div[id='thumb']")).getRect().getPoint().toString();
        Assert.assertNotEquals(nextPositionThumb, startPositionThumb, "Thumb wasn't move");

        actions.clickAndHold(rangeHost.getShadowRoot().findElement(By.cssSelector("div > div > div[id='thumb']"))).moveByOffset(-100, 0).release().perform();
        String holdPositionThumb = rangeHost.getShadowRoot().findElement(By.cssSelector("div > div > div[id='thumb']")).getRect().getPoint().toString();

        Assert.assertNotEquals(holdPositionThumb, startPositionThumb, "Thumb wasn't move");
    }

    @Test(groups = "Daria", priority = 200)
    public void testWebForm() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        driver.findElement(By.xpath("//a[text()='Web form']")).click();
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("web-form"), "ссылка должна содержать 'web-form'");

        driver.quit();
    }

    @Test(groups = "Boich_WebForm", priority = 18)
    public void testTextInput() {
        WebElement input = driver.findElement(By.xpath("//input[@id='my-text-id']"));
        input.click();
        input.sendKeys("Go !!!");

        String actualValue = input.getAttribute("value");
        Assert.assertEquals(actualValue, "Go !!!");
        input.clear();
    }

    @Test(groups = "Boich_WebForm", priority = 19)
    public void testPassword() {
        WebElement password = driver.findElement(By.name("my-password"));
        password.click();
        password.sendKeys("Java!");

        String actualValue = password.getAttribute("value");
        Assert.assertEquals(actualValue, "Java!");
        password.clear();
    }

    @Test(groups = "Boich_WebForm", priority = 20)
    public void testTextarea() {
        WebElement textarea = driver.findElement(By.name("my-textarea"));
        textarea.click();
        textarea.sendKeys("Java! Forever !!");

        String actualValue = textarea.getAttribute("value");
        Assert.assertEquals(actualValue, "Java! Forever !!");
    }

    @Test(groups = "Boich_WebForm", priority = 21)
    public void testDisabledInput() {
        WebElement disabled = driver.findElement(By.name("my-disabled"));
        disabled.click();

        Assert.assertTrue(disabled.isDisplayed(), "Поле не задизейблено");
    }

    @Test(groups = "Boich_WebForm", priority = 22)
    public void testReadonlyInput() {
        WebElement readOnlyInput = driver.findElement(By.name("my-readonly"));
        readOnlyInput.click();

        String expectedPlaceholder = "Readonly input";
        String actualPlaceholder = readOnlyInput.getAttribute("value");
        Assert.assertEquals(actualPlaceholder, expectedPlaceholder, "Текст должен быть 'Readonly input'");
    }

    @Test(groups = "Boich_WebForm", priority = 23)
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

    @Test(groups = "Boich_WebForm", priority = 24)
    public void testDropdownDatalist() {
        WebElement datalist = driver.findElement(By.name("my-datalist"));
        datalist.click();

        List<String> options = List.of(
                "San Francisco", "New York", "Seattle", "Los Angeles", "Chicago"
        );

        Random random = new Random();

        String expectedValue = options.get(random.nextInt(options.size()));

        datalist.sendKeys(expectedValue);

        String actualValue = datalist.getAttribute("value");
        Assert.assertEquals(actualValue, expectedValue, "Error");
        datalist.clear();
    }

    @Test(groups = "Boich_WebForm", priority = 25)
    public void testChexBox() {
        List<WebElement> chexBoxList = driver.findElements(By.xpath("//input[@type='checkbox']"));

        Assert.assertEquals(chexBoxList.size(), 2, "Not == 2");
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

    @Test(groups = "Boich_WebForm", priority = 26)
    public void testButton() {
        List<WebElement> radios = driver.findElements(By.xpath("//input[@type='radio']"));
        Assert.assertEquals(radios.size(), 2, "Нет 2 кнопок");
        Assert.assertTrue(radios.get(0).isSelected(), "Кнопка не выбрана");

        WebElement radio1 = driver.findElement(By.id("my-radio-1"));
        WebElement radio2 = driver.findElement(By.id("my-radio-2"));
        radio2.click();

        Assert.assertFalse(radio1.isSelected(), "Кнопка1 не выбрана");
        Assert.assertTrue(radio2.isSelected(), "Кнопка2 не выбрана");
        radio1.click();
    }

    @Ignore
    @Test(groups = "Boich_WebForm", priority = 100)
    public void testAllPageSubmit() {
        WebElement resultForm = driver.findElement(By.xpath("//h1[text()='Form submitted']"));
        Assert.assertEquals(resultForm.getText(), "Form submitted");
    }

    @Test(groups = "Chapter3_WebForm", priority = 150)
    void submitButtonTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
        wait.until(ExpectedConditions.urlContains(SUBMITTED_PAGE_URL));
        String currentUrl = driver.getCurrentUrl();

        assert currentUrl != null;
        Assert.assertTrue(currentUrl.contains(SUBMITTED_PAGE_URL), "Something wrong!");

        WebElement title = driver.findElement(By.xpath("//h1[@class='display-6']"));

        wait.until(ExpectedConditions.visibilityOf(title));

        String actualTitle = title.getText();

        String expectedTitle = "Form submitted";

        Assert.assertEquals(actualTitle, expectedTitle, "Title is wrong!");
    }

    @Test(groups = "Alexandra", priority = 28)
    public void textInputTest() {
        String textExample = "test";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("my-text-id")));
        WebElement textInput = driver.findElement(By.id("my-text-id"));
        textInput.sendKeys(textExample);
        assertEquals(textInput.getAttribute("value"), textExample, "Input data is not equal");
    }

    @Test(groups = "Alexandra", priority = 29)
    public void psswrdInputTest() {
        String passwordExample = "123456";
        WebElement passwordInput = driver.findElement(By.xpath("//input[@type='password']"));
        passwordInput.sendKeys(passwordExample);
        assertEquals(passwordInput.getAttribute("value"), passwordExample, "Input data is not equal");
    }

    @Test(groups = "Alexandra", priority = 30)
    public void scrollBarAppearTextAreaTest() {
        String textExample = "1\n 2\n 3\n 4\n";
        WebElement textArea = driver.findElement(By.cssSelector(".form-control[name='my-textarea']"));
        long cleanTextArea = Long.parseLong(textArea.getAttribute("scrollHeight"));
        textArea.sendKeys(textExample);
        long fullTextArea = Long.parseLong(textArea.getAttribute("scrollHeight"));
        assertTrue(cleanTextArea < fullTextArea);
    }

    @Test(groups = "Alexandra", priority = 31)
    public void dropdownSelectTest() {
        WebElement dropdown = driver.findElement(By.cssSelector(".form-select[name='my-select']"));
        dropdown.findElement(By.cssSelector(".form-select option:nth-child(3)")).click();

        assertEquals(dropdown.getAttribute("value"), "2");
    }

    @Test(groups = "Alexandra", priority = 32)
    public void dropdownDatalistTest() {
        WebElement cities = driver.findElement(By.id("my-options"));
        List<WebElement> options = cities.findElements(By.tagName("option"));
        List<String> citiesList = new ArrayList<>();
        for (WebElement element : options) {
            citiesList.add(element.getAttribute("value"));
        }

        WebElement dropdownDatalist = driver.findElement(By.xpath("//input[@name='my-datalist']"));
        dropdownDatalist.sendKeys(citiesList.get(0));
        assertEquals(dropdownDatalist.getAttribute("value"), "San Francisco");
    }

    @Ignore
    @Test(groups = "Alexandra", priority = 33)
    public void fileInputTest() {
        String filepath = "src/resources/DanilovaE/FileFieldTest.txt";

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement fileInput = driver.findElement(By.xpath("//input[@name='my-file']"));
        fileInput.sendKeys(filepath);

        assertTrue(fileInput.getAttribute("value").contains("FileFieldTest.txt"), "File is not attached");
    }

    @Test(groups = "Alexandra", priority = 34)
    public void checkboxesTest() {
        WebElement chekedCheckbox = driver.findElement(By.xpath("//input[@id='my-check-1']"));
        boolean one = chekedCheckbox.isSelected();
        chekedCheckbox.click();
        assertNotEquals(chekedCheckbox.isSelected(), one, "Checked checkbox has an error");

        WebElement defaultCheckbox = driver.findElement(By.xpath("//input[@id='my-check-2']"));
        boolean two = defaultCheckbox.isSelected();
        defaultCheckbox.click();
        assertNotEquals(defaultCheckbox.isSelected(), two, "Default checkbox has an error");
    }

    @Test(groups = "Alexandra", priority = 35)
    public void radioButtonTest() {
        WebElement radio1 = driver.findElement(By.xpath("//input[@id='my-radio-1']"));
        boolean one = radio1.isSelected();
        WebElement radio2 = driver.findElement(By.xpath("//input[@id='my-radio-2']"));
        boolean two = radio2.isSelected();
        radio2.click();

        assertNotEquals(radio1.isSelected(), one, "RadioButton 1 didn't change the condition");
        assertNotEquals(radio2.isSelected(), two, "RadioButton 2 didn't change the condition");
    }

    @Ignore
    @Test(groups = "Alexandra", priority = 36)
    public void submitBttnTest() {
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        WebElement submitPageName = driver.findElement(By.xpath("//h1[text()='Form submitted']"));
        assertEquals(submitPageName.getText(), "Form submitted");
    }
}
