package nextstep.domain.reservation;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.domain.reservation.dto.ReservationFindCondition;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReservationFinderImpl implements ReservationFinder {

  ReservationRepository reservationRepository;

  @Override
  public List<Reservation> findAll(ReservationFindCondition condition) {
    return reservationRepository.findAll(condition);
  }
}
