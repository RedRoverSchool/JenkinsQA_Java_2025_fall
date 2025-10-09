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
import java.util.List;

public class DanilovaETest {
    private static final String HOME_PAGE = "https://bonigarcia.dev/selenium-webdriver-java/";
    private static final String WEB_FORM_PAGE = "//a[@href = 'web-form.html']";
    private static final String WEB_FORM_URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
    private static final String HOME_PAGE_TITLE = "//h1[@class='display-4']";
    private static final String SUBMITTED_PAGE_URL = "/submitted-form.html";
    private WebDriverWait wait;
    private WebDriver driver;

    @BeforeClass
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(HOME_PAGE);
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        String title = driver.findElement(By.xpath(HOME_PAGE_TITLE)).getText();
        wait.until(ExpectedConditions.titleIs(title));
    }

    @BeforeGroups("Chapter3_WebForm")
    void openWebForm() {
        driver.findElement(By.xpath(WEB_FORM_PAGE)).click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
    }

    @AfterClass
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

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
    }

    @Test(groups = "Chapter3_WebForm", priority = 15)
    @Ignore
    void changeColorPickerTest(){
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
    void DataPickerTest(){
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

    @Test(groups = "Chapter3_WebForm", priority = 18)
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
}
