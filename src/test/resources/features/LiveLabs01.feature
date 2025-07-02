Feature: As a data consumer, I want to know category of books are being borrowed the most

  @wip @db
  Scenario: verify the the most popular book category that’s being borrowed
    Given Establish the database connection
    When I execute query to find the most popular book category
    Then verify "Fantasy" is the most popular book category.