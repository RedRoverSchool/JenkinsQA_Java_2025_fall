package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.Duration.ofMillis;
import static org.openqa.selenium.By.name;

public class GroupUnitedByJavaTest {

    private static final String LOGIN = "admin@admin.com";
    private static final String PASSWORD = "admin123";
    private static final String DEMOQA_URL = "https://demoqa.com/";
    private static final String THECODE_URL ="https://thecode.media/";
    private static final String ALERT_URL = DEMOQA_URL + "alerts";
    private static final String DATE_PICKER_URL = DEMOQA_URL + "date-picker";
    private static final String ACCORDION_URL = DEMOQA_URL + "accordian";
    private static final String BUTTONS_URL = DEMOQA_URL + "buttons";
    private static final String BROWSER_WINDOWS_URL = DEMOQA_URL + "browser-windows";
    private static final String TOOLTIPS_URL = DEMOQA_URL + "tool-tips";
    private static final String MENU_URL = DEMOQA_URL + "menu";
    private static final String MODAL_DIALOGS_URL = DEMOQA_URL + "modal-dialogs";
    private static final String TEST_NAME = "Ivan";  // Для тестов с PromptBox
    private static final String LOGIN_SECRET_SAUCE = "standard_user";
    private static final String PASSWORD_SECRET_SAUCE = "secret_sauce";

    @Test
    public void testDoubleClick() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get("https://demoqa.com/");

        WebElement elementsPage = driver.findElement(By.xpath("//h5[text()='Elements']"));
        elementsPage.click();
        WebElement buttons = driver.findElement(By.xpath("//span[@class='text' and text()='Buttons']"));
        buttons.click();

        WebElement doubleClickMe = driver.findElement(By.id("doubleClickBtn"));
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickMe);
        actions.perform();

        String doubleClickMessage = driver.findElement(By.id("doubleClickMessage")).getText();

        Assert.assertEquals(doubleClickMessage, "You have done a double click");

        driver.quit();
    }

    @Test
    public void testFirstTest() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");

        driver.findElement(By.xpath("/html/body/section[2]/div/div/div[2]/div[1]/div[4]/div/div[2]/ul/li/a")).click();
        driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div[2]/div[2]/div/span/button")).click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("View Cart")).click();

        Assert.assertEquals(driver.findElement(By.xpath("/html/body/section/div/div[2]/table/tbody/tr/td[2]/h4/a")).getText(), "Sleeveless Dress");

        driver.quit();
    }

    @Test
    public void testPositiveLogin() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://qa-practice.netlify.app/auth_ecommerce");
        driver.manage().window().setSize(new Dimension(1920, 1080));
        
        driver.findElement(By.name("emailAddress")).sendKeys(LOGIN);
        driver.findElement(By.name("password")).sendKeys(PASSWORD);
        driver.findElement(By.id("submitLoginBtn")).click();

        Assert.assertTrue(driver.findElement(By.id("logout")).isDisplayed(),
                "Logout button should be displayed after successful login");

        driver.quit();
    }

    @Test
    public void testBackToMainPageLink() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/inventory.html");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.findElement(By.xpath("//div[@class = 'inventory_item_name'][1]")).click();
        driver.findElement(By.xpath("//button[@class = 'inventory_details_back_button']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class = 'product_label']")).getText(),
                "Products");

        driver.quit();
    }

    @Test
    public void testChangeButtonTextAtAdding() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/inventory.html");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.findElement(By.xpath("(//button[@class='btn_primary btn_inventory'])[1]")).click();
        Assert.assertEquals(driver.findElement(By.xpath("(//button[@class='btn_secondary btn_inventory'])[1]"))
                .getText(), "REMOVE");

        driver.quit();
    }

    @Test
    public void testChangeBasketCounterAtAdding() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/inventory.html");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        String basketCounterSelector = "//span[@class = 'fa-layers-counter shopping_cart_badge']";

        Assert.assertTrue(driver.findElements(By.xpath(basketCounterSelector)).isEmpty());

        driver.findElement(By.xpath("(//button[@class='btn_primary btn_inventory'])[1]")).click();
        Assert.assertEquals(driver.findElement(By.xpath(basketCounterSelector)).getText(), "1");

        driver.quit();
    }

    @Test
    public void testHeadhunterVacancyName() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.hh.ru/vacancy/122718353");
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        Assert.assertEquals(driver.findElement(By.cssSelector("h1[data-qa='vacancy-title']")).getText(), "UX-Исследователь (Junior)");

        driver.quit();
    }

    @Test
    public void testDoubleClickBelyaevThecode() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(THECODE_URL);

        WebElement searchArea = driver.findElement(By.className("tab-questions"));

        new Actions(driver).doubleClick(searchArea).perform();

        String foundSearchTitle = driver.findElement(By.className("search__title")).getText();

        Assert.assertEquals(foundSearchTitle, "Как решить");

        driver.quit();
    }

    @Test
    public void testAddCompareProduct() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get("https://naveenautomationlabs.com/opencart/");

        driver.findElement(By.linkText("Cameras")).click();

        WebElement cameraCanon = driver.findElement(By.cssSelector("[onclick*='compare.add'][onclick*='30']"));
        cameraCanon.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement cameraNikon = driver.findElement(By.cssSelector("[onclick*='compare.add'][onclick*='31']"));
        cameraNikon.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        driver.findElement(By.linkText("Product Compare (2)")).click();

        String titleCanon = driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[2]/a")).getText();
        String titleNikon = driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[3]/a")).getText();

        String compareCameras = titleCanon + " " + titleNikon;

        Assert.assertEquals(compareCameras, "Canon EOS 5D Nikon D300");

        driver.quit();
    }

    @Test
    public void testCheckOpenNewBrowserWindow() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(DEMOQA_URL);
        String demoqaWindow = driver.getWindowHandle();

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[3]")).click();

        driver.findElement(By.xpath("//span[text()='Browser Windows']")).click();

        driver.findElement(By.id("windowButton")).click();

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(demoqaWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        String newWindow =  driver.findElement(By.id("sampleHeading")).getText();

        Assert.assertEquals(newWindow, "This is a sample page");

        driver.quit();
    }

    @Test
    public void testCheckSwitchNewTab() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(DEMOQA_URL);

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[3]")).click();
        driver.findElement(By.xpath("//span[text()='Browser Windows']")).click();
        driver.findElement(By.id("tabButton")).click();

        Object[] windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);

        String newTab =  driver.findElement(By.id("sampleHeading")).getText();

        Assert.assertEquals(newTab, "This is a sample page");

        driver.quit();
    }

    @Test
    public void testCheckSwitchBackToMainWindow() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(DEMOQA_URL);

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[3]")).click();
        driver.findElement(By.xpath("//span[text()='Browser Windows']")).click();
        driver.findElement(By.id("tabButton")).click();

        Object[] windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
        driver.close();
        driver.switchTo().window((String) windowHandles[0]);

        String titleMainSite = driver.getTitle();

        Assert.assertEquals(titleMainSite, "DEMOQA");

        driver.quit();
    }

    @Test
    public void testOpenTabsCtrlClick() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(THECODE_URL);
        String theMainCodeWindow = driver.getWindowHandle();
        String requiredTitle = "Железо";

        driver.findElement(By.cssSelector("#menu-item-20644 > a")).sendKeys(Keys.CONTROL, Keys.ENTER);
        driver.findElement(By.cssSelector("#menu-item-20645 > a")).sendKeys(Keys.CONTROL, Keys.ENTER);
        driver.findElement(By.cssSelector("#menu-item-20646 > a")).sendKeys(Keys.CONTROL, Keys.ENTER);
        driver.findElement(By.cssSelector("#menu-item-20647 > a")).sendKeys(Keys.CONTROL, Keys.ENTER);
        driver.findElement(By.cssSelector("#menu-item-20933 > a")).sendKeys(Keys.CONTROL, Keys.ENTER);
        driver.findElement(By.cssSelector("#menu-item-20932 > a")).sendKeys(Keys.CONTROL, Keys.ENTER);

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        Assert.assertEquals(tabs.size(), 7);
        int reqTabs = 0;

        for (int i = 1; i < tabs.size(); i++) {
            driver.switchTo().window(tabs.get(i));
            String searchTitle = driver.findElement(By.className("search__title")).getText();

            if (searchTitle.toLowerCase().contains(requiredTitle.toLowerCase())){
                reqTabs++;
            }
        }

        Assert.assertTrue(reqTabs > 0, "Вкладки имеют название " + requiredTitle);

        driver.close();
        driver.switchTo().window((theMainCodeWindow));

        driver.quit();
    }

    @Test
    public void testCheckSwitchNewTabUseTab() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(THECODE_URL);
        String thecodeWindow = driver.getWindowHandle();

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(THECODE_URL + "howto/");

        String titleOpenTab =  driver.findElement(By.className("search__title")).getText();

        Assert.assertEquals(titleOpenTab, "Это как");

        driver.close();
        driver.switchTo().window((thecodeWindow));
        driver.quit();
    }

    @Test
    public void testCheckOpenAlertOK() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(DEMOQA_URL);

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[3]")).click();
        driver.findElement(By.xpath("//span[text()='Alerts']")).click();
        driver.findElement(By.id("confirmButton")).click();

        Alert alert = driver.switchTo().alert();
        alert.accept();

        String confirmOK = driver.findElement(By.id("confirmResult")).getText();

        Assert.assertEquals(confirmOK, "You selected Ok");

        driver.quit();
    }

    @Test
    public void testClickRightMouseButton(){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(DEMOQA_URL);

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[1]")).click();
        driver.findElement(By.xpath("//span[text()='Buttons']")).click();

        WebElement rightClickButton = driver.findElement(By.id("rightClickBtn"));
        new Actions(driver).contextClick(rightClickButton).perform();

        String textMessage = driver.findElement(By.id("rightClickMessage")).getText();
        Assert.assertTrue(textMessage.contains("right click"));

        driver.quit();
    }

    @Test
    public void testCheckFieldToolTip() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(DEMOQA_URL);

        driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/div/div[4]")).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.documentElement.scrollHeight/2);");

        driver.findElement(By.xpath("//span[text()='Tool Tips']")).click();

        new Actions(driver).moveToElement(driver.findElement(By.id("toolTipTextField"))).perform();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        String hoverText = driver.findElement(By.id("textFieldToolTip")).getText();

        Assert.assertEquals(hoverText, "You hovered over the text field");

        driver.quit();
    }

    @Test
    public void testFullOrderProduct(){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get("https://www.saucedemo.com/v1/inventory.html");

        driver.findElement(By.linkText("Sauce Labs Backpack")).click();

        driver.findElement(By.className("btn_primary")).click();
        driver.findElement(By.className("shopping_cart_container")).click();
        driver.findElement(By.className("checkout_button")).click();

        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Smith");
        driver.findElement(By.id("postal-code")).sendKeys("123456");

        driver.findElement(By.className("cart_button")).click();
        driver.findElement(By.className("btn_action")).click();

        String orderMessage = driver.findElement(By.className("complete-header")).getText();

        Assert.assertEquals(orderMessage, "THANK YOU FOR YOUR ORDER");

        driver.quit();
    }

    @Test
    public void testRegisterUser(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        SoftAssert softAssert = new SoftAssert();

        // Verify that home page is visible successfully
        WebElement logo = driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-4 > div > a > img"));
        softAssert.assertTrue(logo.isDisplayed(), "Page is not visible");

        // Click on 'Signup / Login' button
        driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(4) > a")).click();

        // Verify 'New User Signup!' is visible
        WebElement textNewUser = driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > h2"));
        softAssert.assertTrue(textNewUser.isDisplayed(),"New User Signup is not visible");

        // Enter name and email address
        WebElement nameField = driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > form > input[type=text]:nth-child(2)"));
        nameField.sendKeys("Dariya");

        WebElement emailField = driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > form > input[type=email]:nth-child(3)"));
        emailField.sendKeys("testdariya@gmail.com");

        //Click 'Signup' button
        driver.findElement(By.cssSelector("#form > div > div > div:nth-child(3) > div > form > button")).click();

        // Verify that 'ENTER ACCOUNT INFORMATION' is visible
        softAssert.assertTrue(driver.findElement(By.cssSelector("#form > div > div > div > div > h2 > b")).isDisplayed(), "ENTER ACCOUNT INFORMATION is not visible");

        // Fill details: Title, Name, Email, Password, Date of birth
        // Check if the radio button "Mrs." selected or not
        if(!driver.findElement(By.cssSelector("#id_gender2")).isSelected()){
            driver.findElement(By.cssSelector("#id_gender2")).click();
        }

        // Check is the fields is filled with correct data
        String valueNameField = driver.findElement(By.cssSelector("#name")).getAttribute("value");
        softAssert.assertEquals(valueNameField, "Dariya");

        String valueEmailField = driver.findElement(By.cssSelector("#email")).getAttribute("value");
        softAssert.assertEquals(valueEmailField, "testdariya@gmail.com");

        // Fill password
        driver.findElement(By.cssSelector("#password")).sendKeys("123Password");

        // Check day, month, year in dropdown
        Select daySelect = new Select(driver.findElement(By.cssSelector("#days")));
        daySelect.selectByValue("15");

        Select monthSelector = new Select(driver.findElement(By.cssSelector("#months")));
        monthSelector.selectByValue("9");

        Select yearSelect = new Select(driver.findElement(By.cssSelector("#years")));
        yearSelect.selectByValue("2000");

        //Scroll 300px
        Actions actions = new Actions(driver);
        actions.scrollByAmount(0, 300).perform();

        // Select checkbox 'Sign up for our newsletter!'
        driver.findElement(By.cssSelector("#newsletter")).click();

        // Select checkbox 'Receive special offers from our partners!'
        driver.findElement(By.cssSelector("#optin")).click();

        //Scroll 1000px
        actions.scrollByAmount(0, 1000).perform();

        //Fill details: First name, Last name, Company, Address, Address2,
        // Country, State, City, Zipcode, Mobile Number
        driver.findElement(By.cssSelector("#first_name")).sendKeys("Dariya");
        driver.findElement(By.cssSelector("#last_name")).sendKeys("Zubovich");
        driver.findElement(By.cssSelector("#company")).sendKeys("Company X");
        driver.findElement(By.cssSelector("#address1")).sendKeys("123 Maple Street, P.O. Box 789, Company X");

        Select countrySelect = new Select(driver.findElement(By.cssSelector("#country")));
        countrySelect.selectByValue("Canada");

        driver.findElement(By.cssSelector("#state")).sendKeys("Alberta");
        driver.findElement(By.cssSelector("#city")).sendKeys("Brooks");
        driver.findElement(By.cssSelector("#zipcode")).sendKeys("12345-6789");
        driver.findElement(By.cssSelector("#mobile_number")).sendKeys("+1 (437) 555-0001");

        //Scroll 500px
        actions.scrollByAmount(0, 500).perform();

        //  Click 'Create Account button'
        driver.findElement(By.cssSelector("#form > div > div > div > div > form > button")).click();

        // Verify that 'ACCOUNT CREATED!' is visible
        softAssert.assertTrue(driver.findElement(By.cssSelector("#form > div > div > div > h2 > b")).isDisplayed(),"Message is not visible");

        // Click 'Continue' button
        driver.findElement(By.cssSelector("#form > div > div > div > div > a")).click();

        // Verify that 'Logged in as username' is visible
        softAssert.assertTrue(driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(10) > a")).isDisplayed(),"Logged in as Dariya is not displayed");

        // Click 'Delete Account' button
        driver.findElement(By.cssSelector("#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(5) > a")).click();

        // Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
        softAssert.assertTrue(driver.findElement(By.cssSelector("#form > div > div > div > h2 > b")).isDisplayed(),"Message ACCOUNT DELETED is not displayed");

        driver.quit();

    }

    @Test
    public void testLogin() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(ofMillis(3000));

        driver.get("https://www.saucedemo.com/");

        driver.findElement(name("user-name")).sendKeys(LOGIN_SECRET_SAUCE);
        driver.findElement(name("password")).sendKeys(PASSWORD_SECRET_SAUCE);
        driver.findElement(name("login-button")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(driver.getTitle(), "Swag Labs");

        driver.quit();
    }

    @Test
    public void testNegative() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(ofMillis(3000));

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.xpath("//input[@id='user-name']"))
                .sendKeys("123");
        driver.findElement(By.xpath("//input[@id='password']"))
                .sendKeys("123");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();

        WebElement errorNotification = driver.findElement(By.xpath("//h3[text()='Epic sadface: " +
                "Username and password do not match any user in this service']"));

        Assert.assertNotNull(errorNotification);

        driver.quit();
    }

    private static WebDriver driverKM;

    public static void createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        driverKM = new ChromeDriver(options);
        driverKM.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public static void closeDriver() {
        driverKM.quit();
    }

    @Test
    public void testSimpleAlert() {
        createDriver();
        driverKM.get(ALERT_URL);
        driverKM.findElement(By.id("alertButton")).click();
        Alert alert = driverKM.switchTo().alert();
        Assert.assertTrue(alert.getText().contains("You clicked a button"),
                "Отсутствует сообщение в окне при появлении обычного alert");
        alert.accept();

        closeDriver();
    }

    @Test
    public void testTimeDelayAlert() {
        createDriver();
        driverKM.get(ALERT_URL);

        driverKM.findElement(By.id("timerAlertButton")).click();
        WebDriverWait wait = new WebDriverWait(driverKM, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alertTime = driverKM.switchTo().alert();
        Assert.assertTrue(alertTime.getText().contains("This alert appeared after 5 seconds"),
                "Отсутствует сообщение в окне при появлении alert с задержкой");
        alertTime.accept();

        closeDriver();
    }

    @Test
    public void testConfirmBoxSelectOkAlert() {
        createDriver();
        driverKM.get(ALERT_URL);

        driverKM.findElement(By.id("confirmButton")).click();
        Alert alertOk = driverKM.switchTo().alert();
        Assert.assertTrue(alertOk.getText().contains("Do you confirm action?"),
                "Отсутствует сообщение в окне при появлении выбора действий");
        alertOk.accept();
        Assert.assertEquals(driverKM.findElement(By.id("confirmResult")).getText(),
                "You selected Ok",
                "Отсутствует отметка о выбранной кнопке Ok");

        closeDriver();
    }

    @Test
    public void testConfirmBoxSelectCancelAlert() {
        createDriver();
        driverKM.get(ALERT_URL);

        driverKM.findElement(By.id("confirmButton")).click();
        Alert alertCancel = driverKM.switchTo().alert();
        Assert.assertTrue(alertCancel.getText().contains("Do you confirm action?"),
                "Отсутствует сообщение в окне при появлении выбора действий");
        alertCancel.dismiss();
        Assert.assertEquals(driverKM.findElement(By.id("confirmResult")).getText(),
                "You selected Cancel",
                "Отсутствует отметка о выбранной кнопке Cancel");

        closeDriver();
    }

    @Test
    public void testPromptBoxSelectOkAlert() {
        createDriver();
        driverKM.get(ALERT_URL);

        driverKM.findElement(By.id("promtButton")).click();
        Alert promptAlert = driverKM.switchTo().alert();
        Assert.assertTrue(promptAlert.getText().contains("Please enter your name"),
                "Отсутствует сообщение в окне с просьбой ввести имя");
        promptAlert.sendKeys(TEST_NAME);
        promptAlert.accept();
        Assert.assertEquals(driverKM.findElement(By.id("promptResult")).getText(),
                "You entered " + TEST_NAME,
                "Отсутствует отметка о введенном имени");

        closeDriver();
    }

    @Test
    public void testPromptBoxSelectCancelAlert() {
        createDriver();
        driverKM.get(ALERT_URL);

        driverKM.findElement(By.id("promtButton")).click();
        Alert promptAlertCancel = driverKM.switchTo().alert();
        Assert.assertTrue(promptAlertCancel.getText().contains("Please enter your name"),
                "Отсутствует сообщение в окне с просьбой ввести имя");
        promptAlertCancel.sendKeys(TEST_NAME);
        promptAlertCancel.dismiss();
        boolean promptResultPresent;
        try {
            driverKM.findElement(By.id("promptResult"));
            promptResultPresent = true;
        } catch (NoSuchElementException e) {
            promptResultPresent = false;
        }
        Assert.assertFalse(promptResultPresent,
                "Элемент для отметки имени должен отсутствовать");

        closeDriver();
    }

    @Test
    public void testSelectDateWithScrollingArrows() {
        createDriver();
        driverKM.get(DATE_PICKER_URL);

        WebElement inputField = driverKM.findElement(By.id("datePickerMonthYearInput"));
        inputField.click();
        driverKM.findElement(By.className("react-datepicker__navigation--next")).click();
        driverKM.findElement(By.className("react-datepicker__day--024")).click();
        Assert.assertEquals(inputField.getAttribute("value"),
                LocalDate.now().plusMonths(1).withDayOfMonth(24)
                        .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                "Неверно указана выбранная дата");

        closeDriver();
    }

    @Test
    public void testSelectDateWithDropdownList() {
        createDriver();
        driverKM.get(DATE_PICKER_URL);

        WebElement inputField = driverKM.findElement(By.id("datePickerMonthYearInput"));
        inputField.click();
        Select selectMonth = new Select(driverKM.findElement(By.className("react-datepicker__month-select")));
        selectMonth.selectByVisibleText("March");
        Select selectYear = new Select(driverKM.findElement(By.className("react-datepicker__year-select")));
        selectYear.selectByVisibleText("1999");
        driverKM.findElement(By.className("react-datepicker__day--024")).click();
        Assert.assertEquals(inputField.getAttribute("value"),
                "03/24/1999",
                "Неверно указана выбранная дата");

        closeDriver();
    }

    @Test
    public void testSelectDateAndTime() {
        createDriver();
        driverKM.get(DATE_PICKER_URL);

        WebElement inputField = driverKM.findElement(By.id("dateAndTimePickerInput"));
        inputField.click();
        driverKM.findElement(By.className("react-datepicker__month-read-view--down-arrow")).click();
        driverKM.findElement(By.xpath(
                "//div[contains(@class,'react-datepicker__month-option') and text()='March']")).click();
        driverKM.findElement(By.className("react-datepicker__year-read-view--down-arrow")).click();
        driverKM.findElement(By.xpath(
                "//div[contains(@class,'react-datepicker__year-option') and text()='2028']")).click();
        driverKM.findElement(By.xpath(
                "//div[contains(@aria-label,'Choose Wednesday, March 15th, 2028')]")).click();
        driverKM.findElement(By.xpath("//li[normalize-space()='21:00']")).click();
        Assert.assertEquals(inputField.getAttribute("value"),
                "March 15, 2028 9:00 PM",
                "Неверно указаны выбранные дата и время");

        closeDriver();
    }

    // Для теста с аккордеоном:
    protected void assertSectionVisibility(SoftAssert softAssert, WebElement sectionContent, boolean shouldBeVisible, String actionDescription, WebDriver driverKM) {
        WebDriverWait wait = new WebDriverWait(driverKM, Duration.ofSeconds(10));
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
        createDriver();
        driverKM.get(ACCORDION_URL);

        WebElement section1Header = driverKM.findElement(By.id("section1Heading"));
        WebElement section2Header = driverKM.findElement(By.id("section2Heading"));
        WebElement section3Header = driverKM.findElement(By.id("section3Heading"));
        WebElement section1Content = driverKM.findElement(By.id("section1Content"));
        WebElement section2Content = driverKM.findElement(By.id("section2Content"));
        WebElement section3Content = driverKM.findElement(By.id("section3Content"));

        SoftAssert softAssertAccordion = new SoftAssert();
        //  Проверка на первоначальное отображение элементов
        softAssertAccordion.assertTrue(section1Content.isDisplayed(), "Section1 должен быть открыт изначально");
        softAssertAccordion.assertFalse(section2Content.isDisplayed(), "Section2 изначально закрыт");
        softAssertAccordion.assertFalse(section3Content.isDisplayed(), "Section3 изначально закрыт");
        //  Проверка на открытие-закрытие блоков при нажатии Section2
        section2Header.click();
        assertSectionVisibility(softAssertAccordion, section2Content, true, "открытии Section2", driverKM);
        assertSectionVisibility(softAssertAccordion, section1Content, false, "открытии Section2", driverKM);
        assertSectionVisibility(softAssertAccordion, section3Content, false, "открытии Section2", driverKM);
        section2Header.click();
        assertSectionVisibility(softAssertAccordion, section1Content, false, "закрытии Section2", driverKM);
        assertSectionVisibility(softAssertAccordion, section2Content, false, "закрытии Section2", driverKM);
        assertSectionVisibility(softAssertAccordion, section3Content, false, "закрытии Section2", driverKM);
        //  Проверка на открытие-закрытие блоков при нажатии Section3
        section3Header.click();
        assertSectionVisibility(softAssertAccordion, section3Content, true, "открытии Section3", driverKM);
        assertSectionVisibility(softAssertAccordion, section1Content, false, "открытии Section3", driverKM);
        assertSectionVisibility(softAssertAccordion, section2Content, false, "открытии Section3", driverKM);
        section3Header.click();
        assertSectionVisibility(softAssertAccordion, section1Content, false, "закрытии Section3", driverKM);
        assertSectionVisibility(softAssertAccordion, section2Content, false, "закрытии Section3", driverKM);
        assertSectionVisibility(softAssertAccordion, section3Content, false, "закрытии Section3", driverKM);
        //  Проверка на открытие-закрытие блоков при нажатии Section1
        section1Header.click();
        assertSectionVisibility(softAssertAccordion, section1Content, true, "открытии Section1", driverKM);
        assertSectionVisibility(softAssertAccordion, section2Content, false, "открытии Section1", driverKM);
        assertSectionVisibility(softAssertAccordion, section3Content, false, "открытии Section1", driverKM);
        section1Header.click();
        assertSectionVisibility(softAssertAccordion, section1Content, false, "закрытии Section1", driverKM);
        assertSectionVisibility(softAssertAccordion, section2Content, false, "закрытии Section1", driverKM);
        assertSectionVisibility(softAssertAccordion, section3Content, false, "закрытии Section1", driverKM);
        softAssertAccordion.assertAll();

        closeDriver();
    }

    @Test
    public void testDoubleClickButton() {
        createDriver();
        Actions action = new Actions(driverKM);
        driverKM.get(BUTTONS_URL);

        action.moveToElement(driverKM.findElement(By.id("doubleClickBtn"))).doubleClick().perform();
        Assert.assertEquals(driverKM.findElement(By.id("doubleClickMessage")).getText(), "You have done a double click");

        closeDriver();
    }

    @Test
    public void testRightClickButton() {
        createDriver();
        Actions action = new Actions(driverKM);
        driverKM.get(BUTTONS_URL);

        action.contextClick(driverKM.findElement(By.id("rightClickBtn"))).build().perform();
        Assert.assertEquals(driverKM.findElement(By.id("rightClickMessage")).getText(), "You have done a right click");

        closeDriver();
    }

    @Test
    public void testDynamicClickButton() {
        createDriver();
        driverKM.get(BUTTONS_URL);

        driverKM.findElement(By.xpath("//button[text()='Click Me']")).click();
        Assert.assertEquals(driverKM.findElement(By.id("dynamicClickMessage")).getText(), "You have done a dynamic click");

        closeDriver();
    }

    @Test
    public void testOpenURLInNewTab() {
        createDriver();
        driverKM.get(BROWSER_WINDOWS_URL);

        driverKM.findElement(By.id("tabButton")).click();
        Assert.assertEquals(driverKM.getWindowHandles().size(), 2);
        Object[] windowHandles = driverKM.getWindowHandles().toArray();
        driverKM.switchTo().window((String) windowHandles[1]);
        Assert.assertEquals(driverKM.getCurrentUrl(), "https://demoqa.com/sample");

        closeDriver();
    }

    @Test
    public void testOpenNewWindowVerifyBackgroundColor() {
        createDriver();
        driverKM.get(BROWSER_WINDOWS_URL);

        driverKM.findElement(By.id("windowButton")).click(); //Нет разницы в открытии нового окна и вкладки
        Assert.assertEquals(driverKM.getWindowHandles().size(), 2);
        Object[] windowHandles = driverKM.getWindowHandles().toArray();
        driverKM.switchTo().window((String) windowHandles[1]);
        Assert.assertEquals(driverKM.findElement(By.tagName("body")).getCssValue("background-color"),
                "rgba(169, 169, 169, 1)");

        closeDriver();
    }

    @Test
    public void testTooltipButton() {
        createDriver();
        Actions action = new Actions(driverKM);
        driverKM.get(TOOLTIPS_URL);

        WebElement button = driverKM.findElement(By.id("toolTipButton"));
        action.moveToElement(button).perform();
        Assert.assertTrue(driverKM.findElement(By.xpath("//*[text()='You hovered over the Button']")).isDisplayed(),
                "Отсутствует/неверное всплывающее уведомление у кнопки");
        Assert.assertEquals(button.getAttribute("aria-describedby"), "buttonToolTip",
                "Нет необходимого атрибута у кнопки");

        closeDriver();
    }

    @Test
    public void testTooltipTextField() {
        createDriver();
        Actions action = new Actions(driverKM);
        driverKM.get(TOOLTIPS_URL);

        action.moveToElement(driverKM.findElement(By.id("toolTipTextField"))).perform();
        Assert.assertEquals(driverKM.findElement(By.id("textFieldToolTip")).getText(), "You hovered over the text field",
                "Неверное всплывающее уведомление у текстового поля");

        closeDriver();
    }

    @Test
    public void testTooltipContraryInText() {
        createDriver();
        Actions action = new Actions(driverKM);
        driverKM.get(TOOLTIPS_URL);

        String text = "Contrary";
        action.moveToElement(driverKM.findElement(By.xpath("//div[@id='texToolTopContainer']//a[text()='" + text + "']"))).perform();
        Assert.assertEquals(driverKM.findElement(By.id(text.toLowerCase() + "TexToolTip")).getText(), "You hovered over the " + text,
                "Неверное всплывающее уведомление у слова Contrary");

        closeDriver();
    }

    @Test
    public void testTooltipNumberInText() {
        createDriver();
        Actions action = new Actions(driverKM);
        driverKM.get(TOOLTIPS_URL);

        String number = "1.10.32";
        action.moveToElement(driverKM.findElement(By.xpath("//div[@id='texToolTopContainer']//a[text()='" + number + "']"))).perform();
        Assert.assertEquals(driverKM.findElement(By.id("sectionToolTip")).getText(), "You hovered over the " + number,
                "Неверное всплывающее уведомление у чисел 1.10.32");

        closeDriver();
    }

    protected void verifyColorChangeOnHover(String elementXpath, int numberOfSubs, WebDriver driverKM, Actions action) {
        WebElement elementParent = driverKM.findElement(By.xpath(elementXpath + "/parent::li"));
        String colorBefore = elementParent.getCssValue("background-color");
        String itemText = elementParent.getText();
        WebDriverWait wait = new WebDriverWait(driverKM, Duration.ofSeconds(10));
        action.moveToElement(elementParent).perform();
        try {
            wait.until(drv -> !elementParent.getCssValue("background-color").equals(colorBefore));
        } catch (TimeoutException e) {
            Assert.fail("Время ожидания изменения цвета вышло для " + itemText);
        }

        List<WebElement> subItems = driverKM.findElements(By.xpath(elementXpath + "/following-sibling::ul/li"));
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
        createDriver();
        Actions action = new Actions(driverKM);
        driverKM.get(MENU_URL);

        verifyColorChangeOnHover("//ul[@id='nav']//a[text()='Main Item 1']", 0, driverKM, action);
        verifyColorChangeOnHover("//ul[@id='nav']//a[text()='Main Item 3']", 0, driverKM, action);
        verifyColorChangeOnHover("//ul[@id='nav']//a[text()='Main Item 2']", 3, driverKM, action);
        verifyColorChangeOnHover("//ul[@id='nav']//li[a[text()='Sub Item']][1]/a", 0, driverKM, action);
        verifyColorChangeOnHover("//ul[@id='nav']//li[a[text()='Sub Item']][2]/a", 0, driverKM, action);
        verifyColorChangeOnHover("//ul[@id='nav']//a[text()='SUB SUB LIST »']", 2, driverKM, action);

        closeDriver();
    }

    @Test
    public void testOpenSmallModalDialogAndCloseWithCloseButton() {
        createDriver();
        driverKM.get(MODAL_DIALOGS_URL);

        driverKM.findElement(By.id("showSmallModal")).click();
        WebDriverWait wait = new WebDriverWait(driverKM, Duration.ofSeconds(10));
        WebElement modalDialog = driverKM.findElement(By.xpath("//div[@role='dialog' and contains(@class, 'show')]"));
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(modalDialog)).isDisplayed());
        Assert.assertEquals(driverKM.findElement(By.id("example-modal-sizes-title-sm")).getText(), "Small Modal");
        Assert.assertEquals(driverKM.findElement(By.className("modal-body")).getText(), "This is a small modal. It has very less content");
        driverKM.findElement(By.id("closeSmallModal")).click();
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(modalDialog)));

        closeDriver();
    }

    @Test
    public void testOpenLargeModalDialogAndCloseWithXButton() {
        createDriver();
        driverKM.get(MODAL_DIALOGS_URL);

        driverKM.findElement(By.id("showLargeModal")).click();
        WebDriverWait wait = new WebDriverWait(driverKM, Duration.ofSeconds(10));
        WebElement modalDialog = driverKM.findElement(By.xpath("//div[@role='dialog' and contains(@class, 'show')]"));
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(modalDialog)).isDisplayed());
        Assert.assertEquals(driverKM.findElement(By.id("example-modal-sizes-title-lg")).getText(), "Large Modal");
        Assert.assertTrue(driverKM.findElement(By.className("modal-body")).getText().contains("It has survived not only five centuries"));
        driverKM.findElement(By.xpath("//button[contains(@class, 'close')]")).click();
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(modalDialog)));

        closeDriver();
    }

    @Test
    public void testTextBox() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/");

        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.findElement(By.xpath("//div[@class='card-body']/h5[text()='Elements']")).click();
        driver.findElement(By.xpath("//span[text()='Text Box']")).click();

        WebElement fullNameInput = driver.findElement(By.xpath("//input[@placeholder='Full Name']"));
        fullNameInput.sendKeys("Scolnicova Uliana");

        WebElement emailInput = driver.findElement(By.xpath("//input[@placeholder='name@example.com']"));
        emailInput.sendKeys("uliana@gmail.com");

        WebElement currentAddressInput = driver.findElement(By.xpath("//textarea[@placeholder='Current Address']"));
        currentAddressInput.sendKeys("Chisinau");

        WebElement permanentAddressInput = driver.findElement(By.xpath("//textarea[@id='permanentAddress']"));
        permanentAddressInput.sendKeys("Chisinau");

        WebElement submitButton = driver.findElement(By.xpath("//button[@id='submit']"));
        new Actions(driver)
                .scrollToElement(submitButton)
                .perform();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("output")));

        Assert.assertEquals(
                driver.findElement(By.xpath("//p[@id='name']")).getText(), "Name:Scolnicova Uliana"
        );
        Assert.assertEquals(
                driver.findElement(By.xpath("//p[@id='email']")).getText(), "Email:uliana@gmail.com"
        );
        Assert.assertEquals(
                driver.findElement(By.xpath("//p[@id='currentAddress']")).getText(), "Current Address :Chisinau"
        );
        Assert.assertEquals(
                driver.findElement(By.xpath("//p[@id='permanentAddress']")).getText(), "Permananet Address :Chisinau"
        );
  
        driver.quit();
}
  
    public void testDataOpeningL2() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.la2era.com/ru");
        WebElement textBox = driver.findElement(By.xpath("/html/body/div/section/section/div/div/div[2]/div[2]/a[1]"));
        textBox.click();
        Assert.assertEquals(driver.findElement(By.cssSelector("body > div > section > section > div > div > div.home-about__stroke.flex-cc > div.tab-content > div.tab-pane.active > div.home-about__stroke.flex-ss > div:nth-child(9)")).getText(), "Дата открытия: 09.02.2018");

        driver.quit();
    }

    @Test
    public void testChangeLanguageL2() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.la2era.com/ru");
        WebElement submitButton = driver.findElement(By.cssSelector("body > div.wrapper > header > nav > div > div.navigation__langs > div"));
        submitButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        WebElement submitButton2 = driver.findElement(By.cssSelector("body > div > header > nav > div > div.navigation__langs > div > a:nth-child(3) > div.navigation__langs-item-name"));
        submitButton2.click();
        Assert.assertEquals(driver.findElement(By.cssSelector("body > div.wrapper > section > section > div > div > div.home-about__textbox > div")).getText(), "HIGH FIVE X3: LAUNCHING OCTOBER 3RD.\n" + "GET READY FOR ADVENTURE!");

        driver.quit();
    }

    @Test
    public void testBookChooseDemoQA() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        driver.get(DEMOQA_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        ((JavascriptExecutor) driver).executeScript(
                "var ad = document.getElementById('fixedban'); if (ad) ad.remove();"
        );

        WebElement BookStoreAppTile = driver.findElement(By.xpath("//div[contains(@class,'card-body')][.//h5[text()='Book Store Application']]"));
        wait.until(ExpectedConditions.visibilityOf(BookStoreAppTile));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", BookStoreAppTile);
        BookStoreAppTile.click();
        String booksUrl = driver.getCurrentUrl();

        Assert.assertEquals(booksUrl, "https://demoqa.com/books");

        Thread.sleep(2000);
        WebElement searchBox = driver.findElement(By.id("searchBox"));

        wait.until(ExpectedConditions.visibilityOf(searchBox));
        searchBox.sendKeys("el");

        List <WebElement> searchResultsGrid = driver.findElements (By.cssSelector(".rt-tr-group .rt-tr:not(.-padRow)"));
        int count = searchResultsGrid.size();
        Assert.assertTrue(count>=1);

        WebElement bookTitle = driver.findElement(By.id("see-book-Eloquent JavaScript, Second Edition"));
        bookTitle.click();
        String bookTitleUrl = driver.getCurrentUrl();
        Assert.assertEquals(bookTitleUrl, "https://demoqa.com/books?book=9781593275846");

        driver.quit();
    }

    @Test
    public void testRadioButton() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/elements");

        driver.manage().window().setSize(new Dimension(1920, 1080));

        WebElement radioButtonMenu = driver.findElement(By.xpath("//span[text()='Radio Button']"));
        radioButtonMenu.click();

        WebElement yesRadioButton = driver.findElement(By.xpath("//label[@for='yesRadio']"));
        yesRadioButton.click();

        WebElement result = driver.findElement(By.xpath("//p[@class='mt-3']"));
        Assert.assertEquals(result.getText(), "You have selected Yes");
      
        driver.quit();

    @Test
    public void testPutDepositBankAccount() {

        createDriver();

        driverKM.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        driverKM.manage().timeouts().implicitlyWait(Duration.ofMillis(900));

        WebElement customerLoginButton = driverKM
                .findElement(By.xpath("//button[@class='btn btn-primary btn-lg' and contains(text(), 'Customer Login')]"));
        customerLoginButton.click();

        Select userDropdown = new Select(driverKM.findElement(By.name("userSelect")));
        userDropdown.selectByValue("1");

        WebElement loginButton = driverKM
                .findElement(By.xpath("//button[@class='btn btn-default']"));
        loginButton.click();
        driverKM.findElement(By.xpath("//button[@ng-click='deposit()']")).click();

        WebElement makeDepositInput = driverKM
                .findElement(By.xpath("//input[@class='form-control ng-pristine ng-untouched ng-invalid ng-invalid-required']"));
        makeDepositInput.sendKeys("1000");
        driverKM.findElement(By.xpath("//button[@class='btn btn-default']")).click();

        WebElement depositMessage = driverKM
                .findElement(By.xpath("//span[@class='error ng-binding']"));
        Assert.assertEquals(depositMessage.getText(), "Deposit Successful", "Ошибка в переводе на депозит");

        closeDriver();
    }

    @Test
    public void testFormFilling() {

        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='80%'");
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/text-box");

        String fullName = "Harry Potter";
        String email = "harrypotter@gmail.com";
        String currentAddress = "The Cupboard under the Stairs, 4 Privet Drive, Little Whinging, SURREY";
        String permanentAddress = "Hogwarts School of Witchcraft and Wizardry, The Scottish Highlands, United Kingdom";

        WebElement fullNameBox = driver.findElement(By.xpath("//*[@id='userName']"));
        fullNameBox.sendKeys(fullName);

        WebElement emailBox = driver.findElement(By.xpath("//*[@id='userEmail']"));
        emailBox.sendKeys(email);

        WebElement currentAddressBox = driver.findElement(By.xpath("//*[@id='currentAddress']"));
        currentAddressBox.sendKeys(currentAddress);

        WebElement permanentAddressBox = driver.findElement(By.xpath("//*[@id='permanentAddress']"));
        permanentAddressBox.sendKeys(permanentAddress);

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        submitButton.click();

        String actualName = driver.findElement(By.xpath("//p[@id='name']")).getText();
        String actualEmail = driver.findElement(By.xpath("//p[@id='email']")).getText();
        String actualCurrentAddress = driver.findElement(By.xpath("//p[@id='currentAddress']")).getText();
        String actualPermanentAddress = driver.findElement(By.xpath("//p[@id='permanentAddress']")).getText();


        Assert.assertEquals(actualName, "Name:" + fullName);
        Assert.assertEquals(actualEmail,"Email:" + email);
        Assert.assertEquals(actualCurrentAddress, "Current Address :" + currentAddress);
        Assert.assertEquals(actualPermanentAddress,"Permananet Address :" + permanentAddress);

        driver.quit();
    }
}

