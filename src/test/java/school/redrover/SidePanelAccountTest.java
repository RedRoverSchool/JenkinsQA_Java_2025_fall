package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class SidePanelAccountTest extends BaseTest {

    @Test
    public void testThemesAndApplyPopUpButton() throws InterruptedException {
      final String EMAIL = "gkg@kgk.kg";
        String actualEmailText = new HomePage(getDriver())
                .clickUserStatusIcon()
                .clickSidePanelAccount()
                // .editEmail(EMAIL)
                .getEmailText();
        System.out.println("Email" + actualEmailText);


      //  Assert.assertEquals(actualEmailText, EMAIL);
    }
}
