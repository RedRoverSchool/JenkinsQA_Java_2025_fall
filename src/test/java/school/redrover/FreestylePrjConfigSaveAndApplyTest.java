package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class FreestylePrjConfigSaveAndApplyTest extends BaseTest {

    @Test
    public void testSaveButtonIsVisibleAndClickable() {
        final String nameFreestyleProjectItem = "My FreeStyleProject";

        WebElement saveButton = new HomePage(getDriver())
                .clickCreateJob()
                .sendName(nameFreestyleProjectItem)
                .selectFreestyleProjectAndSubmit()
                .getSaveButton();

        Assert.assertTrue(saveButton.isDisplayed());
    }

}