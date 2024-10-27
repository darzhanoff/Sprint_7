package praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.hamcrest.core.IsEqual.equalTo;


public class CreateCourierTest {
    private Courier courier;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierRandomizer.randomCourier();
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
    @DisplayName("Регистрация курьера")
    @Description("Проверка: Курьера можно создать.В ручке переданы все обязательные поля." +
            "Возвращается правильный код ответа и значение ok: true")
    public void courierRegistration() {
        Response response = courierClient.createCourier(courier);
        courierClient.assertResponse(response, 201, null);  // Сообщение не проверяем
        response.then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Регистрация двух одинаковых курьеров")
    @Description("Проверка: Нельзя создать двух одинаковых курьеров, возвращается ошибка")
    public void courierDuplicateLogin() {
        courierClient.createCourier(courier);
        Response secondResponse = courierClient.createCourier(courier);
        courierClient.assertResponse(secondResponse, 409, "Этот логин уже используется");
    }

    @Test
    @DisplayName("Регистрация курьера без логина")
    @Description("Проверка: Отсутствие поля Логин возвращает ошибку")
    public void missingCourierLogin() {
        courier.setLogin(null);
        Response response = courierClient.createCourier(courier);
        courierClient.assertResponse(response, 400, "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Регистрация курьера без пароля")
    @Description("Проверка: Отсутствие поля Пароль возвращает ошибку")
    public void missingCourierPassword() {
        courier.setPassword(null);
        Response response = courierClient.createCourier(courier);
        courierClient.assertResponse(response, 400, "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Регистрация курьера без имени")
    @Description("Проверка: Отсутствие поля Имя возвращает ошибку")
    public void missingCourierFirstName() {
        courier.setFirstName(null);
        Response response = courierClient.createCourier(courier);
        courierClient.assertResponse(response, 400, "Недостаточно данных для создания учетной записи");
    }
}
