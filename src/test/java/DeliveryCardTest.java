import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private String date = LocalDate.now().plusDays(4).format(formatter);

    @Test
    @DisplayName("Should show confirmation message if all fields completed correctly")
    public void positiveRequest() {
        open("http://localhost:9999");
        SelenideElement form = $(By.className("App_appContainer__3jRx1"));
        form.$("[data-test-id = city] input").setValue("Челябинск");
        form.$("[data-test-id = date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        form.$("[data-test-id = date] input").setValue(date);
        form.$("[data-test-id = name] input").setValue("Иванов Иван");
        form.$("[data-test-id = phone] input").setValue("+79270000000");
        form.$("[data-test-id = agreement] ").click();
        form.$(By.className("button__content")).click();
        SelenideElement block = $("[data-test-id = notification]");
        block.$(By.className("notification__content")).waitUntil(text("Встреча успешно забронирована на " + date),
                15000);
    }
}
