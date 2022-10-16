package nextstep.domain.reservation;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.domain.reservation.Reservation.Name;
import nextstep.domain.reservation.dto.ReservationCommandDto;
import nextstep.domain.reservation.validator.ReservationCreateValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReservationCommander {

  ReservationRepository reservationRepository;
  List<ReservationCreateValidator> createValidators;

  public Reservation createReservation(ReservationCommandDto.Create createReq) {
    createValidators.forEach(
        createValidator -> createValidator.validate(createReq)
    );

    Reservation reservation = Reservation.builder()
        .date(createReq.date())
        .time(createReq.time())
        .name(Name.of(createReq.name()))
        .build();

    return reservationRepository.save(reservation);
  }

  public void deleteReservation(ReservationCommandDto.Delete deleteReq) {
    boolean isDelete = reservationRepository.delete(deleteReq);
    if (!isDelete) {
      throw new IllegalArgumentException("해당 일시에는 예약이 없습니다. 요청받은 일자 : %s".formatted(deleteReq.toString()));
    }
  }

}
