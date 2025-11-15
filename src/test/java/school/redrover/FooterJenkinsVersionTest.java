package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;

public class FooterJenkinsVersionTest extends BaseTest {

    @Test
    public void testDropdownMenu() {
        final List<String> expectedDropdownList = List.of("About Jenkins", "Get involved", "Website");

        List<String> dropdownList = new HomePage(getDriver())
                .clickJenkinsVersion()
                .getDropdownList();

        Assert.assertEquals(dropdownList, expectedDropdownList);
    }
}
