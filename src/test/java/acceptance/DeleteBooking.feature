@BookingTest
Feature: Acceptance test for deleting bookings

  Background:
    Given the user is on the hotel booking page
    And the user enters the first name as "William-Delete"
    And the user enters the last name as "Smith"
    And the user enters the total price as 450
    And the user selects the deposit paid to be "true"
    And the user enters the check-in date as "2018-07-01"
    And the user enters the check-out date as "2018-07-03"
    And the user clicks save on the hotel booking
    Then the booking for the following customer has been saved
      |William-Delete|Smith|450|true|2018-07-01|2018-07-03|

  Scenario: Delete hotel booking
    When the user clicks on the delete button for "William-Delete" "Smith" booking
    Then the booking for "William-Delete" "Smith" has been deleted


