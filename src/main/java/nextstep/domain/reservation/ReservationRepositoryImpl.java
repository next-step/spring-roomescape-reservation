package nextstep.domain.reservation;

import static java.util.Comparator.comparing;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.stereotype.Component;

@Component
public class ReservationRepositoryImpl implements ReservationRepository {

  static HashMap<LocalDate, Set<Reservation>> reservationBook = new HashMap<>();

  // TODO : 정렬 로직과 중복 제거 로직은 향후 책임 분리
  @Override
  public Reservation save(Reservation reservation) {
    Set<Reservation> reservationsAtDate = reservationBook.getOrDefault(reservation.getDate(), new TreeSet<>(comparing(Reservation::getTime)));
    reservationsAtDate.add(reservation);
    return reservation;
  }
}
