import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class PaymentPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameOnCard   = By.name("name_on_card");
    private final By cardNumber   = By.name("card_number");
    private final By cvc          = By.name("cvc");
    private final By expireMonth  = By.name("expiry_month");
    private final By expireYear   = By.name("expiry_year");
    private final By payBtn       = By.id("submit");

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(payBtn));
    }

    public OrderConfirmationPage pay(String name, String num, String cvcTxt,
                                     String month, String year) {
        driver.findElement(nameOnCard).sendKeys(name);
        driver.findElement(cardNumber).sendKeys(num);
        driver.findElement(cvc).sendKeys(cvcTxt);
        driver.findElement(expireMonth).sendKeys(month);
        driver.findElement(expireYear).sendKeys(year);
        driver.findElement(payBtn).click();
        return new OrderConfirmationPage(driver);
    }
}
