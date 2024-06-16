package roomescape.reservation;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReservationPolicy {

    public boolean validateName(String name) {
        return name.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    public boolean isPastDate(String date) {
        return Integer.parseInt(date.substring(0, 4)) < LocalDate.now().getYear()
                && Integer.parseInt(date.substring(5, 7)) < LocalDate.now().getMonthValue()
                && Integer.parseInt(date.substring(8, 10)) < LocalDate.now().getDayOfMonth();
    }

    public boolean validateDate(String date) {
        return Integer.parseInt(date.substring(5, 7)) > 12
                || Integer.parseInt(date.substring(8, 10)) > 31
                || date.length() != 10
                || isPastDate(date);

    }
}
