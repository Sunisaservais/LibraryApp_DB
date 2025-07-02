package com.cydeo.steps;

import com.cydeo.pages.UsersPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;

import io.cucumber.java.en.When;
import org.junit.Assert;


public class LiveWeekendStepDefinition {

    UsersPage usersPage = new UsersPage();
    String actualUserCount;

    @When("the user gets {string} user count")
    public void the_user_gets_user_count(String status) {
        BrowserUtil.selectByVisibleText(usersPage.userStatusDropdown, status);
        BrowserUtil.waitFor(2);
        String userDetails = usersPage.userCount.getText();

        actualUserCount = usersPage.getCount(userDetails);
    }

    @When("the {string} user count should be equal database")
    public void the_user_count_should_be_equal_database(String status) {
        DB_Util.runQuery("SELECT COUNT(*) FROM users WHERE status='" + status + "' and user_group_id<>1;");

        String expectedUserCount = DB_Util.getFirstRowFirstColumn();

        Assert.assertEquals(expectedUserCount, actualUserCount);

        System.out.println("actualUserCount = " + actualUserCount);
        System.out.println("expectedUserCount = " + expectedUserCount);
    }
}
