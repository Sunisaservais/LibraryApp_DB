Feature: Books module: As a librarian, I should be able to get the book count of each category to manage library

  @wip @db
  Scenario Outline: Librarian should able to get book count based on category
    Given the user logged in as "librarian"
    And the user navigates to "Books" page
    When the user gets "<category>" book count
    Then the "<category>" book count should be equal with database

    Examples:
      | category                |
      | Action and Adventure    |
      | Anthology               |
      | Classic                 |
      | Comic and Graphic Novel |
      | Crime and Detective     |
      | Drama                   |
      | Fable                   |
      | Fairy Tale              |
      | Fan-Fiction             |
      | Fantasy                 |
      | Historical Fiction      |
      | Horror                  |
      | Science Fiction         |
      | Biography/Autobiography |
      | Humor                   |
      | Romance                 |
      | Short Story             |
      | Essay                   |
      | Memoir                  |
      | Poetry                  |