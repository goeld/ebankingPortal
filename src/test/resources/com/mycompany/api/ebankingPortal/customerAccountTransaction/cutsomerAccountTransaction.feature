Feature: As a logged in customer,
  I want to see be see all my accounts by currency
  For each account I should be able to see the transactions such that
  It is filtered by a given calendar month
  I can see in paginated manner
  with latest transactions first


  Scenario: As a authenticated customer system is able to get my custmer id
    Given I am logged into portal
    When  I ask for my transactions
    Then  System is able to get my customer id

  Scenario: As a customer, I should be able to see list of all the accounts for each currency
    Given I am logged into portal
    When  I ask for my transactions
    And System has my customer id
    Then  System is able to get list of all my accounts

  Scenario: As an customer account holder, I should be able to see all my transcation by a calendar month in paginated manner
    Given I am logged into portal
    When  I ask for my transactions
    And System has my customer id
    And System has my list of accounts
    And I specify a Month and Year
    Then  For every accounts I have, give me list of transactions
    And every account transactions list is by calendar month
    And are paginated