package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class GroupUnitedByJavaTest {

    private static final String LOGIN = "admin@admin.com";
    private static final String PASSWORD = "admin123";
    private static final String DEMOQA_URL = "https://demoqa.com/";
    private static final String THECODE_URL ="https://thecode.media/";

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

}

