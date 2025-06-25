package com.cydeo.steps;

import com.cydeo.pages.BookPage;
import com.cydeo.pages.DashboardPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class BooksStepDefinition {
    BookPage bookPage = new BookPage();
    List<String> actualCategoryList;

    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        new DashboardPage().navigateModule(moduleName);
    }

    @When("the user gets all book categories in webpage")
    public void the_user_gets_all_book_categories_in_webpage() {
        //potential wait here if tests fail
        actualCategoryList = BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        actualCategoryList.remove(0);
        System.out.println("actualCategoryList = " + actualCategoryList);
    }

    @Then("user should be able to see following categories")
    public void user_should_be_able_to_see_following_categories(List<String> expectedCategoryList) {
        Assert.assertEquals(expectedCategoryList, actualCategoryList);
    }

    @When("I open book {string}")
    public void i_open_book(String bookName) {
        System.out.println("bookName = " + bookName);
        BrowserUtil.waitForClickablility(bookPage.search, 5).sendKeys(bookName);
        BrowserUtil.waitForClickablility(bookPage.editBook(bookName), 5).click();
    }

    @Then("verify book categories must match book categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {
        DB_Util.runQuery("select name from book_categories");
        List<String> expectedCategoryList = DB_Util.getColumnDataAsList(1);
        Assert.assertEquals("Verify category is matching", actualCategoryList, expectedCategoryList);
        System.out.println("expectedCategoryList = " + expectedCategoryList);
    }

    @Then("book information must match the database for {string}")
    public void book_information_must_match_the_database_for(String bookName) {

    }
}
