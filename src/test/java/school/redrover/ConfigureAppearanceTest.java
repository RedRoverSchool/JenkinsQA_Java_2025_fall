package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.time.Duration;

public class ConfigureAppearanceTest extends BaseTest {
    final String theme = "dark";

    @Test
    public void testChangeTheme() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='appearance']")).click();
        getDriver().findElement(By.cssSelector("label:has(> div[data-theme='dark'])")).click();

        if (!getDriver().findElement(By.cssSelector("input[name='_.disableUserThemes']")).isSelected()) {
            getDriver().findElement(
                    By.xpath("//label[contains(., 'Do not allow users to select a different theme')]")
            ).click();
        }

        getDriver().findElement(By.cssSelector("button.jenkins-submit-button")).click();

        wait.until(driver -> {
            try {
                WebElement html = driver.findElement(By.cssSelector("html"));
                return "dark".equals(html.getAttribute("data-theme"));
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    @Ignore //Test failed on CI
    @Test
    public void testChangeThemeDarkAndCheckBoxAllow() {
        String themaHtmltext = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickAppearanceLink()
                .clickDoNotAllowDifferentTheme()
                .clickDarkTheme()
                .clickDoNotAllowDifferentTheme()
                .clickSaveButton()
                .getHTMLAttributThemeText();

        Assert.assertEquals(themaHtmltext, "dark");
    }

    @Ignore
    @Test
    public void changeTheme() {
        String checking = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickAppearance()
                .changeTheme(theme);
        Assert.assertEquals(checking, theme);
    }

    @Test
    public void testThemesAndApplyPopUpButton() {
        String popUpApplyButtonText = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickAppearanceLink()
                .clickDarkSystemTheme()
                .clickLightTheme()
                .clickDarkTheme()
                .clickApplyButton()
                .getPopUpApplyButtonText();

        Assert.assertEquals(popUpApplyButtonText, "Saved");
    }
}
