package roomescape.reservation.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

import roomescape.reservation.domain.Reservation;
import roomescape.theme.domain.Theme;
import roomescape.time.domain.ReservationTime;

public record ReservationCreateRequest(@NotBlank(message = "예약 이름은 필수 입력 값입니다.")
                                       String name,
                                       @NotBlank(message = "예약 날짜는 필수 입력 값입니다.")
                                       String date,
                                       Long timeId,
                                       Long themeId) {

    public Reservation toReservation(ReservationTime savedReservationTime, Theme savedTheme) {
        return new Reservation(name, LocalDate.parse(date), savedReservationTime, savedTheme);
    }
}
