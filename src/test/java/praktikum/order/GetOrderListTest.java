package praktikum.order;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import org.junit.Test;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка: В теле ответа возвращается список заказов")
    public void checkOrderList() {
        Response response = orderClient.getOrderList();
        orderClient.assertResponse(response, 200, null);
        response.then().assertThat().body("orders", notNullValue());
    }
}

