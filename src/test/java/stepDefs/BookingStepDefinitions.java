package stepDefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import page.BookingPage;
import utils.DriverFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class BookingStepDefinitions {

    WebDriver driver;
    BookingPage page;

    public BookingStepDefinitions(){
        driver = DriverFactory.getDriver();
        page = new BookingPage(driver);
    }

    @Given("^the user is on the hotel booking page$")
    public void theUserIsOnTheHotelBookingsPage() {
        page.navigateToBookingPage();
    }

    @When("^the user enters the first name as \"([^\"]*)\"$")
    public void theUserEntersTheFirstnameAs(String firstName)  {
        page.setFirstName(firstName);
    }

    @And("^the user enters the last name as \"([^\"]*)\"$")
    public void theUserEntersTheLastNameAs(String lastName) {
        page.setLastName(lastName);
    }

    @And("^the user enters the total price as (\\d+)$")
    public void theUserEntersTheTotalPriceAs(int price) {
        page.setPrice(String.valueOf(price));
    }

    @And("^the user selects the deposit paid to be \"([^\"]*)\"$")
    public void theUserSelectsTheDepositPaidToBe(String isDepositPaid) {
        page.setDeposit(isDepositPaid);
    }

    @And("^the user enters the check-in date as \"([^\"]*)\"$")
    public void theUserEntersTheCheckInDateAs(String checkInDate)  {
        page.setCheckIn(checkInDate);
    }

    @And("^the user enters the check-out date as \"([^\"]*)\"$")
    public void theUserEntersTheCheckOutDateAs(String checkOutDate) {
        page.setCheckOut(checkOutDate);
    }

    @And("^the user clicks save on the hotel booking$")
    public void theUserClicksSaveOnTheHotelBookingPage() {
        page.getSaveButton().click();
    }

    @Then("^the booking for the following customer has been saved$")
    public void theBookingForTheFollowingCustomerHasBeenSaved(List<String> customerDetails) {
        Map<String, String> booking = initialiseBookingMap(customerDetails);

        page.waitUntilBookingIsSaved(booking.get("firstName"));
        page.isBookingSaved(booking);
    }

    @When("^the user clicks on the delete button for \"([^\"]*)\" \"([^\"]*)\" booking$")
    public void theUserClicksOnTheDeleteButtonForBooking(String firstName, String lastName) {
        page.deleteBookingForCustomer(firstName, lastName);
    }

    @Then("^the booking for \"([^\"]*)\" \"([^\"]*)\" has been deleted$")
    public void theBookingForHasBeenDeleted(String firstName, String lastName) {
        try {
            page.getBookingByCustomerName(firstName, lastName);
        } catch (RuntimeException e){
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    private Map<String, String> initialiseBookingMap(List<String> customerDetails){
        Map<String, String> booking = new HashMap<String, String>();

        booking.put("firstName", customerDetails.get(0));
        booking.put("lastName", customerDetails.get(1));
        booking.put("price", customerDetails.get(2));
        booking.put("deposit", customerDetails.get(3));
        booking.put("checkIn", customerDetails.get(4));
        booking.put("checkOut", customerDetails.get(5));

        return booking;
    }
}
