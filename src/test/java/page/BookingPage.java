package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;


public class BookingPage extends BasePage {

    private static final String URL = "http://hotel-test.equalexperts.io/";
    private String pageTitle = "Hotel booking form";


    @FindBy(id = "firstname")
    private WebElement firstName;

    @FindBy(id = "lastname")
    private WebElement lastName;

    @FindBy(id = "totalprice")
    private WebElement price;

    @FindBy(id = "depositpaid")
    private WebElement deposit;

    @FindBy(id = "checkin")
    private WebElement checkIn;

    @FindBy(id = "checkout")
    private WebElement checkOut;

    @FindBy(css = "input[value=' Save ']")
    private WebElement saveButton;

    @FindBy(css = "input[value='delete']")
    private WebElement deleteButton;

    @FindBy(id = "bookings")
    private WebElement bookings;

    public BookingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public BookingPage navigateToBookingPage() {
        driver.get(URL);
        assertTrue("bookings are not showing", isElementVisible(bookings));
        assertTrue("page title does not match", driver.getTitle().equals(pageTitle));
        return new BookingPage(driver);
    }


    public WebElement getBookingByCustomerName(String firstName, String lastName) {

        for (WebElement row : getAllRows()) {
            Map<String, String> columns = getAllRowColumns(row);
            if (columns.get("firstName").equals(firstName) && columns.get("lastName").equals(lastName)) {
                return row;
            }
        }

        throw new RuntimeException("booking does not exist");
    }

    public void isBookingSaved(Map<String, String> expectedBooking) {
        WebElement bookingRow = getBookingByCustomerName(expectedBooking.get("firstName"), expectedBooking.get("lastName"));
        Map<String, String> actualBooking = getAllRowColumns(bookingRow);

        //Check all fields are saved with the value we provided.
        assertTrue("incorrect first name", actualBooking.get("firstName").equalsIgnoreCase(expectedBooking.get("firstName")));
        assertTrue("incorrect last name", actualBooking.get("lastName").equalsIgnoreCase(expectedBooking.get("lastName")));
        assertTrue("incorrect price", actualBooking.get("price").equalsIgnoreCase(expectedBooking.get("price")));
        assertTrue("incorrect deposit paid", actualBooking.get("deposit").equalsIgnoreCase(expectedBooking.get("deposit")));
        assertTrue("incorrect check in date", actualBooking.get("checkIn").equalsIgnoreCase(expectedBooking.get("checkIn")));
        assertTrue("incorrect check out date", actualBooking.get("checkOut").equalsIgnoreCase(expectedBooking.get("checkOut")));
    }

    public void deleteBookingForCustomer(String firstName, String lastName) {
        WebElement booking = getBookingByCustomerName(firstName, lastName);
        booking.findElement(By.cssSelector("input[value='Delete']")).click();
    }

    public void waitUntilBookingIsSaved(String firstName) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(waitTillBookingIsSaved(firstName));
    }

    public void setFirstName(String name) {
        provideInput(firstName, name);
    }

    public void setLastName(String name) {
        provideInput(lastName, name);
    }

    public void setPrice(String price) {
        provideInput(this.price, price);
    }

    public void setDeposit(String isDepositPaid) {
        selectOption(deposit, isDepositPaid);
    }

    public void setCheckIn(String date) {
        provideInput(checkIn, date);
    }

    public void setCheckOut(String date) {
        provideInput(checkOut, date);
    }

    public WebElement getSaveButton() {
        return saveButton;
    }

    public WebElement getFirstName() {
        return firstName;
    }

    public WebElement getLastName() {
        return lastName;
    }

    public WebElement getPrice() {
        return price;
    }

    public WebElement getDeposit() {
        return deposit;
    }

    public WebElement getCheckIn() {
        return checkIn;
    }

    public WebElement getCheckOut() {
        return checkOut;
    }

    private List<WebElement> getAllRows() {
        return bookings.findElements(By.className("row"));
    }

    private Map<String, String> getAllRowColumns(WebElement row) {
        List<WebElement> columns = row.findElements(By.tagName("div"));
        Map<String, String> actualBooking = new HashMap<String, String>();

        actualBooking.put("firstName", columns.get(0).getText());
        actualBooking.put("lastName", columns.get(1).getText());
        actualBooking.put("price", columns.get(2).getText());
        actualBooking.put("deposit", columns.get(3).getText());
        actualBooking.put("checkIn", columns.get(4).getText());
        actualBooking.put("checkOut", columns.get(5).getText());

        return actualBooking;
    }

    //waits until the booking is found saved in one of the rows - only uses first name, this can be enhanced
    private ExpectedCondition<Boolean> waitTillBookingIsSaved(final String firstName) {
        return new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                boolean bookingFound = false;
                List<WebElement> allBookings = getAllRows();

                for (WebElement booking : allBookings) {
                    Map<String, String> columns = getAllRowColumns(booking);
                    if (columns.get("firstName").equals(firstName)) {
                        bookingFound = true;
                        break;
                    }
                }
                return bookingFound;
            }
        };
    }
}
