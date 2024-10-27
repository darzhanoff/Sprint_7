package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier/";
    private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";

    @Step("Создание курьера")
    public Response createCourier (Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_PATH);
    }

    @Step("Логин курьера")
    public Response logIn (CourierLogin courierLogin) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courierLogin)
                .when()
                .post(COURIER_LOGIN_PATH);
    }

    @Step("Удаление курьера")
    public Response deleteCourier (String courierId) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_PATH + courierId);
    }

    @Step("Получение id курьера")
    public String getCourierId(Courier courier) {
        Response loginResponse = logIn(CourierLogin.fromCourier(courier));
        if (loginResponse.statusCode() != 200) {
            return null;
        }
        int id = loginResponse.path("id");
        return String.valueOf(id);
    }

    @Step("Проверка кода ответа и сообщение")
    public void assertResponse (Response response, int expectedStatusCode, String expectedMessage) {
        response.then().assertThat()
                .statusCode(expectedStatusCode)
                .body("message", equalTo(expectedMessage));
    }
}
