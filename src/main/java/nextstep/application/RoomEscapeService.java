package nextstep.application;

import java.time.LocalDate;
import java.time.LocalTime;
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
    return null;
  }

  public void removeReservation(LocalDate date, String time) {
  }

}
