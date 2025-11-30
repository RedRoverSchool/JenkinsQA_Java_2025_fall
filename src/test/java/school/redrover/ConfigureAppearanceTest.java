package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;


public class ConfigureAppearanceTest extends BaseTest {
    final String theme = "dark";

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

    @Test
    public void testChangeThemeDarkAndCheckBoxAllow() {
        String themaHtmltext = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickAppearanceLink()
                .clickDoNotAllowDifferentTheme()
                .clickDarkTheme()
                .clickDoNotAllowDifferentTheme()
                .clickSaveButton()
                .getHTMLAttributeThemeText();

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
}
