Feature: user edit

  @regression
  Scenario: user edit
    Given clear database
    Given add message to database with id "45" and text "auto_added_post"
    Given get login by "testuserregister"
    When check that auth success
    When go to "User list" page
    Then check that "user list" page opened
    When find "testuserregister123" user and click to edit
    When check that admin unchecked and check if
    When logout

    Given get login by "testuserregister123"
    When check that auth success
    When go to "Messages" page
    Then find message posted by "testuserregister" user and check that edit button visible
    When go to "User list" page
    Then check that "user list" page opened
    When find "testuserregister123" user and click to edit
    Then check that admin checked
    When logout

