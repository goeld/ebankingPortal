Feature: Able to run the application
  As a service provider
  I want to ensure my application is up and able to serve customers

  Scenario: Able to run the application and ensure it is up
    When Application starts
    Then health status is up
    And it is ready to serve customers

