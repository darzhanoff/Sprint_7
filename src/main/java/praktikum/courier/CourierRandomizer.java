package praktikum.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierRandomizer {

    static Courier randomCourier() {
        return new Courier (
                RandomStringUtils.randomAlphanumeric(6, 10),
                RandomStringUtils.randomAlphanumeric(8, 10),
                RandomStringUtils.randomAlphanumeric(3, 10)
        );
    }
}
