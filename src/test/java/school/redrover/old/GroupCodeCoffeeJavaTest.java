package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Ignore
public class GroupCodeCoffeeJavaTest {
    protected WebDriver driver;
    SoftAssert softAssert = new SoftAssert();
    private static final Logger logger = Logger.getGlobal();
    private int time;
    WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(4));


    public void getActionsScroll() {
        Actions actions = new Actions(driver);
        actions.scrollByAmount(600,600);
        actions.perform();
    }

    @BeforeMethod
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://demoqa.com");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (result.isSuccess()) {
            System.out.println("✅ Test PASSED: " + result.getName());
            logger.info(String.format("✅ Test PASSED: %s", result.getName()));

        } else if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("❌ Test FAILED: " + result.getName());
            logger.warning(String.format("❌ Test FAILED: %s", result.getName()));

        } else if (result.getStatus() == ITestResult.SKIP) {
            System.out.println("⚠️ Test SKIPPED: " + result.getName());
            logger.warning(String.format("⚠️ Test SKIPPED: %s", result.getName()));
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testDemoQa() {
        WebElement elements = driver.findElement(By.xpath("(//div[@class = 'card-up'])[1]"));
        wait4.until(ExpectedConditions.visibilityOf(elements)).click();

        WebElement textBox = driver.findElement(By.xpath("(//ul[@class='menu-list'])[1]//li[1]"));
        wait4.until(ExpectedConditions.visibilityOf(textBox)).click();

        WebElement fullName = driver.findElement(By.id("userName"));
        fullName.sendKeys("Алиса Т.");

        WebElement email = driver.findElement(By.cssSelector("#userEmail"));
        email.sendKeys("Alisya152@gmail.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        WebElement submitButton = driver.findElement(By.cssSelector("#submit"));
        // Боремся со всплывающими окнами с помощью actions
        getActionsScroll();
        submitButton.click();

        WebElement resultName = driver.findElement(By.cssSelector("#name"));
        WebElement resultEmail = driver.findElement(By.cssSelector("#email"));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        // Проверяем правильность текста
        Assert.assertEquals(resultName.getText(), "Name:Алиса Т.");
        Assert.assertEquals(resultEmail.getText(), "Email:Alisya152@gmail.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }
    @Test
    public void testGoToElements(){

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getActionsScroll();
        driver.findElement(By.xpath("(//h5)[1]")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/elements");
        String elementText = driver.findElement(By.xpath("(//div[@class='header-text'])[1]")).getText();
        Assert.assertEquals(elementText, "Elements");
    }
   @Test
    public void testSelectTextBox(){
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
       getActionsScroll();
       driver.findElement(By.xpath("(//h5)[1]")).click();
       String url = driver.getCurrentUrl();
       Assert.assertEquals(url, "https://demoqa.com/elements");
       driver.findElement(By.xpath("//span[text()='Text Box']")).click();
       Assert.assertEquals(driver.findElement(By.xpath("//h1")).getText(), "Text Box");
   }

   @Test
    public void testTextBox() {

       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       WebElement firstCard = wait.until(ExpectedConditions.elementToBeClickable(
               By.xpath("(//div[@class='card mt-4 top-card'])[1]")
       ));
       firstCard.click();
       driver.findElement(By.xpath("//span[text()='Text Box']")).click();

       driver.findElement(By.id("userName")).sendKeys("Ivan Ivanov");
       driver.findElement(By.id("userEmail")).sendKeys("Ivan@gmail.com");
       driver.findElement(By.id("currentAddress")).sendKeys("Mosсow, str. Parkovaya, 20");
       driver.findElement(By.id("permanentAddress")).sendKeys("Mosсow, str. Parkovaya, 22");
       WebElement submitBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
               By.id("submit")
       ));

// Клик через JavaScript
       JavascriptExecutor js = (JavascriptExecutor) driver;
       js.executeScript("arguments[0].click();", submitBtn);
       Assert.assertTrue(driver.findElement(By.id("output")).isDisplayed());
       Assert.assertEquals(driver.findElement(By.id("name")).getText(), "Name:Ivan Ivanov");
       Assert.assertEquals(driver.findElement(By.id("email")).getText(), "Email:Ivan@gmail.com");
   }
       @Test
       public void testAddLine(){
           WebElement elements = driver.findElement(By.xpath("(//div[@class = 'card-up'])[1]"));
           wait4.until(ExpectedConditions.visibilityOf(elements)).click();

           WebElement textBox = driver.findElement(By.xpath("(//ul[@class='menu-list'])[1]//li[4]"));
           wait4.until(ExpectedConditions.visibilityOf(textBox)).click();

           driver.findElement(By.id("addNewRecordButton")).click();
           driver.findElement(By.id("firstName")).sendKeys("Petr");
           driver.findElement(By.id("lastName")).sendKeys("Petrov");
           driver.findElement(By.id("userEmail")).sendKeys("Petrov@gmail.com");
           driver.findElement(By.id("age")).sendKeys("25");
           driver.findElement(By.id("salary")).sendKeys("50000");
           driver.findElement(By.id("department")).sendKeys("IT");
           driver.findElement(By.id("submit")).click();

           List<String> expectedValues = Arrays.asList("Petr", "Petrov", "25", "Petrov@gmail.com", "50000", "IT");
           WebElement tableRow = driver.findElement(By.xpath
                   ("//div[@class='rt-tbody']/div[@class='rt-tr-group'][.//div[@class='rt-td' and" +
                           " normalize-space(text()) != '']][last()]"));
           assertTableRowContainsValues(tableRow, expectedValues);

       }
    // Универсальный метод для проверки строки таблицы
    private void assertTableRowContainsValues(WebElement tableRow, List<String> expectedValues) {
        // Находим все ячейки в строке
        List<WebElement> cells = tableRow.findElements(By.xpath(".//div[contains(@class, 'rt-td')]"));

        // Проверяем каждую ячейку
        for (int i = 0; i < Math.min(cells.size(), expectedValues.size()); i++) {
            String actualText = cells.get(i).getText().trim();
            String expectedText = expectedValues.get(i);

            softAssert.assertEquals(actualText, expectedText,
                    "Ячейка " + (i + 1) + " содержит неверное значение");

            // Логирование для отладки
            System.out.println("Ячейка " + (i + 1) + ": '" + actualText + "' == '" + expectedText + "'");
        }
        softAssert.assertAll(); // Выводим все ошибки в конце
    }

    @Test
    public void testCountLine() {
        WebElement elements = driver.findElement(By.xpath("(//div[@class = 'card-up'])[1]"));
        wait4.until(ExpectedConditions.visibilityOf(elements)).click();

        WebElement textBox = driver.findElement(By.xpath("(//ul[@class='menu-list'])[1]//li[4]"));
        wait4.until(ExpectedConditions.visibilityOf(textBox)).click();

        List<WebElement> initialRows = getTableRows();
        int initialCount = initialRows.size();
        System.out.println("Строк до добавления: " + initialCount);

        driver.findElement(By.id("addNewRecordButton")).click();
        driver.findElement(By.id("firstName")).sendKeys("Petr");
        driver.findElement(By.id("lastName")).sendKeys("Petrov");
        driver.findElement(By.id("userEmail")).sendKeys("Petrov@gmail.com");
        driver.findElement(By.id("age")).sendKeys("25");
        driver.findElement(By.id("salary")).sendKeys("50000");
        driver.findElement(By.id("department")).sendKeys("IT");
        driver.findElement(By.id("submit")).click();

        List<WebElement> finalRows = getTableRows();
        int finalCount = finalRows.size();
        System.out.println("Строк после добавления: " + finalCount);

        // Проверяем, что строк стало на 1 больше
        softAssert.assertEquals(finalCount, initialCount + 1,
                "Количество строк не увеличилось на 1 после добавления записи");

        softAssert.assertAll();
    }
    public List<WebElement> getTableRows() {
        List<WebElement> allRows = driver.findElements(By.xpath("//div[@class='rt-tbody']/div[@class='rt-tr-group']"));

        // Фильтруем только непустые строки
        return allRows.stream()
                .filter(row -> !row.getText().trim().isEmpty())
                .collect(Collectors.toList());
    }

    @Test
    public void buttonsTest() {
        driver.findElement(By.xpath("(//div[@class = 'card-up'])[1]")).click();

        driver.findElement(By.xpath("//span[text()='Buttons']")).click();

        WebElement doubleClickBtn = driver.findElement(By.id("doubleClickBtn"));
        WebElement rightClickBtn = driver.findElement(By.id("rightClickBtn"));
        WebElement singleClickBtn = driver.findElement(By.xpath("//button[text()='Click Me']"));

        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickBtn).perform();
        actions.contextClick(rightClickBtn).perform();
        actions.click(singleClickBtn).perform();

        String msgRightClick = driver.findElement(By.xpath("//p[@id='rightClickMessage']")).getText();
        String msgDoubleClick = driver.findElement(By.xpath("//p[@id='doubleClickMessage']")).getText();
        String msgSingleClick = driver.findElement(By.xpath("//p[@id='dynamicClickMessage']")).getText();

        Assert.assertEquals(msgDoubleClick, "You have done a double click");
        Assert.assertEquals(msgRightClick, "You have done a right click");
        Assert.assertEquals(msgSingleClick, "You have done a dynamic click");
    }

}



