Feature: registration

  @regression
  Scenario: registration
    When open main page
    When check that "login" page opened
    When click to "registration" button
    When check that "registration" page opened
    When refresh "register" page
    When send "test_user_register" to "username" input
    When send "test_user_register" to "password" input
    When send "test_user_register" to "password2" input
    When send "test_user_register@email.com" to "email" input
    When check recaptcha
    When click to "create" button
