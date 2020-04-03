Feature: messages test

  @regression
  Scenario: add message test
    Given clear database
    Given get login by "testuserregister"
    When check that auth success
    When go to "Messages" page
    When click to "Add a post" button
    When send "random" to "about" input
    When send "testtag" to "tag" input
    When click to "publish" button
    Then check that message added
    Then refresh "" page
    When logout

