package nextstep.domain.reservation;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.domain.reservation.Reservation.Name;
import nextstep.domain.reservation.dto.ReservationCommandDto;
import nextstep.domain.reservation.exception.ReservationIllegalArgumentException;
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

    return reservationRepository.save(createReq);
  }

  public void deleteReservation(ReservationCommandDto.Delete deleteReq) {
    boolean isDelete = reservationRepository.delete(deleteReq);
    if (!isDelete) {
      throw new ReservationIllegalArgumentException("해당 일시에는 예약이 없습니다. 요청받은 일자 : %s".formatted(deleteReq.toString()));
    }
  }

}
