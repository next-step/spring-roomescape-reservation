package roomescape.domain.reservation;

import java.time.LocalDate;

public record CreateReservation(String name, LocalDate date, long timeId, long themeId) {

}
