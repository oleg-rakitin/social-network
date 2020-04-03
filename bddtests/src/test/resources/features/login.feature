Feature: login

  @smoke
  Scenario: login
    Given clear database
    Given get login by "testuserregister"
    Then check that auth success
    When logout
