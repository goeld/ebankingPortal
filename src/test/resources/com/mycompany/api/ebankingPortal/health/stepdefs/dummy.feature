Feature: Able to run cucumber test case
  As an user
  I want to be able to run cucumber with my application
  So that I can see the cucumber report in html report


  Scenario: Able to run and see the reports
    Given Nothing
    When I click on the run button
    Then test runs
