package praktikum.order;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OrderRandomizer {
    private static final String[] METRO_STATIONS = {
            "Бульвар Рокоссовского", "Тверская","Арбатская","Фили","ВДНХ",
            "Спартак", "Перово", "Полянка", "Римская", "Зорге"
    };
    private static final String DATE_FORMAT = "yyyy-MM-dd";


    public static Order randomOrder() {
        final String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        final String lastName = RandomStringUtils.randomAlphabetic(3, 15);
        final String address = RandomStringUtils.randomAlphabetic(3, 20);
        final String metroStation = METRO_STATIONS[
                ThreadLocalRandom.current().nextInt(METRO_STATIONS.length)
                ];
        final String phone = RandomStringUtils.randomNumeric(11);
        final int rentTime = ThreadLocalRandom.current().nextInt(1, 7);
        final String deliveryDate = OffsetDateTime
                .now()
                .plusDays(1)
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        final String comment = RandomStringUtils.randomAlphabetic(2, 50);
        final String[] color = {};

        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}

