Feature: subscribe/unsubscribe

  @regression
  Scenario: subscribe
    Given clear database
    Given add message to database with id "45" and text "auto_added_post"
    Given get login by "testuserregister123"
    Then check that auth success
    When go to "Messages" page
    When find "testuserregister" user post and clicked on author
    When click to "subscribe" button
    Then check that button name is unsubscribe
    When click to subscribers count
    Then check that user "testuserregister123" displayed in subscribers list
    When logout

  @regression
  Scenario: unsubscribe
    Given get login by "testuserregister123"
    When check that auth success
    When go to "Messages" page
    When find "testuserregister" user post and clicked on author
    When click to "unsubscribe" button
    Then check that button name is subscribe
    When click to subscribers count
    Then check that user "testuserregister123" not displayed in subscribers list
    When logout
