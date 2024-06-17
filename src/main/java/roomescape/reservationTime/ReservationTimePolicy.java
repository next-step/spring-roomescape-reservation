package roomescape.reservationTime;

import org.springframework.stereotype.Component;

@Component
public class ReservationTimePolicy {

    public boolean validateStartAt(String startAt) {
        return startAt.length() != 5
                || Integer.parseInt(startAt.substring(0, 2)) > 23
                || Integer.parseInt(startAt.substring(3, 5)) > 59;
    }
}
