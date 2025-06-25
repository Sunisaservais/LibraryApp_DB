package com.cydeo.steps;

import com.cydeo.pages.DashboardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginStepDefinition {

    LoginPage loginPage = new LoginPage();
    DashboardPage dashboardPage = new DashboardPage();
    String actualUsername;
    String username;

    @Given("the user logged in  {string} and {string}")
    public void the_user_logged_in_and(String username, String password) {
        loginPage.login(username, password);
        BrowserUtil.waitFor(2);

        //get email assign to global
        this.username = username;
    }

    @When("user gets username  from user fields")
    public void user_gets_username_from_user_fields() {
        actualUsername = dashboardPage.accountHolderName.getText();
        System.out.println("actualUsername = " + actualUsername);

    }

    @Then("the username should be same with database")
    public void the_username_should_be_same_with_database() {
        //Get data from database
        DB_Util.runQuery("SELECT full_name FROM users WHERE email = '" + username + "'");
        String expectedUserName = DB_Util.getFirstRowFirstColumn();
        System.out.println("expectedUserName = " + expectedUserName);

        //Compare actual vs expected
        Assert.assertEquals(actualUsername, expectedUserName);
    }
}
