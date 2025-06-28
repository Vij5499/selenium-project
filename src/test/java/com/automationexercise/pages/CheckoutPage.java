import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class CheckoutPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By commentBox   = By.name("message");
    private final By placeOrderBtn = By.linkText("Place Order");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderBtn));
    }

    public PaymentPage placeOrder(String comment) {
        driver.findElement(commentBox).sendKeys(comment);
        driver.findElement(placeOrderBtn).click();
        return new PaymentPage(driver);
    }
}
