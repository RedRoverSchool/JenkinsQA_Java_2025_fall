package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;


public class ConfigureAppearanceTest extends BaseTest {
    private final String THEME = "dark";

   @Test
    public void testThemesAndApplyButtonPopUp() {
        String expectedText = "Saved";
        String popUpApplyButtonText = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickAppearanceLink()
                .clickDarkSystemTheme()
                .clickLightTheme()
                .clickApplyButton()
                .getPopUpApplyButtonText();

        Assert.assertEquals(popUpApplyButtonText, expectedText);
    }

   @Test(dependsOnMethods = "testThemesAndApplyButtonPopUp")
    public void testChangeThemeAndSaveButton() {
        String expectedTeg = "dark";
        String themaHtmlText = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickAppearanceLink()
                .clickDarkTheme()
                .checkAllowTheme()
                .clickSaveButton()
                .getHTMLAttributeThemeText();

        Assert.assertEquals(themaHtmlText, expectedTeg);
    }

    @Test(dependsOnMethods = "testThemesAndApplyButtonPopUp")
    public void changeTheme() {
        String checking = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
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
                .clickGearManageJenkinsButton()
                .clickAppearanceLink()
                .clickLightTheme()
                .clickDoNotAllowDifferentTheme()
                .clickApplyButton()
                .getPopUpApplyButtonText();

        Assert.assertEquals(checkingLight, finalTheme);
    }
}
