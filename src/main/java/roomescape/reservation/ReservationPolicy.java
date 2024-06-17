package roomescape.reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationPolicy {

    public boolean validateName(String name) {
        return name.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }

    public boolean isPastDate(String date){
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return parsedDate.isBefore(LocalDate.now());
    }

    public boolean validateDate(String date) {

        return Integer.parseInt(date.substring(5, 7)) > 12
                || Integer.parseInt(date.substring(8, 10)) > 31
                || date.length() != 10
                || isPastDate(date);

    }


}
