Feature: grid tests

  @regression
  Scenario: pagination test
    Given clear database
    Given add message to database with id "45" and text "auto_added_post"
    Given add message to database with id "46" and text "auto_added_post"
    Given add message to database with id "47" and text "auto_added_post"
    Given add message to database with id "48" and text "auto_added_post"
    Given add message to database with id "49" and text "auto_added_post"
    Given add message to database with id "50" and text "auto_added_post"
    Given add message to database with id "51" and text "auto_added_post"
    Given add message to database with id "52" and text "auto_added_post"
    Given add message to database with id "53" and text "auto_added_post"
    Given add message to database with id "54" and text "auto_added_post"
    Given add message to database with id "55" and text "auto_added_post"
    Given add message to database with id "56" and text "auto_added_post"
    Given add message to database with id "57" and text "auto_added_post"
    Given add message to database with id "58" and text "auto_added_post"
    Given add message to database with id "59" and text "auto_added_post"
    Given get login by "testuserregister"
    When check that auth success
    When go to "Messages" page
    Then check that count of pages "2" and page "1" active
    When click "2" "page nav" button
    Then check that count of pages "2" and page "2" active
    When logout

  @regression
  Scenario: elements on page test
    Given clear database
    Given add message to database with id "45" and text "auto_added_post"
    Given add message to database with id "46" and text "auto_added_post"
    Given add message to database with id "47" and text "auto_added_post"
    Given add message to database with id "48" and text "auto_added_post"
    Given add message to database with id "49" and text "auto_added_post"
    Given add message to database with id "50" and text "auto_added_post"
    Given add message to database with id "51" and text "auto_added_post"
    Given add message to database with id "52" and text "auto_added_post"
    Given add message to database with id "53" and text "auto_added_post"
    Given add message to database with id "54" and text "auto_added_post"
    Given add message to database with id "55" and text "auto_added_post"
    Given add message to database with id "56" and text "auto_added_post"
    Given add message to database with id "57" and text "auto_added_post"
    Given add message to database with id "58" and text "auto_added_post"
    Given add message to database with id "59" and text "auto_added_post"
    Given get login by "testuserregister"
    When check that auth success
    When go to "Messages" page
    Then check that posts on page "10" and setting count of posts "10"
    When click "5" "post nav" button
    Then check that posts on page "5" and setting count of posts "5"
    When logout

