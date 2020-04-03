Feature: change password tests

  @regression
  Scenario: invalid old password error
    Given clear database
    Given get login by "testuserregister"
    When check that auth success
    When go to "Profile" page
    When send "testuserregisterolderrorpass" to "old password" input
    When send "testuserregisterolderrorpass" to "new password" input
    When click to "save" button
    Then check that password not changed
    When refresh "" page
    When logout

    Given get login with username "testuserregister" and password "testuserregisterolderrorpass"
    Then check that auth not success

  @regression
  Scenario: success change password
    Given clear database
    Given get login by "testuserregister"
    When check that auth success
    When go to "Profile" page
    When send "testuserregister" to "old password" input
    When send "testuserregisterolderrorpass" to "new password" input
    When click to "save" button
    When refresh "" page
    When logout

    Given get login with username "testuserregister" and password "testuserregisterolderrorpass"
    Then check that auth success
    When logout


    @regression
    Scenario: change email
      Given clear database
      Given get login by "testuserregister"
      When check that auth success
      When go to "Profile" page
      When send "testuserregister" to "old password" input
      When send "testuserregisterolderrorpass" to "new password" input
      When send "test_new@gmail.com" to "email" input

      When click to "save" button
      When refresh "" page
      When logout

      Given get login with username "testuserregister" and password "testuserregisterolderrorpass"
      When go to "Profile" page
      Then check that "email" input contains "test_new@gmail.com" data
      When logout


