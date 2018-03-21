import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;


@CucumberOptions(
        plugin = {
                "json:target/cucumber/acceptance/booking.json"
        },
        glue = {
                "stepDefs"
        },
        features = {
                "src/test/java/acceptance/CreateBooking.feature",
                "src/test/java/acceptance/DeleteBooking.feature"
        },
        strict = true
)

@RunWith(Cucumber.class)
public class BookingTest {

    static WebDriver driver;

    @BeforeClass
    public static void setUp(){
        //Can also clean any data saved in a database to start clean
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();

    }

    @AfterClass
    public static void tearDown(){
        if(null != driver){
            driver.quit();
        }
        //Can also clean any data saved in a database
    }


}
