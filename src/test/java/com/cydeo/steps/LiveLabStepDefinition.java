package com.cydeo.steps;

import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LiveLabStepDefinition {

    String actualPopular;

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
}
