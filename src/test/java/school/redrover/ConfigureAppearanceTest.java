package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;


public class ConfigureAppearanceTest extends BaseTest {
    private final String THEME = "dark";

   @Test
    public void testDarkSystemThemeAndApplyButtonPopUp() {
        String expectedText = "Saved";
        String popUpApplyButtonText = new HomePage(getDriver())
                .clickManageJenkinsGear()
                .clickAppearanceLink()
                .clickDarkSystemTheme()
                .clickApplyButton()
                .getPopUpApplyButtonText();

        Assert.assertEquals(popUpApplyButtonText, expectedText);
    }

   @Test(dependsOnMethods = "testDarkSystemThemeAndApplyButtonPopUp")
    public void testChangeDarkThemeAndSaveButton() {
        String expectedTeg = "dark";
        String themaHtmlText = new HomePage(getDriver())
                .clickManageJenkinsGear()
                .clickAppearanceLink()
                .clickDarkTheme()
                .checkAllowTheme()
                .clickSaveButton()
                .getHTMLAttributeThemeText();

        Assert.assertEquals(themaHtmlText, expectedTeg);
    }

    @Test(dependsOnMethods = "testChangeDarkThemeAndSaveButton")
    public void changeTheme() {
        String checking = new HomePage(getDriver())
                .clickManageJenkinsGear()
                .clickAppearanceLink()
                .changeTheme(THEME)
                .clickDoNotAllowDifferentTheme()
                .clickApplyButton()
                .getHTMLAttributeThemeText();

        Assert.assertEquals(checking, THEME);
    }

    @Test(dependsOnMethods = "changeTheme")
    public void changeThemeLight() {
        String finalTheme = "Saved";
        String checkingLight = new HomePage(getDriver())
                .clickManageJenkinsGear()
                .clickAppearanceLink()
                .clickLightTheme()
                .checkAllowTheme()
                .clickApplyButton()
                .getPopUpApplyButtonText();

        Assert.assertEquals(checkingLight, finalTheme);
    }
}
