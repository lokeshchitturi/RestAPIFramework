@tag
Feature: To validate the functionality of rest api's

  @tag2
  Scenario: Add multiple places
    Given load the requried base url
    And load the excel data into list from the sheet "Sheet1"
    And populate the required quey parameters
    When build the payload,Hit the post request, perform validations and validate the response
