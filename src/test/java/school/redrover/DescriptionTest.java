package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

public class DescriptionTest extends BaseTest {

    @Test
    public void testCreateDescription() {
        final String description = "Test description";

        String actualDescription = new HomePage(getDriver())
                .clickDescription()
                .sendDescriptionText(description)
                .submitDescription()
                .getDescription();

        Assert.assertEquals(actualDescription, description);
    }

    @Ignore
    @Test
    public void testChangeDescription() {
        final String firstDescription = "First text!";
        final String secondDescription = "Second text!";

        String actualDescription = new HomePage(getDriver())
                .clickDescription()
                .sendDescriptionText(firstDescription)
                .submitDescription()
                .clickDescription()
                .sendDescriptionText(secondDescription)
                .submitDescription()
                .getDescription();

        Assert.assertEquals(actualDescription, secondDescription + firstDescription);

    }
}