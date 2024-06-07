package roomescape.adapter.out.in_memory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.application.port.out.ReservationPort;
import roomescape.domain.Reservation;

@Component
public class ReservationInMemoryAdapter implements ReservationPort {

  private final AtomicLong index;

  private final Map<Long, Reservation> reservationMap;

  public ReservationInMemoryAdapter() {
    index = new AtomicLong(0);
    reservationMap = new HashMap<>();

    saveReservations(new Reservation(null, "브라운",
      LocalDateTime.parse("2024-06-06 12:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

    saveReservations(new Reservation(null, "블랙",
      LocalDateTime.parse("2024-07-06 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
  }

  @Override
  public List<Reservation> findReservations() {
    return new ArrayList<>(reservationMap.values());
  }

  @Override
  public Reservation saveReservations(Reservation reservation) {
    Long newIndex = index.incrementAndGet();
    Reservation registerReservation = reservation.addId(newIndex);

    reservationMap.put(newIndex, registerReservation);

    return registerReservation;
  }

  @Override
  public void deleteReservation(Long id) {
    reservationMap.remove(id);
  }
}
