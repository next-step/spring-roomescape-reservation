package roomescape.domain.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ReservationIsExists extends HttpClientErrorException {

  public ReservationIsExists() {
    super(HttpStatus.CONFLICT, "해당 시간에 예약이 이미 존재합니다.");
  }
}
