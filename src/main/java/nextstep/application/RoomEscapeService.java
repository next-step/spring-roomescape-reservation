package nextstep.application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.dto.ReservationCreateReq;
import nextstep.application.dto.ReservationRes;
import nextstep.domain.ReservationEntity;
import nextstep.domain.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomEscapeService {

  private final ReservationRepository repository;

  public Long create(ReservationCreateReq req) {
    var reservation = ReservationEntity.builder()
        .date(req.date())
        .time(LocalTime.parse(req.time()))
        .name(req.name())
        .build();
    var entity = repository.save(reservation);
    return entity.getId();
  }

  public List<ReservationRes> findReservations(LocalDate date) {
    var reservations = repository.findReservationsByDate(date);
    return reservations.stream()
        .map(it -> ReservationRes.builder()
            .id(it.getId())
            .date(it.getDate())
            .time(it.getTime().format(DateTimeFormatter.ofPattern("HH:mm")))
            .name(it.getName())
            .build())
        .toList()
        ;
  }

  public void removeReservation(LocalDate date, String time) {
    var targetTime = LocalTime.parse(time);
    repository.deleteByDateAndTime(date, targetTime);
  }

}
