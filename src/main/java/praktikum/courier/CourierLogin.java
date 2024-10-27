package praktikum.courier;

public class CourierLogin {

    private String password;
    private String login;

    public CourierLogin (String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierLogin fromCourier (Courier courier) {
        return new CourierLogin(courier.getLogin(), courier.getPassword());
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
