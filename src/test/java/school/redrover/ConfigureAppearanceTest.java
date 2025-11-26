package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;


public class ConfigureAppearanceTest extends BaseTest {
    final String theme = "dark";

    @Ignore
    @Test
    public void changeTheme() {
        String checking = new HomePage(getDriver())
                .clickManageJenkinsIcon()
                .clickAppearance()
                .changeTheme(theme);
        Assert.assertEquals(checking, theme);
    }

        @Test
        public void testThemesAndSaveButton() {
            String popUpSaveButtonText = new HomePage(getDriver())
                    .clickGearManageJenkinsButton()
                    .clickAppearanceLink()
                    .clickDarkSystemTheme()
                    .clickLightTheme()
                    .clickDarkTheme()
                    .clickSaveButton()
                    .getPopUpSaveButtonText();

            Assert.assertEquals(popUpSaveButtonText, "Saved");
        }

    @Test
    public void testAppearance() {
        String popUpSaveButtonText =  new HomePage(getDriver())
                .clickManageJenkinsIcon()
                .clickAppearanceLink()
                .disableUserThemes()
                .changePrismSyntaxHighlightingThemeDefault()
                .changePrismSyntaxHighlightingThemeCoy()
                .changePrismSyntaxHighlightingDark()
                .changePrismSyntaxHighlightingThemeCoy()
                .pipelineStagesandGraph()
                .clickSaveButton()
                .getPopUpSaveButtonText();

        Assert.assertEquals(popUpSaveButtonText, "Saved");
    }
}
