package nextstep.domain.reservation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.domain.reservation.Reservation.Name;
import nextstep.domain.reservation.dto.ReservationCommandDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReservationCommander {

  ReservationRepository reservationRepository;

  public Reservation createReservation(ReservationCommandDto.Create createReq) {
    Reservation reservation = Reservation.builder()
        .date(createReq.date())
        .time(createReq.time())
        .name(Name.of(createReq.name()))
        .build();

    return reservationRepository.save(reservation);
  }

}
