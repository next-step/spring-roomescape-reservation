package roomescape.adapter.out.in_memory;

import static roomescape.adapter.mapper.ReservationMapper.mapToDomain;
import static roomescape.adapter.mapper.ReservationMapper.mapToEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.adapter.mapper.ReservationMapper;
import roomescape.adapter.out.ReservationEntity;
import roomescape.application.port.out.ReservationPort;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Component
public class ReservationInMemoryRepository implements ReservationPort {

  private final AtomicLong index;

  private final Map<Long, ReservationEntity> reservationMap;

  public ReservationInMemoryRepository() {
    index = new AtomicLong(0);
    reservationMap = new HashMap<>();

    ReservationTime reservationTime1 = new ReservationTime(1L, "12:30");
    ReservationTime reservationTime2 = new ReservationTime(2L, "11:00");
    saveReservation(new Reservation(null, "브라운", "2024-06-06", reservationTime1, null), null);
    saveReservation(new Reservation(null, "블랙", "2024-07-06", reservationTime2, null), null);
  }

  @Override
  public List<Reservation> findReservations() {
    return new ArrayList<>(reservationMap.values()
                                         .stream()
                                         .map(ReservationMapper::mapToDomain)
                                         .toList());
  }

  @Override
  public Optional<Reservation> findReservationByReservationTime(ReservationTime reservationTime) {
    return null;
  }

  @Override
  public Reservation saveReservation(Reservation reservation, ReservationTime reservationTime) {
    Long newIndex = index.incrementAndGet();
    ReservationEntity registerReservation = mapToEntity(reservation.addId(newIndex));

    reservationMap.put(newIndex, registerReservation);

    return mapToDomain(registerReservation);
  }

  @Override
  public void deleteReservation(Long id) {
    reservationMap.remove(id);
  }

  @Override
  public Integer countReservationById(Long id) {
    return null;
  }
}
