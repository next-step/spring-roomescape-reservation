package roomescape.adapter.out.in_memory;

import static roomescape.adapter.mapper.ReservationMapper.mapToDomain;
import static roomescape.adapter.mapper.ReservationMapper.mapToEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.adapter.mapper.ReservationMapper;
import roomescape.adapter.out.ReservationEntity;
import roomescape.application.port.out.ReservationPort;
import roomescape.domain.Reservation;

@Component
public class ReservationInMemoryRepository implements ReservationPort {

  private final AtomicLong index;

  private final Map<Long, ReservationEntity> reservationMap;

  public ReservationInMemoryRepository() {
    index = new AtomicLong(0);
    reservationMap = new HashMap<>();

    saveReservations(new Reservation(null, "브라운", "2024-06-06", "12:30"));
    saveReservations(new Reservation(null, "블랙", "2024-07-06", "11:00"));
  }

  @Override
  public List<Reservation> findReservations() {
    return new ArrayList<>(reservationMap.values()
                                         .stream()
                                         .map(ReservationMapper::mapToDomain)
                                         .toList());
  }

  @Override
  public Reservation saveReservations(Reservation reservation) {
    Long newIndex = index.incrementAndGet();
    ReservationEntity registerReservation = mapToEntity(reservation.addId(newIndex));

    reservationMap.put(newIndex, registerReservation);

    return mapToDomain(registerReservation);
  }

  @Override
  public void deleteReservation(Long id) {
    reservationMap.remove(id);
  }
}
