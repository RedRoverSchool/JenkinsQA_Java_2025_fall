package school.redrover.common;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public abstract class BaseTest {

    private WebDriver driver;

    @BeforeMethod
    protected void beforeMethod(Method method) {
        ProjectUtils.logf("Run %s.%s", this.getClass().getName(), method.getName());

        ProjectUtils.log("Clear data");
        JenkinsUtils.clearData();
        ProjectUtils.log("Open the browser");
        driver = ProjectUtils.createDriver();
        ProjectUtils.log("Get the web page");
        ProjectUtils.get(getDriver());
        ProjectUtils.log("Login");
        JenkinsUtils.login(getDriver());
    }

    @AfterMethod
    protected void afterMethod(Method method, ITestResult testResult) {
        if (!testResult.isSuccess() && ProjectUtils.isRunCI()) {
            ProjectUtils.takeScreenshot(getDriver(), testResult.getTestClass().getRealClass().getSimpleName(), testResult.getName());
        }

        if (ProjectUtils.isRunCI() || testResult.isSuccess() || ProjectUtils.closeIfError()) {
            try {
                JenkinsUtils.logout(getDriver());
            } catch (Exception ignore) {}

            driver.quit();
        }

        ProjectUtils.logf("Execution time is %.3f sec", (testResult.getEndMillis() - testResult.getStartMillis()) / 1000.0);
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
