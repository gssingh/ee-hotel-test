@BookingTest
Feature: Acceptance test for creating bookings

  Scenario Outline: Create hotel bookings
    Given the user is on the hotel booking page
    When the user enters the first name as "<firstName>"
    And the user enters the last name as "<lastName>"
    And the user enters the total price as <totalPrice>
    And the user selects the deposit paid to be "<isDepositPaid>"
    And the user enters the check-in date as "<checkIn>"
    And the user enters the check-out date as "<checkOut>"
    And the user clicks save on the hotel booking
    Then the booking for the following customer has been saved
      |<firstName>|<lastName>|<totalPrice>|<isDepositPaid>|<checkIn>|<checkOut>|

  Examples:
    | firstName | lastName | totalPrice | isDepositPaid | checkIn    | checkOut   |
    | William   | Smith    | 450        | true          | 2018-07-01 | 2018-07-03 |
    | Jack      | Jones    | 350        | false         | 2019-01-01 | 2019-01-02 |

  #




