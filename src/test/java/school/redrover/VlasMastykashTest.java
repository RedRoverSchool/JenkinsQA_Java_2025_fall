package school.redrover;

/*
  For
  -> import net.datafaker.Faker <-
  just add this dependency to pom.xml

  <!-- https://mvnrepository.com/artifact/net.datafaker/datafaker -->
  <dependency>
      <groupId>net.datafaker</groupId>
      <artifactId>datafaker</artifactId>
      <version>2.5.1</version>
  </dependency>
 */
import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// https://automationexercise.com/test_cases
public class VlasMastykashTest {
    private static final String BASE_URL = "https://automationexercise.com";

    private WebDriver driver;
    private Faker faker;

    // Web site header
    private final By loginPageLink = By.linkText("Signup / Login");
    private final By loggedAsInfo = By.xpath("//i[contains(@class, 'fa-user')]/..");

    // Login page
    // Sign Up form
    private final By signUpHeader = By.cssSelector(".signup-form > h2");
    private final By signUpNameInput = By.name("name");
    private final By signUpEmailInput = By.cssSelector("input[data-qa='signup-email']");
    private final By signUpButton = By.cssSelector("button[data-qa='signup-button']");
    // Login form
    private final By loginFormHeader = By.cssSelector("div.login-form > h2");
    private final By loginEmailInput = By.cssSelector("input[data-qa='login-email']");
    private final By loginPasswordInput = By.cssSelector("input[data-qa='login-password']");
    private final By loginConfirmButton = By.cssSelector("button[data-qa='login-button']");

    // Success pages
    private final By accountCreatedHeader = By.cssSelector("h2[data-qa='account-created']");
    private final By accountDeletedHeader = By.cssSelector("h2[data-qa='account-deleted']");

    // Test data
    private static final String[] COUNTRIES = {
            "India", "United States", "Canada", "Australia",
            "Israel", "New Zealand", "Singapore",
    };

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-infobars");
        options.addArguments("--window-size=1920,1080");
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
        faker = new Faker();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Test case 1: Register User
    @Test(description = "Test Case 1: Register User")
    public void testUserRegistration() throws InterruptedException {
        User testUser = new User();

        driver.get(BASE_URL);
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");

        driver.findElement(loginPageLink).click();
        Assert.assertTrue(driver.findElement(signUpHeader).isDisplayed());
        Assert.assertEquals(driver.findElement(signUpHeader).getText(), "New User Signup!");

        driver.findElement(signUpNameInput).sendKeys(testUser.getFirstName());
        driver.findElement(signUpEmailInput).sendKeys(testUser.getEmail());
        driver.findElement(signUpButton).click();

        Thread.sleep(1000);
        Assert.assertTrue(driver.findElement(SignUpPage.accountHeader).isDisplayed());
        Assert.assertEquals(driver.findElement(SignUpPage.accountHeader).getText(), "ENTER ACCOUNT INFORMATION");

        driver.findElement(SignUpPage.femaleGenderRadioButton).click();

        WebElement signUpNameInput = driver.findElement(SignUpPage.nameInput);
        String signUpNameValue = signUpNameInput.getAttribute("value");
        Assert.assertEquals(signUpNameValue, testUser.getFirstName());
        signUpNameInput.clear();

        testUser.setFirstName(faker.name().firstName());
        signUpNameInput.sendKeys(testUser.getFirstName());
        driver.findElement(SignUpPage.passwordInput).sendKeys("Pa$$word1!");

        Select days = new Select(driver.findElement(SignUpPage.daysDropDown));
        int randomDay = ThreadLocalRandom.current().nextInt(1, 31);
        days.selectByValue(String.valueOf(randomDay));

        Select months = new Select(driver.findElement(SignUpPage.monthsDropDown));
        int randomMonth = ThreadLocalRandom.current().nextInt(1, 12);
        months.selectByValue(String.valueOf(randomMonth));

        Select years = new Select(driver.findElement(SignUpPage.yearsDropDown));
        int randomYear = ThreadLocalRandom.current().nextInt(1980, 2005);
        years.selectByValue(String.valueOf(randomYear));

        driver.findElement(SignUpPage.newsletterCheckBox).click();
        driver.findElement(SignUpPage.specialOfferCheckBox).click();

        driver.findElement(SignUpPage.firstNameInput).sendKeys(testUser.getFirstName());
        driver.findElement(SignUpPage.lastNameInput).sendKeys(testUser.getLastName());
        driver.findElement(SignUpPage.companyInput).sendKeys(testUser.getCompanyName());
        driver.findElement(SignUpPage.address1Input).sendKeys(testUser.getStreetAddress());
        driver.findElement(SignUpPage.address2Input).sendKeys(testUser.getSecondaryAddress());

        Select countryDropDown = new Select(driver.findElement(SignUpPage.countryInput));
        countryDropDown.selectByValue(getRandomCountry());

        driver.findElement(SignUpPage.stateInput).sendKeys(testUser.getState());
        driver.findElement(SignUpPage.cityInput).sendKeys(testUser.getCity());
        driver.findElement(SignUpPage.zipCodeInput).sendKeys(testUser.getZipCode());
        driver.findElement(SignUpPage.mobileNumberInput).sendKeys(testUser.getPhoneNumber());

        driver.findElement(SignUpPage.createAccountButton).click();

        Assert.assertTrue(driver.findElement(accountCreatedHeader).isDisplayed());
        Assert.assertEquals(driver.findElement(accountCreatedHeader).getText(), "ACCOUNT CREATED!");

        driver.findElement(By.linkText("Continue")).click();

        Assert.assertTrue(driver.findElement(loggedAsInfo).isDisplayed());
        Assert.assertEquals(driver.findElement(loggedAsInfo).getText(), "Logged in as %s".formatted(testUser.getFirstName()));

        driver.findElement(By.linkText("Delete Account")).click();
        Assert.assertTrue(driver.findElement(accountDeletedHeader).isDisplayed());
        Assert.assertEquals(driver.findElement(accountDeletedHeader).getText(), "ACCOUNT DELETED!");

        driver.findElement(By.linkText("Continue")).click();
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");
    }

    @Test(description = "Test Case 2: Login User with correct email and password")
    public void testLoginWithValidData() {
        User testUser = User.DEFAULT_TEST_USER;

        driver.get(BASE_URL);
        Assert.assertEquals(driver.getTitle(), "Automation Exercise");

        driver.findElement(loginPageLink).click();
        Assert.assertEquals(driver.findElement(loginFormHeader).getText(), "Login to your account");

        driver.findElement(loginEmailInput).sendKeys(testUser.getEmail());
        driver.findElement(loginPasswordInput).sendKeys(testUser.getPassword());
        driver.findElement(loginConfirmButton).click();

        Assert.assertTrue(driver.findElement(loggedAsInfo).isDisplayed());
        Assert.assertEquals(driver.findElement(loggedAsInfo).getText(), "Logged in as %s".formatted(testUser.getFirstName()));
    }

    public static String getRandomCountry() {
        return COUNTRIES[new Random().nextInt(COUNTRIES.length)];
    }

    private static class SignUpPage {
        // Sign Up Account block
        private static final By accountHeader = By.cssSelector("div.login-form > h2");
        private static final By femaleGenderRadioButton = By.id("id_gender2");
        private static final By nameInput = By.id("name");
        private static final By daysDropDown = By.id("days");
        private static final By monthsDropDown = By.id("months");
        private static final By passwordInput = By.id("password");
        private static final By yearsDropDown = By.id("years");
        private static final By newsletterCheckBox = By.id("newsletter");
        private static final By specialOfferCheckBox = By.id("optin");

        // Sign Up Address block
        private static final By firstNameInput = By.id("first_name");
        private static final By lastNameInput = By.id("last_name");
        private static final By companyInput = By.id("company");
        private static final By address1Input = By.id("address1");
        private static final By address2Input = By.id("address2");
        private static final By countryInput = By.id("country");
        private static final By stateInput = By.id("state");
        private static final By cityInput = By.id("city");
        private static final By zipCodeInput = By.id("zipcode");
        private static final By mobileNumberInput = By.id("mobile_number");

        // Sign Up confirm
        private static final By createAccountButton = By.cssSelector("button[type='submit']");
    }
}

class User {
    private final Faker faker = new Faker();

    private String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    private final String companyName;
    private final String streetAddress;
    private final String secondaryAddress;

    private final String country;
    private final String state;
    private final String city;
    private final String zipCode;
    private final String phoneNumber;

    public static final User DEFAULT_TEST_USER = new User.Builder()
            .firstName("Maryalice")
            .lastName("Lakin")
            .email("maryalice.lakin.1759567115@hane.org")
            .password("Pa$$word1!")
            .companyName("Ratke LLC")
            .streetAddress("72420 Kozey Unions")
            .secondaryAddress("Apt. 380")
            .country("India")
            .state("Virginia")
            .city("North Mazie")
            .zipCode("88997")
            .phoneNumber("(305) 750-8981")
            .build();

    public User() {
        this.firstName = faker.name().firstName();
        this.lastName = faker.name().lastName();
        this.email = "%s.%s.%d@%s"
                .formatted(
                        this.firstName,
                        this.lastName,
                        Instant.now().getEpochSecond(),
                        faker.internet().domainName())
                .toLowerCase();
        this.password = "Pa$$word1!";
        this.companyName = faker.company().name();
        this.streetAddress = faker.address().streetAddress();
        this.secondaryAddress = faker.address().secondaryAddress();
        this.country = VlasMastykashTest.getRandomCountry();
        this.state = faker.address().state();
        this.city = faker.address().city();
        this.zipCode = faker.address().zipCode();
        this.phoneNumber = faker.phoneNumber().cellPhone();
    }

    public User(String firstName, String lastName, String email, String password, String companyName,
                String streetAddress, String secondaryAddress, String country, String state,
                String city, String zipCode, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.streetAddress = streetAddress;
        this.secondaryAddress = secondaryAddress;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getSecondaryAddress() {
        return secondaryAddress;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", companyName='" + companyName + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", secondaryAddress='" + secondaryAddress + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;

        private String companyName;
        private String streetAddress;
        private String secondaryAddress;

        private String country;
        private String state;
        private String city;
        private String zipCode;
        private String phoneNumber;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder streetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
            return this;
        }

        public Builder secondaryAddress(String secondaryAddress) {
            this.secondaryAddress = secondaryAddress;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        User build() {
            return new User(firstName, lastName, email, password, companyName, streetAddress,
                    secondaryAddress, country, state, city, zipCode, phoneNumber);
        }
    }
}