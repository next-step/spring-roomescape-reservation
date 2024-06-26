package roomescape.api.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.domain.reservation.CreateReservation;

record CreateReservationRequest(@NotBlank String name, @NotNull LocalDate date, long timeId, long themeId) {
  CreateReservation toDomain() {
    return new CreateReservation(name, date, timeId, themeId);
  }
}
