package nextstep.domain.reservation;

import static java.util.Comparator.comparing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import nextstep.domain.reservation.dto.ReservationFindCondition;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReservationRepositoryImpl implements ReservationRepository {

  static Map<LocalDate, Set<Reservation>> reservationBook = new ConcurrentHashMap<>();

  // TODO : 정렬 로직과 중복 제거 로직은 향후 책임 분리
  @Override
  public Reservation save(Reservation reservation) {
    // FIXME : getOrDefault와 put 사이의 지연으로 인해 동시성 이슈 발생할 수 있음
    Set<Reservation> reservationsAtDate = reservationBook.getOrDefault(reservation.getDate(), new TreeSet<>(comparing(Reservation::getTime)));
    reservationsAtDate.add(reservation);
    reservationBook.put(reservation.getDate(), reservationsAtDate);
    return reservation;
  }

  @Override
  public List<Reservation> findAll(ReservationFindCondition condition) {
    Set<Reservation> reservations = reservationBook.getOrDefault(condition.getDate(), new HashSet<>());
    return new ArrayList<>(reservations);
  }
}
