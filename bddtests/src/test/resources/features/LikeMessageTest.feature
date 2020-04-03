Feature: like message test

  @regression
  Scenario: like message
    Given clear database
    Given add message to database with id "45" and text "auto_added_post"
    Given get login by "testuserregister123"
    When check that auth success
    When go to "Messages" page
    When find message posted by "testuserregister" user and like
    Then check that post by "testuserregister" user liked
    When logout

  @regression
  Scenario: dislike message
    Given get login by "testuserregister123"
    When check that auth success
    When go to "Messages" page
    When find message posted by "testuserregister" user and dislike
    Then check that post by "testuserregister" user disliked
    When logout

