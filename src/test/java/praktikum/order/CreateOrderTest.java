package praktikum.order;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final OrderClient orderClient = new OrderClient();
    private final String testName;
    private final String[] color;

    public CreateOrderTest(String testName, String[] color) {
        this.testName = testName;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] setColor() {
        return new Object[][]{
                {"Черный жемчуг", new String[]{"BLACK"}},
                {"Серая безысходность", new String[]{"GREY"}},
                {"Черный жемчуг и Серая безысходность", new String[]{"BLACK", "GREY"}},
                {"Не выбран", new String[]{}}
        };
    }

    @Test
    @DisplayName("Создание заказа с разными цветами самоката")
    @Description("Проверка: Заказ создаётся с разными цветами самоката, тело ответа содержит track")
    public void createOrder() {
        Order order = OrderRandomizer.randomOrder();
        order.setColor(color);

        Response response = orderClient.createOrder(order);
        orderClient.assertResponse(response, 201, null);
        response.then().assertThat().body("track", notNullValue());
    }
}
