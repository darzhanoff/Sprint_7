package praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;


public class LogInCourierTest {
    private Courier courier;
    private CourierClient courierClient;

    @Before
    public void SetUp() {
        courierClient = new CourierClient();
        courier = CourierRandomizer.randomCourier();
        courierClient.createCourier(courier);
    }

    @After
    public void cleanUp() {
        if (courier != null) {
            String courierId = courierClient.getCourierId(courier);
            if (courierId != null) {
                courierClient.deleteCourier(courierId);
            }
        }
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка: Курьер может войти в систему, переданы все обязательные поля" +
            "Успешный запрос возвращает ID")
    public void courierAuthorization() {
        Response loginResponse = courierClient.logIn(CourierLogin.fromCourier(courier));
        loginResponse.then()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным или несуществующим логином")
    @Description("Проверка: Ввод неправильного логина возвращает ошибку")
    public void incorrectLoginAuthorization() {
        Courier newCourier = CourierRandomizer.randomCourier();
        Response loginResponse = courierClient.logIn(
                new CourierLogin(newCourier.getLogin(), courier.getPassword())
        );
        courierClient.assertResponse(loginResponse, 404, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным паролем")
    @Description("Проверка: Ввод неправильного пароля возвращает ошибку")
    public void incorrectPasswordAuthorization() {
        Courier newCourier = CourierRandomizer.randomCourier();
        Response loginResponse = courierClient.logIn(
                new CourierLogin(courier.getLogin(), newCourier.getPassword())
        );
        courierClient.assertResponse(loginResponse, 404, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка: Попытка входа в систему без поля логин, возвращает ошибку")
    public void missingAuthorizationLogin() {
        Courier newCourier = CourierRandomizer.randomCourier();
        newCourier.setLogin(null);
        Response loginResponse = courierClient.logIn(CourierLogin.fromCourier(newCourier));

        courierClient.assertResponse(loginResponse, 400, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка: Попытка входа в систему без поля пароль, возвращает ошибку")
    public void missingAuthorizationPassword() {
        Courier newCourier = CourierRandomizer.randomCourier();
        newCourier.setPassword(null);
        Response loginResponse = courierClient.logIn(CourierLogin.fromCourier(newCourier));

        courierClient.assertResponse(loginResponse, 400, "Недостаточно данных для входа");
    }
}
