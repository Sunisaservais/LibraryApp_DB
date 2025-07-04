package com.cydeo.steps;

import com.cydeo.pages.BookPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

public class LiveLabStepDefinition {

    BookPage bookPage = new BookPage();
    String actualPopular;
    String actualBookCount;
    String expectedBookCount;

    //Live Lab Feature 01:

    @Given("Establish the database connection")
    public void establish_the_database_connection() {
        //DB_Util.createConnection();
        System.out.println("DATABASE CONNECTION IS DONE BY HOOK");
    }

    @When("I execute query to find the most popular book category")
    public void i_execute_query_to_find_the_most_popular_book_category() {
        DB_Util.runQuery("select name\n" +
                "from (SELECT BC.name, COUNT(BB.book_id)\n" +
                "      FROM book_categories BC\n" +
                "               JOIN books B\n" +
                "                    ON BC.id = B.book_category_id\n" +
                "               JOIN book_borrow BB\n" +
                "                    ON B.id = BB.book_id\n" +
                "      GROUP BY BC.name\n" +
                "      ORDER BY 2 DESC) AS sub;");

        actualPopular = DB_Util.getFirstRowFirstColumn();
    }

    @Then("verify {string} is the most popular book category.")
    public void verify_is_the_most_popular_book_category(String expectedPopular) {
        BrowserUtil.waitFor(2);

        System.out.println("Actual: " + actualPopular);
        System.out.println("expected: " + expectedPopular);

        Assert.assertEquals(expectedPopular, actualPopular);
    }

    //Live Lab Feature 0:

    @When("the user gets {string} book count")
    public void the_user_gets_book_count(String category) {
        //OPT1
        //Select select = new Select(bookPage.mainCategoryElement);
        //select.selectByVisibleText(category);
        //BrowserUtil.waitFor(2);
        //actualCount = bookPage.bookCount.getText();
        //System.out.println(actualCount);

        //OPT2
        BrowserUtil.selectByVisibleText(bookPage.mainCategoryElement, category);
        BrowserUtil.waitFor(2);
        actualBookCount = bookPage.getCount(bookPage.bookCount.getText());
    }

    @Then("the {string} book count should be equal with database")
    public void the_book_count_should_be_equal_with_database(String category) {
        DB_Util.runQuery("SELECT COUNT(*) AS total_books\n" +
                "FROM books\n" +
                "         JOIN library2.book_categories bc ON bc.id = books.book_category_id\n" +
                "where bc.name = '" + category + "'");

        expectedBookCount = DB_Util.getFirstRowFirstColumn();
        System.out.println("actualCount = " + actualBookCount);
        System.out.println("expectedCount = " + expectedBookCount);
        Assert.assertEquals(expectedBookCount, actualBookCount);
    }
}
