import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class OrderConfirmationPage {
    private final WebDriver driver;
    private final By successAlert =
        By.xpath("//p[contains(text(),'Congratulations! Your order has been confirmed')]");

    public OrderConfirmationPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOrderSuccessful() {
        return !driver.findElements(successAlert).isEmpty();
    }
}
