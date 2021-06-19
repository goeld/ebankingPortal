Feature: As a validated customer
  system should be able to provide the account(s) I have

  Scenario: As a valid customer, if currency is not provided, no account is returned
    Given I am valid customer
    When  I request for my accounts
          And currency is missing
    Then "Currency is missing" message is shown to me

  Scenario: As a valid customer, if currency is provided,  account is returned
    Given I am valid customer
    When  I request for my accounts with valid currency
    Then My accounts are returned