package nextstep.core.reservation.in;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationUseCase {
    ReservationResponse create(ReservationCreateRequest request);

    List<ReservationResponse> findAllByDate(LocalDate date);

    void deleteByDateAndTime(LocalDate date, LocalTime time);
}
