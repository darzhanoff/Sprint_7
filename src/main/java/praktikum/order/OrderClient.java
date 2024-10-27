package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OrderClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_PATH = "/api/v1/orders";

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Получение списка заказов")
    public Response getOrderList() {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .get(ORDER_PATH);
    }

    @Step("Проверка кода ответа и сообщение")
    public void assertResponse(Response response, int expectedStatusCode, String expectedMessage) {
        response.then().assertThat()
                .statusCode(expectedStatusCode)
                .body("message", equalTo(expectedMessage));
    }
}
