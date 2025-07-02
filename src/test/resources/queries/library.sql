SELECT COUNT(*)
FROM books;

SELECT COUNT(*)
FROM users;

SELECT COUNT(*)
FROM book_borrow
WHERE is_returned = 0;

SELECT name
FROM book_categories;

SELECT name, author, isbn, year, description
FROM books
WHERE name = 'Agile Testing';

SELECT full_name
FROM users
WHERE email = 'librarian44@library';

SELECT *
from users
where email = 'Jonnie@library';

select *
from users;

SELECT COUNT(*)
FROM users
WHERE status = 'ACTIVE'
  AND user_group_id <> '1';

SELECT COUNT(*)
FROM users
WHERE status = 'INACTIVE'
  AND user_group_id = '1';

SELECT COUNT(*) AS total_books
FROM books
         JOIN library2.book_categories bc ON bc.id = books.book_category_id
where bc.name = 'Action and Adventure';

SELECT COUNT(*) AS borrow_count
FROM book_borrow bb2
         JOIN books b2 ON bb2.book_id = b2.id
         JOIN book_categories bc2 ON b2.book_category_id = bc2.id
GROUP BY bc2.name
;


SELECT bc.name
FROM book_borrow bb
         JOIN books b ON bb.book_id = b.id
         JOIN book_categories bc ON b.book_category_id = bc.id
GROUP BY bc.name
HAVING COUNT(*) = (SELECT MAX(borrow_count)
                   FROM (SELECT COUNT(*) AS borrow_count
                         FROM book_borrow bb2
                                  JOIN books b2 ON bb2.book_id = b2.id
                                  JOIN book_categories bc2 ON b2.book_category_id = bc2.id
                         GROUP BY bc2.name) AS borrow_counts);

SELECT BC.name, COUNT(BB.book_id)
FROM book_categories BC
         JOIN books B
              ON BC.id = B.book_category_id
         JOIN book_borrow BB
              ON B.id = BB.book_id
GROUP BY BC.name
ORDER BY 2 DESC
LIMIT 1;


select name
from (SELECT BC.name, COUNT(BB.book_id)
      FROM book_categories BC
               JOIN books B
                    ON BC.id = B.book_category_id
               JOIN book_borrow BB
                    ON B.id = BB.book_id
      GROUP BY BC.name
      ORDER BY 2 DESC) AS sub;

SELECT name
FROM (SELECT BC.name, COUNT(BB.book_id) AS borrow_count
      FROM book_categories BC
               JOIN books B ON BC.id = B.book_category_id
               JOIN book_borrow BB ON B.id = BB.book_id
      GROUP BY BC.name
      ORDER BY borrow_count DESC) AS sub
