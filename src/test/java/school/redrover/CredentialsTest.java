package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.List;

public class CredentialsTest extends BaseTest {

    @Test
    public void testCreateUsernameWithPassword() {
        final String username = "Admin";
        final String password = "123321";
        final String description = "Test Create Username with password kind";
        final String expectedName = String.format("%s/****** (%s)", username, description);

        List<WebElement> credentialsListBeforeCreateNewOne = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickCredentialsLink()
                .clickGlobalLink()
                .getGlobalCredentialsList();

        int beforeCount = credentialsListBeforeCreateNewOne.size();

        List<WebElement> credentialsListAfterCreateNewOne = new HomePage(getDriver())
                .clickGearManageJenkinsButton()
                .clickCredentialsLink()
                .clickGlobalLink()
                .clickAddCredentialsButton()
                .sendUsernameIntoUsernameInputField(username)
                .sendPasswordIntoPasswordInputField(password)
                .sendDescriptionIntoDescriptionInputField(description)
                .clickCreateButton()
                .getGlobalCredentialsList();

        Assert.assertEquals(
                credentialsListAfterCreateNewOne.size(),
                beforeCount + 1,
                "Количество credentials должно увеличиться на 1");

        boolean actualName = credentialsListAfterCreateNewOne
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .anyMatch(text -> text.equals(expectedName));

        Assert.assertTrue(
                actualName,
                String.format("Credential с именем '%s' не найден", expectedName));
    }
}

