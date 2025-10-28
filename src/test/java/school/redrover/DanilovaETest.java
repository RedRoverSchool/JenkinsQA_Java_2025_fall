package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.ProjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DanilovaETest extends BaseTest {
    private static final Properties properties = new Properties();
    private WebDriverWait wait = null;

    private static final String NEW_JOB_PAGE = "view/all/newJob";

    private final By welcomeText = By.xpath("//div[@class='empty-state-block']");
    private static final String jenkinsVersion = "Jenkins 2.516.3";
    private static final By jenkinsVersionButton = By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']");
    private static final By logoSource = By.xpath("//img[@id='jenkins-head-icon']");
    private static final By createItemButton = By.xpath("(//a[@class='task-link task-link-no-confirm '])[1]");

    private static final By newItemNameFieldHost = By.cssSelector("input[name='name']");
    private static final By pipelineButton = By.xpath("(//span[@class='label'])[2]");
    private static final By submitButton = By.xpath("//button[@type='submit']");

    private String getCurrentStageUrl() throws IOException {
        properties.load(getClass().getClassLoader().getResourceAsStream(".properties"));
//        String remoutHost = System.getenv("JENKINS_HOST");
//        String localHost = properties.getProperty("jenkins.host");
//        String remoutPort = System.getenv("JENKINS_PORT");
//        String localPort = properties.getProperty("jenkins.port");
        String host = ProjectUtils.getValue("jenkins.host");
        String port = ProjectUtils.getValue("jenkins.port");
        return String.format("http://%s:%s/", host, port);
    }

    @Test
    void checkCurrentStageHomePage() throws IOException {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(welcomeText));

        String expectedUrl = getCurrentStageUrl();
        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Check home page url for this stage!");
    }

    @Test
    void checkJenkinsVersion() {
        String actualVersion = getDriver().findElement(jenkinsVersionButton).getText();
        Assert.assertEquals(actualVersion, jenkinsVersion, "Version must be \"Jenkins 2.516.3\"");
    }

    @Test
    public void checkLogoBytesEqualTest() throws IOException {
        String actualLogoSrcURI = getDriver().findElement(logoSource).getAttribute("src");

        InputStream expectedLogoStream = getClass().getClassLoader()
                .getResourceAsStream("logo/logo.svg");
        Assert.assertNotNull(expectedLogoStream, "Expected logo file not found");

        assert actualLogoSrcURI != null;
        InputStream actualLogoStream = null;
        try {
            actualLogoStream = new URL(actualLogoSrcURI).openStream();
        } catch (IOException ignore) {
        }
        byte[] expectedBytes = expectedLogoStream.readAllBytes();
        byte[] actualBytes = null;
        try {
            assert actualLogoStream != null;
            actualBytes = actualLogoStream.readAllBytes();
        } catch (NullPointerException ignored) {
        }

        Assert.assertTrue(Arrays.equals(expectedBytes, actualBytes),
                "Logo image bytes differ from expected");
    }

    private void openCreateNewJobPage() {
        getDriver().findElement(createItemButton).click();
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains(NEW_JOB_PAGE));
    }

    @Test
    void createSuccessfullyTaskTest() throws IOException {
        openCreateNewJobPage();

        String expectedUrl = getCurrentStageUrl() + NEW_JOB_PAGE;
        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Check NewJob page-url for this stage!");

        String newName = "My new item";
        getDriver().findElement(newItemNameFieldHost).sendKeys(newName);
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(pipelineButton));
        getDriver().findElement(pipelineButton).click();
        getDriver().findElement(submitButton).click();

        String currentUrl = getDriver().getCurrentUrl();
        String expectedNewUrl = String.format("/job/%s/configure", newName);
        assert currentUrl != null;
        URI uri = URI.create(currentUrl);

        String decodedPath = URLDecoder.decode(uri.getPath(), UTF_8);
        Assert.assertEquals(decodedPath, expectedNewUrl, " check Url address!");
    }
}



