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

SELECT full_name FROM users
WHERE email = 'librarian44@library';

SELECT * from users where email = 'Jonnie@library'