package com.cydeo.steps;

import com.cydeo.pages.DashboardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class DashboardStepDefinition {
    String actualUserNumbers;
    String actualBookNumbers;
    String actualBorrowedBookNumbers;
    LoginPage loginPage = new LoginPage();
    DashboardPage dashBoardPage = new DashboardPage();


    @Given("the user logged in as {string}")
    public void the_user_logged_in_as(String user) {
        loginPage.login(user);
        BrowserUtil.waitFor(4);
    }

    @When("user gets all information from modules")
    public void user_gets_all_information_from_modules() {

        actualUserNumbers = dashBoardPage.usersNumber.getText();
        System.out.println("actualUserNumbers = " + actualUserNumbers);
        actualBookNumbers = dashBoardPage.booksNumber.getText();
        System.out.println("actualBookNumbers = " + actualBookNumbers);
        actualBorrowedBookNumbers = dashBoardPage.borrowedBooksNumber.getText();
        System.out.println("actualBorrowedBookNumbers = " + actualBorrowedBookNumbers);

    }

    @Then("the information should be same with database")
    public void the_information_should_be_same_with_database() {

        //This scenario Database result is expected.

        //Connect the same database (library2)
        //DB_Util.createConnection(); connection will be created by @db hooks
        //run query for each of the result (3 different queries)

        //BOOKS
        DB_Util.runQuery("select count(*) from books");

        String expectedBookNumbers = DB_Util.getFirstRowFirstColumn();
        System.out.println("expectedBookNumbers = " + expectedBookNumbers);
        //compare results with UI results(Actual results)
        Assert.assertEquals(actualBookNumbers, expectedBookNumbers);

        //USERS
        DB_Util.runQuery("select count(*) from users");
        String expectedUserNumbers = DB_Util.getFirstRowFirstColumn();
        System.out.println("expectedUserNumbers = " + expectedUserNumbers);
        //compare results
        Assert.assertEquals(actualUserNumbers, expectedUserNumbers);

        //BORROWED BOOKS
        String query = "select count(*) from book_borrow\n" +
                "where is_returned = 0";

        DB_Util.runQuery(query);

        String expectedBorrowedBookNumbers = DB_Util.getFirstRowFirstColumn();
        System.out.println("expectedBorrowedBookNumbers = " + expectedBorrowedBookNumbers);

        Assert.assertEquals(actualBorrowedBookNumbers, expectedBorrowedBookNumbers);

        //close connection
        //DB_Util.destroy(); connection will be closed  by @db hooks

    }
}
