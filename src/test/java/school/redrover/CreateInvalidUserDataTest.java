package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * US_15.001 | User > Create
 *
 * @see <a href="https://github.com/RedRoverSchool/JenkinsQA_Java_2025_fall/issues/328">US</a>
 * @see <a href="https://github.com/RedRoverSchool/JenkinsQA_Java_2025_fall/issues/414">TC TC_15.001.04 </a>
 * @see <a href="https://github.com/RedRoverSchool/JenkinsQA_Java_2025_fall/issues/415">TC TC_15.001.05</a>
 * @see <a href="https://github.com/RedRoverSchool/JenkinsQA_Java_2025_fall/issues/460">TC TC_15.001.08</a>
 * @see <a href="https://github.com/RedRoverSchool/JenkinsQA_Java_2025_fall/issues/417">TC TC_15.001.06</a>
 * @see <a href="https://github.com/RedRoverSchool/JenkinsQA_Java_2025_fall/issues/419">TC TC_15.001.07</a>
 */

public class CreateInvalidUserDataTest extends BaseTest {

    private static CharSequence getRandomAsciiCharUtil() {
        final char[] RANGE_ASCII =
                java.util.stream.Stream.of(
                                IntStream.rangeClosed(33, 255)
                        ).flatMapToInt(s -> s)
                        .collect(StringBuilder::new,
                                (sb, cp) -> sb.append((char) cp),
                                StringBuilder::append)
                        .toString()
                        .toCharArray();

        int i = ThreadLocalRandom.current().nextInt(RANGE_ASCII.length);
        return String.valueOf(RANGE_ASCII[i]);
    }

    private static CharSequence getRandomInvalidAsciiCharForNameUtil() {
        final char[] RANGE_NAME =
                java.util.stream.Stream.of(
                                IntStream.rangeClosed(33, 45),
                                IntStream.rangeClosed(46, 47),
                                IntStream.rangeClosed(58, 64),
                                IntStream.rangeClosed(91, 94),
                                IntStream.of(96),
                                IntStream.rangeClosed(123, 126),
                                IntStream.rangeClosed(128, 255)
                        ).flatMapToInt(s -> s)
                        .collect(StringBuilder::new,
                                (sb, cp) -> sb.append((char) cp),
                                StringBuilder::append)
                        .toString()
                        .toCharArray();

        int i = ThreadLocalRandom.current().nextInt(RANGE_NAME.length);
        return String.valueOf(RANGE_NAME[i]);
    }

    private static CharSequence getRandomInvalidAsciiCharForEmailUtil() {
        final char[] RANGE_EMAIL =
                java.util.stream.Stream.of(
                                IntStream.rangeClosed(32, 63),
                                IntStream.rangeClosed(65, 255)
                        ).flatMapToInt(s -> s)
                        .collect(StringBuilder::new,
                                (sb, cp) -> sb.append((char) cp),
                                StringBuilder::append)
                        .toString()
                        .toCharArray();

        int i = ThreadLocalRandom.current().nextInt(RANGE_EMAIL.length);
        return String.valueOf(RANGE_EMAIL[i]);
    }

    private static Integer getRandomInteger(){
        int min = 0;
        int max = Integer.MAX_VALUE;
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private void getAddUserPage() {
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.xpath("//a[@href='securityRealm/']")).click();
        getDriver().findElement(By.xpath("//a[@href='addUser']")).click();
    }

    @Test
    public void createInvalidUserFullNameTest() {
        getAddUserPage();
        WebElement userName = getDriver().findElement(By.xpath("//input[@name='username']"));
        String testData = "";

        userName.sendKeys(testData.repeat(getRandomInteger()));

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String expectedFullNameErrorMessage = "\"\" is prohibited as a full name for security reasons.";
        String actualErrorMessage = getDriver().findElement(By.xpath("(//div[@class='error jenkins-!-margin-bottom-2'])[4]"))
                .getText();
        Assert.assertEquals(actualErrorMessage, expectedFullNameErrorMessage);
    }

    @Test
    public void createInvalidUserNameTest() {
        getAddUserPage();
        WebElement userName = getDriver().findElement(By.xpath("//input[@name='username']"));
        CharSequence testData = getRandomInvalidAsciiCharForNameUtil();

        int length1 = Objects.requireNonNull(userName.getAttribute("value")).length();
        userName.sendKeys(testData);
        int length2 = Objects.requireNonNull(userName.getAttribute("value")).length();
        Assert.assertTrue(length2 > length1);

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        String userNameText = getDriver().findElement(By.xpath("//input[@name='username']")).getAttribute("value");
        String fullNameText = getDriver().findElement(By.xpath("//input[@name='fullname']")).getAttribute("value");

        String expectedErrorMessage = "User name must only contain alphanumeric characters, underscore and dash";
        String actualErrorMessage = getDriver().findElement(By.xpath("(//div[@class='error jenkins-!-margin-bottom-2'])[1]"))
                .getText();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
        Assert.assertEquals(fullNameText, userNameText);
    }

    @Test
    public void createInvalidUserPasswordTest() {
        getAddUserPage();
        WebElement password = getDriver().findElement(By.xpath("//input[@name='password1']"));

        password.sendKeys("");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String expectedErrorMessage = "Password is required";
        String actualErrorMessage1 = getDriver().findElement(By.xpath("(//div[@class='error jenkins-!-margin-bottom-2'])[2]"))
                .getText();
        String actualErrorMessage2 = getDriver().findElement(By.xpath("(//div[@class='error jenkins-!-margin-bottom-2'])[3]"))
                .getText();
        Assert.assertEquals(actualErrorMessage1, expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage2, expectedErrorMessage);
    }

    @Test
    public void createNotMatchedPasswordTest() {
        getAddUserPage();
        WebElement password = getDriver().findElement(By.xpath("//input[@name='password1']"));

        password.sendKeys(getRandomAsciiCharUtil());

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String expectedErrorMessage = "Password didn't match";
        String actualErrorMessage1 = getDriver().findElement(By.xpath("(//div[@class='error jenkins-!-margin-bottom-2'])[2]"))
                .getText();
        String actualErrorMessage2 = getDriver().findElement(By.xpath("(//div[@class='error jenkins-!-margin-bottom-2'])[3]"))
                .getText();
        Assert.assertEquals(actualErrorMessage1, expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage2, expectedErrorMessage);
    }

    @Test
    public void createInvalidEmailTest() {
        getAddUserPage();

        WebElement email = getDriver().findElement(By.xpath("//input[@name='email']"));

        int length1 = Objects.requireNonNull(email.getAttribute("value")).length();
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys(getRandomInvalidAsciiCharForEmailUtil());
        int length2 = Objects.requireNonNull(getDriver().findElement(By.xpath("//input[@name='email']")).getAttribute("value")).length();
        Assert.assertTrue(length2 > length1);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String expectedErrorMessage = "Invalid e-mail address";
        String actualErrorMessage = getDriver().findElement(By.xpath("(//div[@class='error jenkins-!-margin-bottom-2'])[5]"))
                .getText();

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
