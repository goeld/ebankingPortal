Feature: System should be able to get exchange rates
  it

  Scenario: System should be able to get the exchange rates from XYZ provider
    Given Provider is up
    When Exchange rate is enquired
    Then exchange rates are provided for all the pairs