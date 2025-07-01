package com.cydeo.utility;

public class DatabaseHelper {

    public static String getMostPopularBookCategory() {

        return "select bc.name,count(*)" +
                "    from book_categories bc" +
                "        inner join books b on bc.id = b.book_category_id" +
                "        inner join book_borrow bb on b.id = bb.book_id" +
                "    group by bc.name" +
                "    order by count(*) desc" +
                "    limit 1";

    }

    public static String getBookInfo(String bookName) {

        return "select name, author, isbn from books where name = '" + bookName + "'";
    }

    //DON'T TELL
    /*
    public static String deleteBook(int isbn){
        return "delete * from books where isbn = '" + isbn + "'";
    }

    //SQL INJECTION
    //The deleteBook() method is vulnerable to SQL injection because it directly concatenates the isbn into the query string. This allows an attacker to manipulate the isbn value and potentially delete all records in the books table. For example, if an attacker inputs 1' OR '1=1, the resulting query would be:

    public static String deleteBook2(int isbn){
        return "delete * from books where isbn = '" + isbn + "' OR isbn = '1=1'";
    }
    //Since 1=1 is always true, this could delete all rows in the table.

    //HOW TO FIX IT?
    // To prevent this, use PreparedStatement with parameterized queries instead of string concatenation. Here’s a safer way to handle it in JDBC:

    public static void deleteBook(Connection connection, int isbn) throws SQLException {
        String query = "DELETE FROM books WHERE isbn = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, isbn);
            preparedStatement.executeUpdate();
        }
    }
    //This way, even if an attacker tries to inject malicious input, it will be treated as a parameter, not executable SQL code, effectively preventing SQL injection.
    */
}

