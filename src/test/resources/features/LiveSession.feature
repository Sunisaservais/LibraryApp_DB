Feature: User module
  As a librarian, I should be able to see ACTIVE/INACTIVE user count
  to manage library

  @db
  Scenario: Librarian should be able to see ACTIVE/INACTIVE user count
    Given the user logged in as "librarian"
    And the user navigates to "Users" page
    When the user gets "ACTIVE" user count
    And the "ACTIVE" user count should be equal database
    When the user gets "INACTIVE" user count
    And the "INACTIVE" user count should be equal database
