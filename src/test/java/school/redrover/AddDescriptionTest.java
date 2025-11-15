package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class AddDescriptionTest extends BaseTest {

    @Test
    public void checkElementTest() {
        final String text = "Test text";

        String confirmElement2 = new HomePage(getDriver())
                .clickAddDescription()
                .sendTextAndSubmit(text)
                .getDescription();

        Assert.assertEquals(confirmElement2, text);
    }
}
