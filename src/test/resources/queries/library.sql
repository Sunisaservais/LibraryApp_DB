SELECT COUNT(*)
FROM books;

SELECT COUNT(*)
FROM users;

SELECT COUNT(*)
FROM book_borrow
WHERE is_returned = 0;

SELECT name
FROM book_categories;

SELECT name,author,isbn,year,description
FROM books
WHERE name = 'Agile Testing';