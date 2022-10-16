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
import nextstep.domain.reservation.dto.ReservationCommandDto;
import nextstep.domain.reservation.dto.ReservationCommandDto.Delete;
import nextstep.domain.reservation.dto.ReservationFindCondition;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InMemoryReservationRepository implements ReservationRepository {

  Map<LocalDate, Set<Reservation>> reservationBook = new ConcurrentHashMap<>();

  // TODO : 정렬 로직과 중복 제거 로직은 향후 책임 분리
  @Override
  public Reservation save(Reservation reservation) {
    // FIXME : getOrDefault와 put 사이의 지연으로 인해 동시성 이슈 발생할 수 있음
    Set<Reservation> reservationsAtDate = reservationBook.getOrDefault(reservation.getDate(),
        new TreeSet<>(comparing(Reservation::getTime)));
    reservationsAtDate.add(reservation);
    reservationBook.put(reservation.getDate(), reservationsAtDate);
    return reservation;
  }

  @Override
  public List<Reservation> findAll(ReservationFindCondition condition) {
    Set<Reservation> reservations = reservationBook.getOrDefault(condition.getDate(), new HashSet<>());
    return new ArrayList<>(reservations);
  }

  /**
   * @param deleteReq
   * @return 실제로 삭제 되었는지 여부. 삭제 되었으면 true, 그렇지 않으면 false
   */
  @Override
  public boolean delete(ReservationCommandDto.Delete deleteReq) {
    // FIXME : getOrDefault와 put 사이의 지연으로 인해 동시성 이슈 발생할 수 있음
    Set<Reservation> reservationsAtDate = reservationBook.getOrDefault(deleteReq.date(),
        new TreeSet<>(comparing(Reservation::getTime)));
    if (reservationsAtDate.size() == 0) {
      return false;
    }

    return removeSameDateTimeReservation(deleteReq, reservationsAtDate);
  }

  private boolean removeSameDateTimeReservation(Delete deleteReq, Set<Reservation> reservationsAtDate) {
    var iter = reservationsAtDate.iterator();
    var deleted = false;
    do {
      var next = iter.next();
      if (next.hasSameDateTime(deleteReq.date(), deleteReq.time())) {
        iter.remove();
        deleted = true;
        break;
      }
    } while (iter.hasNext());
    return deleted;
  }
}
