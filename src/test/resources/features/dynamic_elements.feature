Feature: Dynamic element tests

# Make sure "Hello World!" appears based on 2nd example form here: https://the-internet.herokuapp.com/dynamic_loading
  Scenario Outline: Dynamic elements
    Given I open page "<uri>"
    When I click button with text - "Start"
    Then I wait for result with text - "Hello World!"

    Examples:
      | uri                |
      | /dynamic_loading/1 |
      | /dynamic_loading/2 |