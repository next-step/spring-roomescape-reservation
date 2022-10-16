package nextstep.domain.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder(toBuilder = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public class ReservationFindCondition {

  LocalDate date;
  LocalTime time;
}
