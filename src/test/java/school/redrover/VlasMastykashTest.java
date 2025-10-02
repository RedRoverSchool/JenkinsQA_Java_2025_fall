package school.redrover;

/*
  For net.datafaker.Faker just add this dependency to pom.xml
  and remove faker comments
  <!-- https://mvnrepository.com/artifact/net.datafaker/datafaker -->
  <dependency>
      <groupId>net.datafaker</groupId>
      <artifactId>datafaker</artifactId>
      <version>2.5.1</version>
  </dependency>
 */
//import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class VlasMastykashTest {
    private WebDriver driver;
    //    private Faker faker;

    // Main header
    private final By loginLink = By.linkText("Signup / Login");

    // Sign Up Account block
    private final By accountHeader = By.cssSelector("div.login-form > h2");
    private final By femaleGenderRadioButton = By.id("id_gender2");
    private final By nameInput = By.id("name");
    private final By daysDropDown = By.id("days");
    private final By monthsDropDown = By.id("months");
    private final By password = By.id("password");
    private final By yearsDropDown = By.id("years");
    private final By newsletterCheckBox = By.id("newsletter");
    private final By specialOfferCheckBox = By.id("optin");
    // Sign Up Address block
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By address1 = By.id("address1");
    private final By address2 = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipCode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");
    // Sign Up confirm
    private final By createAccountButton = By.cssSelector("button[type='submit']");

    // Test data
    private final String[] countries = {
            "India", "United States", "Canada", "Australia",
            "Israel", "New Zealand", "Singapore",
    };

    private final String baseUrl = "https://automationexercise.com/";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-infobars");
        options.addArguments("--window-size=950,1080");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        // Настройки для отключения сохранения паролей и автозаполнения
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        prefs.put("autofill.credit_card_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        //        faker = new Faker();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get(baseUrl);
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl);
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "Test Case 1: Register User")
    public void testUserRegistration() {
        // Test case 1: Register User
        // https://automationexercise.com/test_cases
        driver.findElement(loginLink).click();

        WebElement newUserSignUpHeader = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/h2"));
        Assert.assertTrue(newUserSignUpHeader.isDisplayed());
        Assert.assertEquals(newUserSignUpHeader.getText(), "New User Signup!");

        WebElement signUpName = driver.findElement(By.name("name"));
        String userInputValue = "Username"; // faker.name().firstName();
        signUpName.sendKeys(userInputValue);

        WebElement signUpEmail = driver.findElement(By.xpath("//*[@id=\"form\"]/div/div/div[3]/div/form/input[3]"));
        String userEmailValue = "testuser%d@example.com".formatted(Instant.now().getEpochSecond());
        signUpEmail.sendKeys(userEmailValue);

        WebElement signUpButton = driver.findElement(By.cssSelector("button[data-qa=\"signup-button\"]"));
        signUpButton.click();

        Select days = new Select(driver.findElement(daysDropDown));
        Select months = new Select(driver.findElement(monthsDropDown));
        Select years = new Select(driver.findElement(yearsDropDown));

        Assert.assertTrue(driver.findElement(accountHeader).isDisplayed());
        Assert.assertEquals(driver.findElement(accountHeader).getText(), "ENTER ACCOUNT INFORMATION");

        driver.findElement(femaleGenderRadioButton).click();

        Assert.assertEquals(driver.findElement(nameInput).getAttribute("value"), userInputValue);
        driver.findElement(nameInput).clear();
        String newName = "Boris";// faker.name().firstName();
        driver.findElement(nameInput).sendKeys(newName);

        driver.findElement(password).sendKeys("Pa$$word1!");

        int randomDay = ThreadLocalRandom.current().nextInt(1, 31);
        days.selectByValue(String.valueOf(randomDay));

        int randomMonth = ThreadLocalRandom.current().nextInt(1, 12);
        months.selectByValue(String.valueOf(randomMonth));

        int randomYear = ThreadLocalRandom.current().nextInt(1980, 2005);
        years.selectByValue(String.valueOf(randomYear));

        driver.findElement(newsletterCheckBox).click();
        driver.findElement(specialOfferCheckBox).click();

        //        driver.findElement(firstName).sendKeys(faker.name().firstName());
        //        driver.findElement(lastName).sendKeys(faker.name().lastName());
        //        driver.findElement(company).sendKeys(faker.company().name());
        //        driver.findElement(address1).sendKeys(faker.address().streetAddress());
        //        driver.findElement(address2).sendKeys(faker.address().secondaryAddress());

        driver.findElement(firstName).sendKeys("Boris");
        driver.findElement(lastName).sendKeys("Brejcha");
        driver.findElement(company).sendKeys("World DJs");
        driver.findElement(address1).sendKeys("15312 Main Street NY");
        driver.findElement(address2).sendKeys("Apt. 4734");

        Select countryDropDown = new Select(driver.findElement(country));
        countryDropDown.selectByValue(countries[new Random().nextInt(countries.length)]);

        //        driver.findElement(state).sendKeys(faker.address().state());
        //        driver.findElement(city).sendKeys(faker.address().city());
        //        driver.findElement(zipCode).sendKeys(faker.address().zipCode());
        //        driver.findElement(mobileNumber).sendKeys(faker.phoneNumber().cellPhone());

        driver.findElement(state).sendKeys("NY");
        driver.findElement(city).sendKeys("NY");
        driver.findElement(zipCode).sendKeys("63892");
        driver.findElement(mobileNumber).sendKeys("+15552352321");

        driver.findElement(createAccountButton).click();

        WebElement accountCreatedHeader = driver.findElement(By.cssSelector("h2[data-qa=\"account-created\"]"));
        Assert.assertTrue(accountCreatedHeader.isDisplayed());
        Assert.assertEquals(accountCreatedHeader.getText(), "ACCOUNT CREATED!");

        driver.findElement(By.linkText("Continue")).click();

        WebElement loginHeader = driver.findElement(By.linkText("Logged in as %s".formatted(newName)));
        Assert.assertTrue(loginHeader.isDisplayed());
        Assert.assertEquals(loginHeader.getText(), "Logged in as %s".formatted(newName));

        driver.findElement(By.linkText("Delete Account")).click();

        WebElement accountDeletedHeader = driver.findElement(By.cssSelector("h2[data-qa=\"account-deleted\"]"));
        Assert.assertTrue(accountDeletedHeader.isDisplayed());
        Assert.assertEquals(accountDeletedHeader.getText(), "ACCOUNT DELETED!");

        driver.findElement(By.linkText("Continue")).click();
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl);
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");
    }
}
