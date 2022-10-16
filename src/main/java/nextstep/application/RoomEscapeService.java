package nextstep.application;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.dto.ReservationReq;
import nextstep.application.dto.ReservationRes;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomEscapeService {

  public Long create(ReservationReq req) {
    return null;
  }

  public List<ReservationRes> findReservations(LocalDate date) {
    return null;
  }
}
