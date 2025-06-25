package com.cydeo.steps;

import com.cydeo.pages.UsersPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

public class UsersStepDefinition {

    UsersPage usersPage = new UsersPage();
    String email;
    String expectedStatus;

    @When("the user clicks Edit User button")
    public void the_user_clicks_edit_user_button() {
        usersPage.editUser.click();
    }

    @When("the user changes user status {string} to {string}")
    public void the_user_changes_user_status_to(String active, String inactive) {
        BrowserUtil.waitFor(1);
        Select select = new Select(usersPage.statusDropdown);
        select.selectByVisibleText(inactive);
        BrowserUtil.waitFor(1);

        //Save email of the user that we change the status
        email = usersPage.email.getAttribute("value");
        System.out.println("email = " + email);
        this.email = email;
        System.out.println("email = " + email);

        expectedStatus = inactive;
    }

    @When("the user clicks save changes button")
    public void the_user_clicks_save_changes_button() {
        BrowserUtil.waitFor(1);
        usersPage.saveChanges.click();
        System.out.println(email + " is deactivated");
    }

    @Then("{string} message should appear")
    public void message_should_appear(String expectedMessage) {
        BrowserUtil.waitFor(1);
        String actualMessage = usersPage.toastMessage.getText();
        System.out.println("Actual Message = " + actualMessage);
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Then("the users should see same status for related user in database")
    public void the_users_should_see_same_status_for_related_user_in_database() {
        DB_Util.runQuery("SELECT status from users where email = '" + email + "'");
        String actualStatus = DB_Util.getFirstRowFirstColumn();
        Assert.assertEquals(actualStatus, expectedStatus);

        System.out.println("Actual Status = " + actualStatus);
        System.out.println("expectedStatus = " + expectedStatus);
    }

    @Then("the user changes current user status {string} to {string}")
    public void the_user_changes_current_user_status_to(String inactive, String active) {
        //after testing, we can not leave our QA environment settings as it is because we made some changes.
        // so we need to change it to DEFAULT status for our next tests to execute properly
        // so this step for switching user status to inactive again, after we run our test case

        //OPT1 >> BrowserUtil.selectByVisibleText(usersPage.userStatusDropdown,inactive);
        //OPT2
        Select select = new Select(usersPage.userStatusDropdown);
        select.selectByVisibleText(inactive);
        BrowserUtil.waitFor(1);

        //Find user that we deactivated
        usersPage.searchField.click();
        usersPage.searchField.sendKeys(email);
        usersPage.searchField.sendKeys(Keys.ENTER);

        //We will click on edit user button based on the email that we updated
        usersPage.editUser.click();
        BrowserUtil.waitFor(1);

        // save changes
        usersPage.saveChanges.click();
        BrowserUtil.waitFor(1);
    }
}
