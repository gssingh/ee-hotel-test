package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    WebDriver driver;

    BasePage(WebDriver driver){
        this.driver = driver;
    }

    //enter text in a text field
    public void provideInput(WebElement element, String string) {
        element.sendKeys(string);
    }

    //select an option from a select tag
    protected void selectOption(WebElement dropdownElement, String option){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(dropdownElement));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(option);
    }

    //check whether an element is visible, this will time-out and throw an exception if element does not appear within 10 seconds
    protected boolean isElementVisible(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));

        return true;
    }
}
